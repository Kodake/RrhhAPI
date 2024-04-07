package com.rrhhapi.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sueldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idSueldo;
    Double cantidad;
}