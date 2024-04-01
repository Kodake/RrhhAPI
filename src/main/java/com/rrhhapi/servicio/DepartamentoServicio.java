package com.rrhhapi.servicio;

import com.rrhhapi.modelo.Departamento;
import com.rrhhapi.repositorio.IDepartamentoRepositorio;
import com.rrhhapi.repositorio.IEmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServicio implements IDepartamentoServicio {
    @Autowired
    private IDepartamentoRepositorio departamentoRepositorio;

    @Override
    public List<Departamento> listar() {
        return departamentoRepositorio.findAll();
    }

    @Override
    public Page<Departamento> listarPaginado(Pageable pageable) {
        return departamentoRepositorio.findAll(pageable);
    }

    @Override
    public Departamento buscarPorId(Integer idDepartamento) {
        return departamentoRepositorio.findById(idDepartamento).orElse(null);
    }

    @Override
    public Departamento guardar(Departamento departamento) {
        return departamentoRepositorio.save(departamento);
    }
}
