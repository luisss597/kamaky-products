
package com.kamaky.controller;

import com.kamaky.domain.InformacionNegocio;
import com.kamaky.service.InformacionNegocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class InformacionNegocioController {

    private final InformacionNegocioService informacionNegocioService;

    @GetMapping("/contacto")
    public String contacto(Model model) {
        InformacionNegocio informacion = informacionNegocioService
                .getInformacionNegocio()
                .stream()
                .findFirst()
                .orElse(new InformacionNegocio());

        model.addAttribute("informacionNegocio", informacion);

        return "contacto/contacto";
    }

    @GetMapping("/informacionNegocio/quienesSomos")
    public String quienesSomos(Model model) {
        InformacionNegocio informacion = informacionNegocioService
                .getInformacionNegocio()
                .stream()
                .findFirst()
                .orElse(new InformacionNegocio());

        model.addAttribute("informacionNegocio", informacion);

        return "informacionNegocio/quienesSomos";
    }

    @GetMapping("/informacionNegocio/listado")
    public String listado(Model model) {
        InformacionNegocio informacion = informacionNegocioService
                .getInformacionNegocio()
                .stream()
                .findFirst()
                .orElse(new InformacionNegocio());

        model.addAttribute("informacionNegocio", informacion);

        return "informacionNegocio/listado";
    }

    @PostMapping("/informacionNegocio/guardar")
    public String guardar(
            @Valid @ModelAttribute("informacionNegocio") InformacionNegocio informacionNegocio,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            return "informacionNegocio/listado";
        }

        informacionNegocioService.save(informacionNegocio);

        redirectAttributes.addFlashAttribute(
                "exito", "La información del negocio se actualizó correctamente.");

        return "redirect:/informacionNegocio/listado";
    }
}
