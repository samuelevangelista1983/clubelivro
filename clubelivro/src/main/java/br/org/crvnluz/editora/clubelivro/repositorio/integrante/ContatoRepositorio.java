package br.org.crvnluz.editora.clubelivro.repositorio.integrante;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;

public interface ContatoRepositorio extends JpaRepository<Contato, Long> {

}
