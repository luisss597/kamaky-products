package com.kamaky.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author karias
 */

@Getter
@Setter
@Entity
@Table(name = "informacion_negocio")
public class InformacionNegocio implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacion")
    private Integer idInformacion;
    
    @Column(nullable = false, length =  100)
    @NotBlank
    @Size(max = 100)
    private String nombre; 
    
    @Column(columnDefinition = "TEXT")
    private String descripcion; 
    
    @Column(length = 20)
    private String telefono;
    
    @Column(length = 100)
    @Email
    @Size(max = 100)
    private String correo; 
    
    @Column(length = 150)
    private String ubicacion; 
    
    @Column(length = 150)
    private String whatsapp; 
    
    @Column(length = 150)
    private String instagram; 
    
    @Column(length = 150)
    private String facebook; 
    

    
}
