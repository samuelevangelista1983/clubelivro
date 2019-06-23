package crvnluz.cobcaixa.repositorio.access;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import crvnluz.cobcaixa.entidade.access.BoletoCobCaixa;
import crvnluz.cobcaixa.entidade.access.BoletoCobCaixaPK;

public interface BoletoCobCaixaRepositorio extends JpaRepository<BoletoCobCaixa, BoletoCobCaixaPK> {
	
	@Query("from BoletoCobCaixa where dataDocumento >= ?1 and codigoRetorno = '09' order by nossoNumero")
	public List<BoletoCobCaixa> getBoletosCancelados(LocalDate data, Pageable pageable);
	
	@Query("from BoletoCobCaixa where dataDocumento >= ?1 and (codigoRetorno = '01' or codigoRetorno = '02') order by nossoNumero")
	public List<BoletoCobCaixa> getBoletosEmitidos(LocalDate data, Pageable pageable);
	
	@Query("from BoletoCobCaixa where dataDocumento >= ?1 and codigoRetorno = '06' order by nossoNumero")
	public List<BoletoCobCaixa> getBoletosLiquidados(LocalDate data, Pageable pageable);
	
}
