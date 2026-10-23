Evidencia AA5-EV01: Servicio web de autenticación

Descripción

Aplicación REST desarrollada con Java 17 y Spring Boot para registrar usuarios e iniciar sesión mediante usuario y contraseña.

Funcionalidades

- Registro de usuarios.
- Validación de campos obligatorios.
- Protección de contraseñas mediante BCrypt.
- Inicio de sesión con validación de credenciales.
- Respuesta HTTP 401 cuando la autenticación falla.

Endpoints

Comprobar el servicio

- Método: GET
- Ruta: "/api/prueba"

Registrar usuario

- Método: POST
- Ruta: "/api/auth/registro"
- Formato JSON:

{
  "usuario": "mabel",
  "contrasena": "Clave123"
}

Iniciar sesión

- Método: POST
- Ruta: "/api/auth/login"
- Formato JSON:

{
  "usuario": "mabel",
  "contrasena": "Clave123"
}

Pruebas realizadas

1. Servicio web disponible y funcionando.
2. Registro de usuario exitoso.
3. Inicio de sesión exitoso con credenciales correctas.
4. Rechazo de contraseña incorrecta mediante HTTP 401.

Tecnologías

- Java 17
- Spring Boot
- Maven
- API REST
- JSON
- BCrypt
- Git para el control de versiones

Nota

Los usuarios se almacenan temporalmente en memoria. Al reiniciar la aplicación, los registros se pierden. Este almacenamiento se utiliza para las pruebas de la evidencia.