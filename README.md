# Kamaky Products

Aplicación web de repostería artesanal para consultar el catálogo, ver promociones, comprar con un carrito, hacer pedidos y solicitar productos personalizados. El administrador gestiona productos, categorías, promociones, pedidos e información del negocio.

Proyecto del curso **Desarrollo de Aplicaciones Web y Patrones**, Universidad Fidélitas.

## Integrantes
- Katherine Arias Molina
- Luis Sebastián Caravaca Rojas
- Roberto Arias Badilla
- Minor Gabriel Segura Solano

## Tecnologías
Java 21, Spring Boot 4.1.1, Thymeleaf, Spring Data JPA, MySQL 8, Bootstrap 5.3, Font Awesome, Firebase Storage y Lombok.

## Cómo ejecutarlo
1. Ejecutar completo el script `kamaky.sql` en MySQL Workbench con el usuario `root`. El script crea la base `kamaky`, el usuario `usuario_kamaky`, las tablas y los datos de prueba.
2. Colocar la llave de Firebase del grupo en `src/main/resources/firebase/`. La llave no se sube a GitHub, se comparte de forma privada.
3. Abrir el proyecto en NetBeans con JDK 21, hacer Clean and Build y Run.
4. Entrar a **http://localhost**.

## Forma de trabajo
Cada integrante trabaja en su propia rama `feature/...` y abre un Pull Request hacia `main`. Otro integrante revisa y aprueba el Pull Request antes de unirlo.

## Avance 2
| Integrante | Historias de usuario |
|---|---|
| Luis | HU-009, HU-010, HU-016, HU-019 |
| Minor | HU-001, HU-003, HU-013, HU-015 |
| Roberto | HU-004, HU-005, HU-006, HU-007, HU-008, HU-017 |
| Katherine | HU-011, HU-012, HU-020 |