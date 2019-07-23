package crvnluz.boletos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import crvnluz.boletos.entidade.Boleto;

public interface BoletoRepositorio extends JpaRepository<Boleto, Long> {

	public Boleto findByNumeroBanco(String numeroBanco);
	
}
