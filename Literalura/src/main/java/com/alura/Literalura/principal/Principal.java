package com.alura.Literalura.principal;
import com.alura.Literalura.model.*;
import com.alura.Literalura.repository.AutorRepository;
import com.alura.Literalura.service.ConsumoAPI;
import com.alura.Literalura.service.ConvierteDatos;
import com.alura.Literalura.repository.LibrosRepository;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class Principal{
  //Variables generales
  private Scanner teclado = new Scanner(System.in);
  private ConsumoAPI consumoApi = new ConsumoAPI();
  private ConvierteDatos conversor = new ConvierteDatos();
 // private List<DatosDeLibro> datosLibro = new ArrayList<>();
  private final String URL_BASE ="https://gutendex.com/books/?search=";
  String linea ="||**********************************||";
  private LibrosRepository librosRepository;
  private AutorRepository autorRepository;

  public Principal(LibrosRepository librosRepository, AutorRepository autorRepository){
    this.librosRepository=librosRepository;
    this.autorRepository=autorRepository;
  }

  public void muestraElMenu(){

    System.out.println(linea);
    System.out.println("BIEN VENIDO AL PROYECTO LITERALURA");
    System.out.println(linea);

    int opcion = -1;
    while (opcion != 0){
      System.out.println("📚 CATÁLOGO DE LIBROS");
      System.out.println("1 - 📖 Buscar y guardar libro por título");
      System.out.println("2 - 🔎 listar libros registrados");
      System.out.println("3 - 👨‍💻 Listar Autor(es) Registrado(s)");
      System.out.println("4 - 📅 Listar Autor(es) En determinado Año");
      System.out.println("5 - 🌍 Listrar libros por idioma");
      System.out.println("0 - 🚪 Salir");

      System.out.print("Seleccione una opción: ");

      opcion = teclado.nextInt();
      teclado.nextLine(); // limpiar el buffer
      switch (opcion) {
        case 1:
            buscarYMostrarLibro();
          break;
        case 2:
          librosRegistrados();
          break;
        case 3:
          autoresRegistrados();
          break;
        case 4:
          autoresPorAnio();
          break;
        case 5:
          listarLibroPorIdioma();
          break;
        case 0:
          System.out.println("👋 ¡Hasta luego!");
          break;
        default:
          System.out.println("❌ Opción inválida. Intente de nuevo.");
          break;
      }
    }
  }

  private Datos getDatosLibro(){

    String titulo = teclado.nextLine();
    var json = consumoApi.obtenerDatos( URL_BASE+ titulo.replace(" ","%20"));

    Datos datos = conversor.obtenerDatos(json, Datos.class);
    return datos;
  }

  private Libro crearLibro(DatosDeLibros datosDeLibros, Autor autor) {
    if (autor != null) {
      return new Libro(datosDeLibros, autor);
    } else {
      System.out.println("El autor es null, no se puede crear el libro");
      return null;
    }
  }

  private void buscarYMostrarLibro(){
    System.out.print("🔍 Ingrese el título a buscar: ");
    Datos datos = getDatosLibro();

    if (!datos.resultados().isEmpty() && !datos.resultados().get(0).autor().isEmpty()) {

      DatosDeLibros datosDeLibros = datos.resultados().get(0);
      DatosAutor datosAutor = datosDeLibros.autor().get(0);

      Libro libro = null;
      Libro libroRepositorio = librosRepository.findByTitulo(datosDeLibros.titulo());

      if (libroRepositorio != null) {
        System.out.println("Este libro ya se encuentra en la base de datos");
        System.out.println(libroRepositorio.toString());
      } else {
        Autor autorRepositorio = autorRepository.findByNombreIgnoreCase(datosDeLibros.autor().get(0).nombre());
        if (autorRepositorio != null) {

          libro = crearLibro(datosDeLibros, autorRepositorio);
          librosRepository.save(libro);
          System.out.println("----- LIBRO AGREGADO -----");
          System.out.println(libro);
        } else {
          Autor autor = new Autor(datosAutor);
          autor = autorRepository.save(autor);
          libro = crearLibro(datosDeLibros, autor);
          librosRepository.save(libro);
          System.out.println("----- LIBRO AGREGADO -----");
          System.out.println(libro);
        }
      }
    }
    else {
      System.out.println("❌ No se encontró autor o libro. Intenta con otro título.");
    }
  }


  private void librosRegistrados() {
    List<Libro> libros = librosRepository.findAll();
    if (libros.isEmpty()) {
      System.out.println("No hay libros registrados");
      return;
    }
    System.out.println("----- LOS LIBROS REGISTRADOS SON: -----\n");
    libros.stream()
            .sorted(Comparator.comparing(Libro::getTitulo))
            .forEach(System.out::println);
  }

  private void autoresRegistrados() {
    List<Autor> autor = autorRepository.findAll();
    if (autor.isEmpty()) {
      System.out.println("No hay autores registrados");
      return;
    }
    System.out.println("|***** LOS AUTORES REGISTRADOS SON: *****|\n");
    autor.stream()
            .sorted(Comparator.comparing(Autor::getNombre))
            .forEach(System.out::println);
  }

  private void autoresPorAnio() {
    System.out.println("Escribe el año en el que deseas buscar: ");
    var anio = teclado.nextInt();
    teclado.nextLine();
    if(anio < 0) {
      System.out.println("El año no puede ser negativo, intenta de nuevo");
      return;
    }
    List<Autor> autoresPorAnio = autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
    if (autoresPorAnio.isEmpty()) {
      System.out.println("No hay autores registrados en ese año");
      return;
    }
    System.out.println("----- LOS AUTORES VIVOS REGISTRADOS EN EL AÑO " + anio + " SON: -----\n");
    autoresPorAnio.stream()
            .sorted(Comparator.comparing(Autor::getNombre))
            .forEach(System.out::println);
  }

  private void  listarLibroPorIdioma() {
    System.out.println("Escribe el idioma por el que deseas buscar: ");

    System.out.println("es - Español");
    System.out.println("en - Inglés");
    System.out.println("fr - Francés");
    System.out.println("pt - Portugués");
    var idioma = teclado.nextLine();
    if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
      System.out.println("Idioma no válido, intenta de nuevo");
      return;
    }
    List<Libro> librosPorIdioma = librosRepository.findByIdiomasContaining(idioma);
    if (librosPorIdioma.isEmpty()) {
      System.out.println("No hay libros registrados en ese idioma");
      return;
    }
    System.out.println("----- LOS LIBROS REGISTRADOS EN EL IDIOMA SELECCIONADO SON: -----\n");
    librosPorIdioma.stream()
            .sorted(Comparator.comparing(Libro::getTitulo))
            .forEach(System.out::println);
  }
}