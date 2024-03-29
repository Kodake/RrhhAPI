package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Empleado;

import java.util.List;

public interface IEmpleadoServicio {
    public List<Empleado> listar();
    public Empleado buscarPorId(Integer idEmpleado);
    public Empleado guardar(Empleado empleado);
    public void eliminar(Empleado empleado);
}
