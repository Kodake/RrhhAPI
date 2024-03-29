package com.rrhhapi.repositorio;

import com.rrhhapi.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
}
