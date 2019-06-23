package crvnluz.cobcaixa.repositorio.hsql;

import org.springframework.data.jpa.repository.JpaRepository;

import crvnluz.cobcaixa.entidade.hsql.Sacado;

public interface SacadoRepositorio extends JpaRepository<Sacado, Long> {
	
	public Sacado findByNomeAndDocumento(String nome, String documento);
	
}
