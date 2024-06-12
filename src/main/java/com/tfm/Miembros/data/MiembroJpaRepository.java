package com.tfm.Miembros.data;

import com.tfm.Miembros.model.pojo.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MiembroJpaRepository extends JpaRepository<Miembro, Long>, JpaSpecificationExecutor<Miembro> {
}
