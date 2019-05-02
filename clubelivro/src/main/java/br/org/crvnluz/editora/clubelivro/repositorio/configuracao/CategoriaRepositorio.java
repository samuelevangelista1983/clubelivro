package br.org.crvnluz.editora.clubelivro.repositorio.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
	
	public Categoria findByNome(String nome);
	
}
