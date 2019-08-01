package br.org.crvnluz.editora.clubelivro.repositorio.financeiro;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Boleto;

public interface BoletoRepositorio extends JpaRepository<Boleto, Long> {

}
