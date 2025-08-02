package com.alura.Literalura.model;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;

import java.util.Arrays;
import java.util.List;

 @Entity
 @Table(name="libros")
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

   @ManyToOne
   @JoinColumn(name = "autor_id", nullable = false)
   private Autor autor;

   @Column(name = "nombre_autor")
   private String nombre;

   @Column(name = "idiomas")
   private String idiomas;
   private double descargas;
   //Constructor vacio
   public Libro() {}

   public Libro(DatosDeLibros datosDeLibros, Autor autor) {
     this.titulo = datosDeLibros.titulo();
     this.descargas = datosDeLibros.descargas();
     this.nombre = autor.getNombre();
     this.autor = autor;
     this.setIdiomas(datosDeLibros.idiomas());

   }

  // getters y setters...
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }


   @Transient
   public List<String> getIdiomas() {

     return Arrays.asList(idiomas.split(","));
   }
   @Transient
   public void setIdiomas(List<String> idiomas) {
     this.idiomas = String.join(",", idiomas);
   }

   @Transient
  public double getDescargas() {

    return descargas;
  }

   @Transient
  public void setDescargas(double descargas) {

    this.descargas = descargas;
  }

 public Autor getAutor() {
   return autor;
 }

   public void setAutor(Autor autor) {
     this.autor = autor;
   }
   @Override
   public String toString() {
     return "--------------- LIBRO 📖 ---------------" + "\n" +
             "Título: " + titulo + "\n" +
             "Autor: " + nombre + "\n" +
             "Idioma: " + idiomas + "\n" +
             "Número de descargas: " + descargas + "\n" +
             "------------------------------------" + "\n";
   }
}
