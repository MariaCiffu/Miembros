package com.tfm.Miembros.model.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "miembros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "tfno")
    private String tfno;

    @Column(name = "mail")
    private String mail;
    public void update(MiembroDto miembroDto) {
        this.nombre = miembroDto.getNombre();
        this.apellido = miembroDto.getApellido();
        this.cargo = miembroDto.getCargo();
        this.tfno = miembroDto.getTfno();
        this.mail = miembroDto.getMail();
    }
}
