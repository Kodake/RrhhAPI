package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Empleado;
import com.rrhhapi.modelo.Sueldo;
import com.rrhhapi.repositorio.IEmpleadoRepositorio;
import com.rrhhapi.repositorio.ISueldoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {
    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Override
    public List<Empleado> listar() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Page<Empleado> listarPaginado(Pageable pageable) {
        return empleadoRepositorio.findAll(pageable);
    }

    @Override
    public Empleado buscarPorId(Integer idEmpleado) {
        return empleadoRepositorio.findById(idEmpleado).orElse(null);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    @Override
    public void eliminar(Empleado empleado) {
        empleadoRepositorio.delete(empleado);
    }
}
