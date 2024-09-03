# Proyecto de Registro de Mascotas para Adopción

## Descripción

Este proyecto es una aplicación para gestionar el registro de mascotas disponibles para adopción. Permite agregar, listar y eliminar mascotas y propietarios, además de asociar mascotas a propietarios. La aplicación incluye una API REST para la interacción programática y la documentación de la API con Swagger. 
Es la versión mejorada de: https://github.com/laurabarrero98/projecto-final-pet-adoption

## Funcionalidades

### Mascotas

- **Mostrar Mascota por ID**:
  - **Endpoint**: `GET /api/mascotas/{id}`
  - **Descripción**: Devuelve los detalles de una mascota específica por su ID.

- **Mostrar Mascota por Nombre**:
  - **Endpoint**: `GET /api/mascotas?search={nombre}`
  - **Descripción**: Devuelve una lista de mascotas cuyo nombre coincide con el parámetro de búsqueda.

- **Añadir una Mascota**:
  - **Endpoint**: `POST /api/mascotas/crear`
  - **Descripción**: Permite agregar una nueva mascota con los detalles proporcionados en el cuerpo de la solicitud. Además, se le puede asignar un propietario de los disponibles en el momento de la creación.

- **Listar todas las mascotas**:
  - **Endpoint**: `GET /api/mascotas`
  - **Descripción**: Devuelve una lista de todas las mascotas registradas. Puede ordenar por ID o nombre.

- **Listar las 20 mascotas más jóvenes**:
  - **Endpoint**: `GET /api/mascotas/mas-jovenes`
  - **Descripción**: Devuelve las 20 mascotas más jóvenes ordenadas por fecha de nacimiento. En caso de haber menos de 20 mascotas, las muestra todas ordenadas por fecha de nacimiento.

- **Actualizar una Mascota**:
  - **Endpoint**: `PUT /api/mascotas/actualizar/{id}`
  - **Descripción**: Permite actualizar los detalles de una mascota existente, incluyendo la opción de añadir o cambiar el propietario.

- **Borrar una Mascota**:
  - **Endpoint**: `DELETE /api/mascotas/eliminar/{id}`
  - **Descripción**: Elimina una mascota específica por su ID.

### Propietarios

- **Añadir Propietario**:
  - **Endpoint**: `POST /api/propietarios/crear`
  - **Descripción**: Permite agregar un nuevo propietario con los detalles proporcionados. Además, en el momento de la creación se le pueden asignar mascotas.

- **Ver Propietarios**:
  - **Endpoint**: `GET /api/propietarios`
  - **Descripción**: Devuelve una lista de todos los propietarios, mostrando su nombre, email y mascotas asignadas (incluyendo nombre y foto).

- **Ver Propietario por ID**:
  - **Endpoint**: `GET /api/propietarios/{id}`
  - **Descripción**: Devuelve los detalles de un propietario específico por su ID.

- **Asociar Mascotas a Propietarios**:
  - **Descripción**: Durante la creación de propietarios, se pueden seleccionar mascotas existentes para asignarles un propietario.

## Mejoras Implementadas y Corrección de Errores

- **Manejo de Imágenes de Mascotas**:
  - Se ha mejorado la gestión de imágenes permitiendo la carga de imágenes personalizadas para las mascotas. Las imágenes se guardan con un nombre dinámico basado en el ID de la mascota (por ejemplo, `mascota{id}.jpg`).
- **Opción ver Propietarios**:
  - Se ha solucionado el problema inicial. Ahora se puede ver todos los propietarios con sus datos y mascotas. Además, puedes hacer clic en las mascotas del propietario para ver la información de las mascotas.
- **Interfaz de Usuario Mejorada**:
  - La interfaz web se ha actualizado para mejorar la usabilidad, incluyendo una mejor visualización de la información de mascotas y propietarios.

## Acceder a la Aplicación

- **Interfaz web**: [http://localhost:8080](http://localhost:8080)

## Archivos Importantes

- `application.properties`: Configuración de la base de datos y propiedades de la aplicación.
- `data.sql`: Script para insertar datos de prueba.
- `schema.sql`: Script para definir la estructura de la base de datos.

## Updates posibles:
- Poder actualizar los datos de propietarios y mascotas, ya que únicamente se puede modificar los propietarios de las mascotas. Seria útil poder cambiar la foto de la mascota u otros datos, al igual que poder actualizar información de los porpietarios, como el mail.
- Mejorar la interfaz de usuario: Modificar el formato en que se presentan los listados y las imagenes de mawscotas y añadir un menú desplegable, para mejorar su usabilidad.

