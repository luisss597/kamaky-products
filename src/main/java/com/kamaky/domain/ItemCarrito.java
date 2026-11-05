/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kamaky.domain;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author arbad
 */
@Getter
@Setter
public class ItemCarrito {

    private Producto producto;
    private Integer cantidad;

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return producto.getPrecio().multiply(
                BigDecimal.valueOf(cantidad));
    }
}

