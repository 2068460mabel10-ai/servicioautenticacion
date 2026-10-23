package com.evidencia.servicioautenticacion.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Servicio REST para registrar usuarios e iniciar sesión.
 */
@RestController
@RequestMapping("/api/auth")
public class AutenticacionController {

    // Almacena temporalmente los usuarios y sus contraseñas protegidas.
    private final Map<String, String> usuarios = new ConcurrentHashMap<>();

    // Herramienta que protege las contraseñas mediante BCrypt.
    private final BCryptPasswordEncoder codificador =
            new BCryptPasswordEncoder();

    /**
     * Registra un usuario nuevo.
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(
            @RequestBody Map<String, String> datos) {

        String usuario = datos.get("usuario");
        String contrasena = datos.get("contrasena");

        // Verifica que los campos no estén vacíos.
        if (usuario == null || usuario.isBlank()
                || contrasena == null || contrasena.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje",
                            "Debe ingresar usuario y contrasena"));
        }

        // Protege la contraseña antes de guardarla.
        String contrasenaProtegida = codificador.encode(contrasena);

        // Evita registrar un usuario que ya existe.
        if (usuarios.putIfAbsent(usuario, contrasenaProtegida) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El usuario ya existe"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuario registrado correctamente"));
    }

    /**
     * Verifica las credenciales para iniciar sesión.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> iniciarSesion(
            @RequestBody Map<String, String> datos) {

        String usuario = datos.get("usuario");
        String contrasena = datos.get("contrasena");

        // Obtiene la contraseña protegida del usuario registrado.
        String contrasenaGuardada = usuario == null
                ? null : usuarios.get(usuario);

        // Compara la contraseña recibida con la protegida.
        if (contrasena != null && contrasenaGuardada != null
                && codificador.matches(contrasena, contrasenaGuardada)) {
            return ResponseEntity.ok(
                    Map.of("mensaje", "Autenticacion satisfactoria"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Error en la autenticacion"));
    }
}