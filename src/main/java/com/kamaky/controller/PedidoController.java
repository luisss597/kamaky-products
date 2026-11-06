/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kamaky.controller;

import com.kamaky.domain.Cliente;
import com.kamaky.domain.ItemCarrito;
import com.kamaky.domain.Pedido;
import com.kamaky.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author arbad
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    
    
    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("pedidos", pedidoService.getPedidos());
        return "pedido/listado";
    }


    @GetMapping("/confirmar")
    public String confirmar(Model model, HttpSession session) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrito item : carrito) {
            total = total.add(item.getSubtotal());
        }

        model.addAttribute("cliente", new Cliente());
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "pedido/confirmar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "El carrito esta vacio");
            return "redirect:/carrito";
        }

        if (cliente.getNombre() == null ||
                cliente.getNombre().isBlank() ||
                cliente.getTelefono() == null ||
                cliente.getTelefono().isBlank() ||
                cliente.getCorreo() == null ||
                cliente.getCorreo().isBlank()) {

            redirectAttributes.addFlashAttribute("error",
                    "Debe completar todos los datos");
            return "redirect:/pedido/confirmar";
        }

        try {
            Pedido pedido = pedidoService.realizarPedido(cliente, carrito);

            session.removeAttribute("carrito");

            redirectAttributes.addFlashAttribute("exito",
                    "Pedido #" + pedido.getIdPedido()
                    + " realizado correctamente");

            return "redirect:/";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("error",
                    e.getMessage());

            return "redirect:/pedido/confirmar";
        }
    }
}

