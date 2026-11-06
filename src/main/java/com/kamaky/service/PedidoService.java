/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kamaky.service;

import com.kamaky.domain.Cliente;
import com.kamaky.domain.DetallePedido;
import com.kamaky.domain.ItemCarrito;
import com.kamaky.domain.Pedido;
import com.kamaky.domain.Producto;
import com.kamaky.repository.ClienteRepository;
import com.kamaky.repository.DetallePedidoRepository;
import com.kamaky.repository.PedidoRepository;
import com.kamaky.repository.ProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author arbad
 */
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pedido> getPedido(Integer idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    @Transactional
    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido realizarPedido(Cliente cliente, List<ItemCarrito> carrito) {

        if (carrito == null || carrito.isEmpty()) {
            throw new IllegalArgumentException("El carrito esta vacio");
        }

        Optional<Cliente> clienteExistente =
                clienteRepository.findByCorreo(cliente.getCorreo());

        if (clienteExistente.isPresent()) {
            cliente = clienteExistente.get();
        } else {
            cliente = clienteRepository.save(cliente);
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrito item : carrito) {

            if (item.getCantidad() == null || item.getCantidad() <= 0) {
                throw new IllegalArgumentException("Cantidad invalida");
            }

            Producto producto = productoRepository
                    .findById(item.getProducto().getIdProducto())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Producto no encontrado"));

            if (!producto.isDisponible()) {
                throw new IllegalArgumentException("Producto no disponible");
            }

            total = total.add(producto.getPrecio().multiply(
                    BigDecimal.valueOf(item.getCantidad())));
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("Pendiente");
        pedido.setTotal(total);

        pedido = pedidoRepository.save(pedido);

        for (ItemCarrito item : carrito) {

            Producto producto = productoRepository
                    .findById(item.getProducto().getIdProducto())
                    .orElseThrow();

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal = producto.getPrecio().multiply(
                    BigDecimal.valueOf(item.getCantidad()));

            detalle.setSubtotal(subtotal);
            detallePedidoRepository.save(detalle);
        }

        return pedido;
    }
}
