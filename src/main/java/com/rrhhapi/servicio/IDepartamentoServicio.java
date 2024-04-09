package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDepartamentoServicio {
    public List<Departamento> listar();
    public Page<Departamento> listarPaginado(Pageable pageable);
    public Departamento buscarPorId(Integer idDepartamento);
    public Departamento guardar(Departamento departamento);
}
