package com.tfm.Miembros.model.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MiembroDto {
    private String nombre;

    private String apellido;

    private String cargo;

    private String tfno;

    private String mail;
}
