package crvnluz.pessoas.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import crvnluz.pessoas.entidade.Pessoa;

public interface PessoaRepositorio extends MongoRepository<Pessoa, String> {
	
	@Query("{'documentos.nome': ?0, 'documentos.valor': ?1}")
	public Pessoa findByDocumento(String nome, String valor);
	
	public List<Pessoa> findByNomeLikeOrderByNome(String nome);
	
}
