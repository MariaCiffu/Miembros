package com.tfm.Miembros.service;


import com.tfm.Miembros.model.pojo.Miembro;
import com.tfm.Miembros.model.pojo.MiembroDto;
import com.tfm.Miembros.model.request.CreateMiembroRequest;

import java.util.List;

public interface MiembrosService {

    List<Miembro> getMiembros(String nombre, String apellido, String cargo, String tfno, String mail);

    Miembro getMiembro(String miembroId);

    Boolean removeMiembro(String miembroId);

    Miembro createMiembro(CreateMiembroRequest request);

    Miembro updateMiembro(String miembroId, String updateRequest);

    Miembro updateMiembro(String miembroId, MiembroDto updateRequest);
}
