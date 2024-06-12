package com.tfm.Miembros.data;


import com.tfm.Miembros.data.utils.SearchCriteria;
import com.tfm.Miembros.data.utils.SearchOperation;
import com.tfm.Miembros.data.utils.SearchStatement;
import com.tfm.Miembros.model.pojo.Miembro;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MiembroRepository {

    private final MiembroJpaRepository repository;

    public List<Miembro> getMiembros() {
        return repository.findAll();
    }

    public Miembro getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Miembro save(Miembro miembro) {
        return repository.save(miembro);
    }

    public void delete(Miembro miembro) {
        repository.delete(miembro);
    }

    public List<Miembro> search(String nombre, String apellido, String cargo, String tfno, String mail) {
        SearchCriteria<Miembro> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(nombre)) {
            spec.add(new SearchStatement("nombre", nombre, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(apellido)) {
            spec.add(new SearchStatement("apellido", apellido, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(cargo)) {
            spec.add(new SearchStatement("cargo", cargo, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(tfno)) {
            spec.add(new SearchStatement("tfno", tfno, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(mail)) {
            spec.add(new SearchStatement("mail", mail, SearchOperation.MATCH));
        }
        return repository.findAll(spec);
    }

}
