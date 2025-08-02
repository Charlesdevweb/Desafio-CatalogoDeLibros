package com.alura.Literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alura.Literalura.model.Libro;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libro, Long> {
  Libro findByTitulo(String titulo);
  List<Libro> findByIdiomasContaining(String idiomas);

}



