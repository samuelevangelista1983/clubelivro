package br.org.crvnluz.editora.clubelivro.repositorio.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;

@Repository
public interface TipoContatoRepositorio extends JpaRepository<TipoContato, Long> {

	public TipoContato findByNome(String nome);
	
}
