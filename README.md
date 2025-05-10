# task-api 
This API is designed to provide a tool that helps manage activities. It offers functionalities such as registering users, creating projects and activities, adding members to projects, viewing activities by user or project, deleting activities or projects, updating activities, and general project management.

## Requirements
Make sure you have the following installed:
- Java 17 (recommended: OpenJDK 17.0.12)
- Apache Maven 3.9.9
- MySQL

## Configuration
- Set the database credentials in the application.properties file, located at:
  src/main/resources/application.properties
- Modify the following lines with your local configuration:
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  
## Running
- In the root of the project, run the following command:
  mvn spring-boot:run
- This will start the embedded Spring Boot server (Tomcat) on the default port 8080
  http://localhost:8080

## Test the API with Postman
- You can test the API endpoints using the Postman collection located at:
  postman/TASKS.postman_collection.json
  ![image](https://github.com/user-attachments/assets/3d914354-f26e-42b7-8af2-f18d1818eef5)

**CONSIDERATIONS**:
  - After exporting the collection, you must take into account that it is necessary to create a user. To do this, send a request to the public user creation endpoint:
  ![image](https://github.com/user-attachments/assets/5549ec8e-dd17-42dc-89f4-ba791d67ed42)
  
 The endpoint /api/public/user/register creates users without needing to be authenticated. However, if there is already at least one user in the database, then all users created through this endpoint will automatically be assigned the "USER" role.
If there are no users in the database, the first user created will be assigned the "ADMIN" role. After the first user is created, all subsequent users will be assigned the "USER" role.
  - Once the first user is created, it is necessary to log in. To do this, send a request to the endpoint:
  ![image](https://github.com/user-attachments/assets/cbe1eebf-bccb-4d7d-83b3-7e88691687f3)

**NOTE**:
  You must include the token in all requests located in the folder named "PRIVATE"
  
## Roles
There are two types of roles: "ADMIN" and "USER"
  - **ADMIN**: Can access all API features, as long as the data belongs to projects in which the user is a member.
  - **USER**: Can access limited endpoints, as they cannot update information belonging to other users. However, they can view projects, project members, tasks, task members, and general information of the projects they belong to.

## Credits
Developed by Diana Cruz
© 2025

# task-api 
Esta API está diseñada para proporcionar una herramienta que ayude a gestionar actividades. Ofrece funcionalidades como el registro de usuarios, la creación de proyectos y actividades, la incorporación de miembros a proyectos, la visualización de actividades por usuario o proyecto, la eliminación de actividades o proyectos, la actualización de actividades y la gestión general de proyectos.

## Requisitos
Asegúrate de tener instalado lo siguiente:
- Java 17 (recomendado: OpenJDK 17.0.12)
- Apache Maven 3.9.9
- MySQL

## Configuración
- Configura las credenciales de la base de datos en el archivo application.properties, ubicado en:
  src/main/resources/application.properties
- Modifica las siguientes líneas con tu configuración local:
  spring.datasource.url=jdbc:mysql://localhost:3306/nombre_de_tu_bd
  spring.datasource.username=tu_usuario
  spring.datasource.password=tu_contraseña
  
## Ejecución
- En la raíz del proyecto, corre el siguiente comando:
  mvn spring-boot:run
- Esto iniciará el servidor embebido de Spring Boot (Tomcat) en el puerto por defecto 8080
  http://localhost:8080

## Probar la API con Postman
- Puedes probar los endpoints del API, usando la colección de postman que se encuentra en:
  ![image](https://github.com/user-attachments/assets/90353a5e-3ec4-4a15-8774-fc5a62488874)
  postman/TASKS.postman_collection.json

**CONSIDERACIONES**:
  - Luego de exportar la colección, debes tomar en cuenta que es necesario crear un usuario, para esto debes enviar una petición al endpoint de crear usuario de manera publica:
  ![image](https://github.com/user-attachments/assets/5549ec8e-dd17-42dc-89f4-ba791d67ed42)
  
  El endpoint de "/api/public/user/register", crea a usuarios sin necesidad de estar autenticado, pero con la condición de, SI EXISTE UN USUARIO EN LA BASE DE DATOS, automaticamente todos los usuarios que sean creados por este endpoint, seran asignados con un rol de "USER", en caso de que la existencia de usuarios en la base de datos sea nula, el usuario que se crearía sería "ADMIN", luego de la creación del primer usuario, todos los demás usuarios se les asignara el rol "USER"
- Una vez creado el primer usuario es necesario iniciar sesión, para esto debes enviar una petición al endpoint:
  ![image](https://github.com/user-attachments/assets/cbe1eebf-bccb-4d7d-83b3-7e88691687f3)

**NOTA**:
Es necesario colocar el token a las peticiones que se encuentren en el folder de nombre "PRIVATE"
  
## Roles
  Hay dos tipos de roles, "ADMIN" y "USER"
- **ADMIN**: Puede acceder a todas las funcionalidades del API, siempre y cuando sean datos de proyectos en los que es miembro.
- **USER**: Puede acceder a endpoints límitados, ya que no puede actualizar información ajena a dicho usuario, sin embargo puede visualizar proyectos, miembros de proyectos, tareas, y miembros de las tareas e información general de los proyectos al que es miembro.

## Créditos
Desarrollado por Diana Cruz
© 2025

