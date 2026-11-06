package com.kamaky.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

//Promoción del negocio (HU-010 consultar, HU-016 gestionar)
@Getter
@Setter
@Entity
@Table(name = "promocion")
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @Column(nullable = false, length = 60)
    @NotBlank
    @Size(max = 60)
    private String titulo;

    @Column(nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String descripcion;

    @NotNull
    @Min(1)
    private Integer porcentajeDescuento;

    //El usuario escribe la fecha con formato dd/MM/yyyy
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean activa;
}
