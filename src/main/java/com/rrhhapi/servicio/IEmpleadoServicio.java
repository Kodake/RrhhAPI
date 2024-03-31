package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmpleadoServicio {
    public List<Empleado> listar();
    public Page<Empleado> listarPaginado(Pageable pageable);
    public Empleado buscarPorId(Integer idEmpleado);
    public Empleado guardar(Empleado empleado);
    public void eliminar(Empleado empleado);
}
