package com.rrhhapi.dto;

import lombok.Data;

@Data
public class GetEmpleadoDTO {
    Integer idEmpleado;
    String nombre;
    String departamento;
    Double sueldo;
}
