package com.rrhhapi.repositorio;

import com.rrhhapi.modelo.Sueldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISueldoRepositorio extends JpaRepository<Sueldo, Integer> {
}
