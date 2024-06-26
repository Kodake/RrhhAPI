package com.rrhhapi.controlador;

import com.rrhhapi.excepcion.RecursoNoEncontradoExcepcion;
import com.rrhhapi.modelo.Departamento;
import com.rrhhapi.servicio.IDepartamentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rrhh")
@CrossOrigin(value = "http://localhost:5173")
public class DepartamentoControlador {
    @Autowired
    private IDepartamentoServicio departamentoServicio;

    @GetMapping("/departamentos2")
    public List<Departamento> listar() {
        return departamentoServicio.listar();
    }

    @GetMapping("/departamentos")
    public Page<Departamento> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return departamentoServicio.listarPaginado(pageable);
    }

    @PostMapping("/departamentos")
    public Departamento agregar(@RequestBody Departamento departamento) {
        return departamentoServicio.guardar(departamento);
    }

    @GetMapping("/departamentos/{id}")
    public ResponseEntity<Departamento> buscarPorId(@PathVariable Integer id) {
        Departamento departamento = departamentoServicio.buscarPorId(id);
        if (departamento == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el departamento con el id: " + id);
        }
        return ResponseEntity.ok(departamento);
    }

    @PutMapping("/departamentos/{id}")
    public ResponseEntity<Departamento> actualizar(@PathVariable Integer id, @RequestBody Departamento departamentoActualizado) {
        Departamento departamentoExistente = departamentoServicio.buscarPorId(id);
        if (departamentoExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el departamento con el id: " + id);
        }
        departamentoExistente.setNombre(departamentoActualizado.getNombre());
        departamentoServicio.guardar(departamentoExistente);
        return ResponseEntity.ok(departamentoExistente);
    }
}
