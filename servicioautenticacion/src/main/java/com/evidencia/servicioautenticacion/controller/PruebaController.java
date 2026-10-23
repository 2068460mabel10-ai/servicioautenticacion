
package com.evidencia.servicioautenticacion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para comprobar el funcionamiento del servicio web.
 */
@RestController
public class PruebaController {

    @GetMapping("/api/prueba")
    public String probarServicio() {
        return "El servicio web funciona correctamente";
    }
}