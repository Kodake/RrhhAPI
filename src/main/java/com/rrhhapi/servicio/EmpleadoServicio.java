package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Empleado;
import com.rrhhapi.repositorio.IEmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
