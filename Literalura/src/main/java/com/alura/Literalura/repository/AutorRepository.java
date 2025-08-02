package com.alura.Literalura.repository;
import com.alura.Literalura.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

  Autor findByNombreIgnoreCase(String nombre);
  List<Autor>findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(
          int anioInicial,
          int anioFinal
  );
}
