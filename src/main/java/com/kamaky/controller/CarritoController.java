/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kamaky.controller;

import com.kamaky.domain.ItemCarrito;
import com.kamaky.domain.Producto;
import com.kamaky.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author arbad
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/carrito")
public class CarritoController {

    private final ProductoService productoService;

    @GetMapping("")
    public String listado(Model model, HttpSession session) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        for (ItemCarrito item : carrito) {
            total = total.add(item.getSubtotal());
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito/listado";
    }


    @PostMapping("/agregar")
    public String agregar(@RequestParam Integer idProducto,
            HttpSession session) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        Producto producto = productoService
                .getProducto(idProducto).orElse(null);

        if (producto != null && producto.isDisponible()) {
            boolean encontrado = false;

            for (ItemCarrito item : carrito) {
                if (item.getProducto().getIdProducto()
                        .equals(idProducto)) {
                    item.setCantidad(item.getCantidad() + 1);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                carrito.add(new ItemCarrito(producto, 1));
            }
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idProducto,
            HttpSession session) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito != null) {
            carrito.removeIf(item ->
                    item.getProducto().getIdProducto()
                            .equals(idProducto));
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/carrito";
    }
    
    
    @PostMapping("/actualizar")
    public String actualizar(@RequestParam Integer idProducto,
            @RequestParam Integer cantidad,
            HttpSession session) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito != null) {
            for (ItemCarrito item : carrito) {
                if (item.getProducto().getIdProducto()
                        .equals(idProducto)) {

                    if (cantidad > 0) {
                        item.setCantidad(cantidad);
                    } else {
                        carrito.remove(item);
                    }
                    break;
                }
            }
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/carrito";
    }

}

