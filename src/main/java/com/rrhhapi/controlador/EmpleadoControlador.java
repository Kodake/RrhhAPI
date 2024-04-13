package com.rrhhapi.controlador;

import com.rrhhapi.dto.EmpleadoDTO;
import com.rrhhapi.dto.GetEmpleadoDTO;
import com.rrhhapi.excepcion.RecursoNoEncontradoExcepcion;
import com.rrhhapi.modelo.Departamento;
import com.rrhhapi.modelo.Empleado;
import com.rrhhapi.modelo.Sueldo;
import com.rrhhapi.servicio.IEmpleadoServicio;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/rrhh")
@CrossOrigin(value = "http://localhost:5173")
public class EmpleadoControlador {
    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @GetMapping("/empleados2")
    public List<GetEmpleadoDTO> listar() {
        List<Empleado> empleados = empleadoServicio.listar();

        List<GetEmpleadoDTO> getEmpleadoDTO = empleados.stream()
                .map(empleado -> {
                    GetEmpleadoDTO productoDTO = modelMapper.map(empleado, GetEmpleadoDTO.class);
                    productoDTO.setSueldo(empleado.getSueldo().getCantidad());
                    productoDTO.setDepartamento(empleado.getDepartamento().getNombre());
                    return productoDTO;
                })
                .collect(Collectors.toList());

        return getEmpleadoDTO;
    }

    @GetMapping("/empleados")
    public Page<GetEmpleadoDTO> listarPaginado(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Empleado> empleadosPage = empleadoServicio.listarPaginado(pageable);

        Page<GetEmpleadoDTO> empleadoDTOPage = empleadosPage.map(empleado -> {
            GetEmpleadoDTO getEmpleadoDTO = modelMapper.map(empleado, GetEmpleadoDTO.class);
            getEmpleadoDTO.setSueldo(empleado.getSueldo().getCantidad());
            getEmpleadoDTO.setDepartamento(empleado.getDepartamento().getNombre());
            return getEmpleadoDTO;
        });

        return empleadoDTOPage;
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con el id: " + id);
        }

        EmpleadoDTO empleadoDTO = modelMapper.map(empleado, EmpleadoDTO.class);
        empleadoDTO.setSueldo(empleado.getSueldo().getCantidad());
        empleadoDTO.setDepartamento(empleado.getDepartamento().getIdDepartamento());

        return ResponseEntity.ok(empleadoDTO);
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> agregar(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = mapToEmpleado(empleadoDTO);
        Empleado empleadoGuardado = empleadoServicio.guardar(empleado);
        EmpleadoDTO respuesta = mapToEmpleadoDTO(empleadoGuardado);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Integer id, @RequestBody EmpleadoDTO empleadoActualizadoDTO) {
        Empleado empleadoExistente = existe(id);
        actualizarEmpleadoNuevo(empleadoExistente, empleadoActualizadoDTO);
        Empleado empleadoGuardado = empleadoServicio.guardar(empleadoExistente);
        EmpleadoDTO respuesta = mapToEmpleadoDTO(empleadoGuardado);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminar(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con el id: " + id);
        }
        empleadoServicio.eliminar(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    private Empleado mapToEmpleado(EmpleadoDTO empleadoDTO) {
        Empleado empleado = modelMapper.map(empleadoDTO, Empleado.class);
        empleado.setDepartamento(crearDepartamento(empleadoDTO.getDepartamento()));
        empleado.setSueldo(crearSueldo(empleadoDTO.getSueldo()));
        return empleado;
    }

    private EmpleadoDTO mapToEmpleadoDTO(Empleado empleado) {
        return modelMapper.map(empleado, EmpleadoDTO.class);
    }

    private Departamento crearDepartamento(Integer idDepartamento) {
        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(idDepartamento);
        return departamento;
    }

    private Sueldo crearSueldo(Double cantidad) {
        Sueldo sueldo = new Sueldo();
        sueldo.setCantidad(cantidad);
        return sueldo;
    }

    private void actualizarSueldo(Empleado empleadoExistente, Double cantidad) {
        Sueldo sueldoExistente = empleadoExistente.getSueldo();
        if (cantidad != null) {
            if (sueldoExistente == null) {
                sueldoExistente = new Sueldo();
                empleadoExistente.setSueldo(sueldoExistente);
            }
            sueldoExistente.setCantidad(cantidad);
        }
    }

    private Empleado existe(Integer id) {
        Empleado empleadoExistente = empleadoServicio.buscarPorId(id);
        if (empleadoExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con el id: " + id);
        }
        return empleadoExistente;
    }

    private void actualizarEmpleadoNuevo(Empleado empleadoExistente, EmpleadoDTO empleadoActualizadoDTO) {
        empleadoExistente.setNombre(empleadoActualizadoDTO.getNombre());
        empleadoExistente.setDepartamento(crearDepartamento(empleadoActualizadoDTO.getDepartamento()));
        actualizarSueldo(empleadoExistente, empleadoActualizadoDTO.getSueldo());
    }
}
