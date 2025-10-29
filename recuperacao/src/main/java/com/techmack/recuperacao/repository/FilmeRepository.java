package com.techmack.recuperacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techmack.recuperacao.model.Filme;

@Repository
public interface  FilmeRepository extends JpaRepository< Filme, Long> {
    
}

