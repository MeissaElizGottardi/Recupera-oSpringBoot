package com.techmack.recuperacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.techmack.recuperacao.model.Filme;
import com.techmack.recuperacao.repository.FilmeRepository;


@Controller
public class FilmeController {
 
    @Autowired
        private FilmeRepository filmeRepository;

        /**
     * Adicionar um novo filme ao banco.
     */
    @PostMapping("/adicionar")
    public String adicionarFilme(@ModelAttribute  Filme filme) {
        filmeRepository.save(filme);
        return "redirect:/";
    }


    /**
     * Excluir um filme pelo ID.
     */
    @GetMapping("/excluir/{id}")
    public String excluirFilme(@PathVariable Long id) {
        filmeRepository.deleteById(id);
        return "redirect:/";
    }

    /**
     * Marca um filme como assistido.
     */
    @GetMapping("/assistido/{id}")
    public String assistirFilme(@PathVariable Long id) {
        filmeRepository.findById(id).ifPresent(filme -> {
            filme.setAssistido(true);
            filmeRepository.save(filme);
        });
        return "redirect:/";
    }

 /**
     * Lista todos os filmes com paginação.
     */
    @GetMapping("/")
    public String listarFilmes(Model model, @RequestParam(defaultValue = "0") int page) {
        int tamanhoPagina = 5; // número de filmes por página
        Pageable configuracaoPagina = PageRequest.of(page, tamanhoPagina);
        Page<Filme> paginaFilmes = filmeRepository.findAll(configuracaoPagina);

        model.addAttribute("paginaFilmes", paginaFilmes);
        model.addAttribute("Paginaatual",page); 
        model.addAttribute("novoFilme", new Filme());

        return "index"; // página principal (templates/index.html)
}


/**
     * Exibe o formulário de edição de um filme.
     */
    @GetMapping("/editar/{id}")
    public String editarFilme(@PathVariable Long id, Model model) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme inválida: " + id));

        // adiciona o filme ao modelo para preencher o formulário
        model.addAttribute("filme", filme);
        return "editar"; // página templates/filme.html
    }

    /**
     * Atualiza um filme existente.
     */
    @PostMapping("/atualizar/{id}")
    public String atualizarFilme(@PathVariable Long id, @ModelAttribute Filme filmeAtualizado) {
        // busca o filme original no banco
        Filme filmeExistente = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme inválida: " + id));

        // atualiza apenas os campos desejados
        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setDescricao(filmeAtualizado.getDescricao());
        filmeExistente.setAssistido(filmeAtualizado.isAssistido());

        // salva novamente no banco
        filmeRepository.save(filmeExistente);

        // redireciona para a página principal
        return "redirect:/";
}
}