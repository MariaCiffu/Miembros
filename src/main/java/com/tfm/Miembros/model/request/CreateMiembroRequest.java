package com.tfm.Miembros.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMiembroRequest {
    private String nombre;

    private String apellido;

    private String cargo;

    private String tfno;

    private String mail;
}
