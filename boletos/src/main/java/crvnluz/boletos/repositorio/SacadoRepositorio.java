package crvnluz.boletos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import crvnluz.boletos.entidade.Sacado;

public interface SacadoRepositorio extends JpaRepository<Sacado, Long> {

	public Sacado findByDocumento(String documento);
	
}
