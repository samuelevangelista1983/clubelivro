package br.org.crvnluz.editora.clubelivro.repositorio.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;

public interface FormaEntregaRepositorio extends JpaRepository<FormaEntrega, Long> {

	public FormaEntrega findByNome(String nome);
}
