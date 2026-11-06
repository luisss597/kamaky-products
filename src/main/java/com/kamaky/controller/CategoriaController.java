package com.kamaky.controller;

import com.kamaky.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/categoria/listado")
    public String listado(Model model) {
        model.addAttribute("categorias",
                categoriaService.getCategorias(false));

        return "categoria/listado";
    }
}
