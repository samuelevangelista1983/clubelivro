package br.org.crvnluz.editora.clubelivro.repositorio.financeiro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Boleto;

@Repository
public interface BoletoRepositorio extends JpaRepository<Boleto, Long> {
	
	public Boleto findByNumeroBanco(String numeroBanco);
	
	@Query("select min(entidade.emissao) from Boleto as entidade")
	public LocalDate getMinimoEmissao();
	
	@Query("select max(entidade.vcto) from Boleto as entidade")
	public LocalDate getMaximoVencimento();
	
	@Query("select max(entidade.pgto) from Boleto as entidade")
	public LocalDate getMaximoPagamento();
	
	@Query("from Boleto as entidade where entidade.integrante.nome like %?1% order by entidade.integrante.nome")
	public List<Boleto> pesquisarPorNome(String nome);
	
	@Query("from Boleto as entidade where entidade.integrante.nome like %?1% and entidade.nossoNumero = ?2 order by entidade.integrante.nome")
	public List<Boleto> pesquisarPorNomeENumeroBoleto(String nome, String nossoNumero);
	
	@Query("from Boleto as entidade where entidade.nossoNumero = ?1 order by entidade.integrante.nome")
	public List<Boleto> pesquisarPorNumeroBoleto(String nossoNumero);
	
}
