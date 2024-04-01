package com.rrhhapi.repositorio;

import com.rrhhapi.modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoRepositorio extends JpaRepository<Departamento, Integer> {
}
