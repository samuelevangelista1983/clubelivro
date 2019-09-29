package br.org.crvnluz.editora.clubelivro.repositorio.financeiro;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class FinanceiroDAO {
	
	@Autowired
	private EntityManager em;
	@Autowired
	private QueryLoader queryLoader;
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getFluxoReceita(LocalDate inicio, LocalDate fim) throws Throwable {
		String sql = queryLoader.getConsulta("fluxoreceita.sql");
		Query query = em.createNativeQuery(sql);
		query.setParameter("inicio", inicio.toString());
		query.setParameter("fim", fim.toString());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getInadimplenciaPorCategoria(LocalDate dataLimite) throws Throwable {
		String sql = queryLoader.getConsulta("inadimplencia_categoria.sql");
		Query query = em.createNativeQuery(sql);
		query.setParameter("datalimite", dataLimite.toString());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getInadimplenciaMensal(LocalDate dataLimite) throws Throwable {
		String sql = queryLoader.getConsulta("inadimplencia_mensal.sql");
		Query query = em.createNativeQuery(sql);
		query.setParameter("datalimite", dataLimite.toString());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getInadimplentes(Long idCategoria) throws Throwable {
		String sql = queryLoader.getConsulta("inadimplentes.sql");
		Query query = em.createNativeQuery(sql);
		query.setParameter("idCategoria", idCategoria);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getReceitaPorCategoria(LocalDate dataLimite) throws Throwable {
		String sql = queryLoader.getConsulta("receita_categoria.sql");
		Query query = em.createNativeQuery(sql);
		query.setParameter("datalimite", dataLimite.toString());
		return query.getResultList();
	}
	
}
