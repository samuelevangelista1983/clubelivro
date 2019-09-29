package br.org.crvnluz.editora.clubelivro.repositorio.integrante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;

@Repository
public interface IntegranteRepositorio extends JpaRepository<Integrante, Long> {

	public Integrante findByCpf(String cpf);
	
	public Integrante findByCpfAndIdNot(String cpf, Long id);
	
	public Integrante findByNome(String nome);
	
	public Integrante findByNomeAndIdNot(String nome, Long id);
	
	@Query("from Integrante as entidade where entidade.nome like %?1% order by entidade.nome")
	public List<Integrante> pesquisar(String nome);
	
	@Query("from Integrante as entidade where entidade.categoria.id = ?1 order by entidade.nome")
	public List<Integrante> pesquisar(Long idCategoria);
	
	@Query("from Integrante as entidade where entidade.nome like %?1% and entidade.categoria.id = ?2 order by entidade.nome")
	public List<Integrante> pesquisar(String nome, Long idCategoria);
	
}
