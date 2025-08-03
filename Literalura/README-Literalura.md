
# 📚 Literalura - Catálogo de Libros

Literalura es una aplicación de consola desarrollada en **Java + Spring Boot**, que permite consultar libros desde la API pública de [Gutendex](https://gutendex.com/) y almacenarlos en una base de datos local. Puedes buscar libros por título, ver autores registrados, listar libros por idioma y más.

Proyecto desarrollado como parte del programa **Oracle ONE - Alura Latam**.

---

## 🎯 Funcionalidades principales

- 🔎 Buscar libros por título desde la API.
- 💾 Guardar libros y autores en base de datos.
- 📚 Listar todos los libros registrados.
- 👨‍🏫 Listar autores.
- 🗓️ Filtrar autores vivos en un año específico.
- 🌍 Filtrar libros por idioma (en formato ISO: `en`, `es`, `fr`...).
- ⚠️ Evita duplicados (por título).
- 🧹 Resetear la base de datos fácilmente.

---

## 🧪 Tecnologías usadas

- Java 23
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- Jackson (para deserializar JSON)  
- API externa: [Gutendex](https://gutendex.com)  
- PostgresSQL como base de datos

---

## 🧭 Menú principal (vista en consola)

```
🌐 - Catálogo de libros
----------------------------------
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un año específico
5. Listar libros por idioma
0. Salir
Seleccione una opción:
```

---

## 🗂️ Estructura del proyecto

```
com.alura.literalura
│
├── model         → Entidades JPA (Libro, Autor)
├── repository    → Interfaces JPA (LibroRepository, AutorRepository)
├── principal     → Clase main y lógica del menú que enlaza a la Clse app con metodo main,
├── service       → Lógica para consumir la API Gutendex
```

---

## 🧠 Diagrama de Clases (simplificado)

```
Autor
 ├── id: Long
 ├── nombre: String
 ├── anioNacimiento: int
 ├── anioFallecimiento: int
 └── libros: List<Libro>
        ▲
        │
Libro
 ├── id: Long
 ├── titulo: String
 ├── idiomas: String
 ├── descargas: Double
 └── autor: Autor
```

---

## ⚙️ Cómo ejecutar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/literalura.git
cd literalura
```

2. Abre el proyecto en tu IDE (IntelliJ, Eclipse o VS Code con Spring Plugin).

3. Verifica que tu JDK sea 17 o superior.

4. Ejecuta la clase con el método `main` (por ejemplo: `Principal.java` o `LiteraluraApplication.java`).

---

## ⚙️ Configuración de base de datos

En `src/main/resources/application.properties`:

```properties
# Para borrar y crear tablas en cada reinicio
spring.jpa.hibernate.ddl-auto=create

# H2 en memoria
spring.datasource.url=jdbc:h2:mem:literalura-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Mostrar SQL en consola
spring.jpa.show-sql=true
```

---

## ✅ Cómo probar la aplicación

**Caso de prueba básico:**

1. Ejecuta la aplicación.
2. En el menú, elige la opción 1.
3. Escribe un título conocido como: `Don Quijote`.
4. Verifica que el libro se guarda correctamente.
5. Usa la opción 2 para ver todos los libros.
6. Usa la opción 3 para ver los autores.
7. Usa la opción 4 e ingresa el año `1605` para ver si el autor estaba vivo en ese año.
8. Usa la opción 5 e ingresa `es` (idioma español) para filtrar libros.

---

## ❗ Problemas comunes

| Error | Solución |
|------|----------|
| `PropertyReferenceException` | Asegúrate de que las propiedades buscadas en los métodos de los repositorios existen con ese nombre exacto en las clases. |
| `null` en autor o idioma | Revisa el constructor de `Autor` y `Libro`. Asegúrate de que se usan correctamente los objetos `DatosAutor` y `DatosDeLibros`. |
| Columna extra `idioma` | Si renombraste `idioma` a `idiomas`, limpia la base de datos o cambia `ddl-auto=create` temporalmente. |
| No se encuentra un libro | Muestra un mensaje con `System.out.println("El libro no aparece, ingresa otro");` para manejar el caso de lista vacía. |

---

## 🙋 Autor

Desarrollado por **[Carlos Rivera]** como parte del curso **Back-End Java con Spring Boot** - Oracle ONE + Alura Latam.
