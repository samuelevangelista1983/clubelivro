package br.org.crvnluz.editora.clubelivro.repositorio.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;

public interface FormaPgtoRepositorio extends JpaRepository<FormaPgto, Long> {

	public FormaPgto findByNome(String nome);
	
}
