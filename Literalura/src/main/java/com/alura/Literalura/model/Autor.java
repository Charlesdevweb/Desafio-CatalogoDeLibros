package com.alura.Literalura.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private int anioNacimiento;
  private int anioFallecimiento;

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Libro> libros = new ArrayList<>();

  public Autor(DatosAutor datosAutor) {
    this.nombre = datosAutor.nombre();
    this.anioNacimiento = datosAutor.anioNacimiento();
    this.anioFallecimiento = datosAutor.anioFallecimiento();
  }

  // constructor vacío
  public Autor() {}

  // Getters, setters

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getAnioNacimiento() {
    return anioNacimiento;
  }

  public void setAnioNacimiento(int anioNacimiento) {
    this.anioNacimiento = anioNacimiento;
  }

  public int getAnioFallecimiento() {
    return anioFallecimiento;
  }

  public void setAnioFallecimiento(int anioFallecimiento) {
    this.anioFallecimiento = anioFallecimiento;
  }

  public List<Libro> getLibros() {
    return libros;
  }

  public void setLibros(List<Libro> libros) {
    this.libros = libros;
  }

  // Obtener el nombre de un libro
  @Override
  public String toString() {
    StringBuilder librosTitulos = new StringBuilder();
    for (Libro libro : libros) {
      librosTitulos.append(libro.getTitulo()).append(", ");
    }

    // quita la coma al final y el espacio
    if (librosTitulos.length() > 0) {
      librosTitulos.setLength(librosTitulos.length() - 2);
    }

    return  "|************ AUTOR 👨‍🏫 ************|" + "\n" +
            "Autor: " + nombre + "\n" +
            "Fecha de nacimiento: " + anioNacimiento + "\n" +
            "Fecha de fallecimiento: " + anioFallecimiento + "\n" +
            "Libros: " + librosTitulos + "\n";
  }
}
