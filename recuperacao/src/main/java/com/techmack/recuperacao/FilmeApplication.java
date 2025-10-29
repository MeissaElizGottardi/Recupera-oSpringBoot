package com.techmack.recuperacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmeApplication.class, args);
		System.out.println("Sistema iniciado com sucesso!");
		System.out.println("Acesse: http://localhost:8080");
	}
	}


