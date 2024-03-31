package com.rrhhapi.controlador;

import com.rrhhapi.excepcion.RecursoNoEncontradoExcepcion;
import com.rrhhapi.modelo.Empleado;
import com.rrhhapi.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/rrhh")
@CrossOrigin(value = "http://localhost:5173")
public class EmpleadoControlador {
    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @GetMapping("/empleados2")
    public List<Empleado> listar() {
        return empleadoServicio.listar();
    }

    @GetMapping("/empleados")
    public Page<Empleado> listarPaginado(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return empleadoServicio.listarPaginado(pageable);
    }

    @PostMapping("/empleados")
    public Empleado agregar(@RequestBody Empleado empleado) {
        return empleadoServicio.guardar(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado con el id: " + id);
        }
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Integer id, @RequestBody Empleado empleadoActualizado) {
        Empleado empleadoExistente = empleadoServicio.buscarPorId(id);
        if (empleadoExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado con el id: " + id);
        }
        empleadoExistente.setNombre(empleadoActualizado.getNombre());
        empleadoExistente.setDepartamento(empleadoActualizado.getDepartamento());
        empleadoExistente.setSueldo(empleadoActualizado.getSueldo());
        empleadoServicio.guardar(empleadoExistente);
        return ResponseEntity.ok(empleadoExistente);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminar(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado con el id: " + id);
        }
        empleadoServicio.eliminar(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
