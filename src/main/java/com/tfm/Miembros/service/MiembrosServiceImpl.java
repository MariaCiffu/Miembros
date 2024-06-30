package com.tfm.Miembros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

import com.tfm.Miembros.data.MiembroRepository;
import com.tfm.Miembros.model.pojo.Miembro;
import com.tfm.Miembros.model.pojo.MiembroDto;
import com.tfm.Miembros.model.request.CreateMiembroRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class MiembrosServiceImpl implements MiembrosService {

    @Autowired
    private MiembroRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Miembro> getMiembros(String nombre, String apellido, String cargo, String tfno, String mail) {

        if (StringUtils.hasLength(nombre) || StringUtils.hasLength(apellido) || StringUtils.hasLength(cargo)
                || StringUtils.hasLength(tfno) || StringUtils.hasLength(mail)) {
            return repository.search(nombre, apellido, cargo, tfno, mail);
        }

        List<Miembro> miembros = repository.getMiembros();
        return miembros.isEmpty() ? null : miembros;
    }

    @Override
    public Miembro getMiembro(String miembroId) {
        return repository.getById(Long.valueOf(miembroId));
    }

    @Override
    public Boolean removeMiembro(String miembroId) {

        Miembro miembro = repository.getById(Long.valueOf(miembroId));

        if (miembro != null) {
            repository.delete(miembro);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Miembro createMiembro(CreateMiembroRequest request) {

        //Otra opcion: Jakarta Validation: https://www.baeldung.com/java-validation
        if (request != null && StringUtils.hasLength(request.getNombre().trim())
                && StringUtils.hasLength(request.getApellido().trim())
                && StringUtils.hasLength(request.getCargo().trim()) && StringUtils.hasLength(request.getTfno().trim())
        ) {

            Miembro miembro = Miembro.builder().nombre(request.getNombre()).apellido(request.getApellido())
                    .cargo(request.getCargo()).tfno(request.getTfno())
                    .mail(request.getMail()).build();

            return repository.save(miembro);
        } else {
            return null;
        }
    }

    @Override
    public Miembro updateMiembro(String miembroId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        Miembro miembro = repository.getById(Long.valueOf(miembroId));
        if (miembro != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(miembro)));
                Miembro patched = objectMapper.treeToValue(target, Miembro.class);
                repository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating product {}", miembroId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Miembro updateMiembro(String miembroId, MiembroDto updateRequest) {
        Miembro miembro = repository.getById(Long.valueOf(miembroId));
        if (miembro != null) {
            miembro.update(updateRequest);
            repository.save(miembro);
            return miembro;
        } else {
            return null;
        }
    }
}
