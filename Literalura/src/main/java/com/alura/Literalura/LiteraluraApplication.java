package com.alura.Literalura;

import com.alura.Literalura.principal.Principal;
import com.alura.Literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alura.Literalura.repository.LibrosRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

  @Autowired
  private LibrosRepository librosRepository;
  @Autowired
  private AutorRepository autorRepository;

  public static void main(String[] args) {

    SpringApplication.run(LiteraluraApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

      Principal principal = new Principal(librosRepository, autorRepository);
      principal.muestraElMenu();
    }
}