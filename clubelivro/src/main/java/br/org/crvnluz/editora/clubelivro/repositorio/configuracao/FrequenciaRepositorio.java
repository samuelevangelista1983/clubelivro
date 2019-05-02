package br.org.crvnluz.editora.clubelivro.repositorio.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;

public interface FrequenciaRepositorio extends JpaRepository<Frequencia, Long> {

	public Frequencia findByNome(String nome);
	
}
