package br.org.crvnluz.editora.clubelivro.servico.financeiro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.crvnluz.editora.clubelivro.controlador.financeiro.inadimplencia.Inadimplente;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.BoletoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.FinanceiroDAO;

@Service
public class FinanceiroService {
	
	@Autowired
	private BoletoRepositorio boletoRepositorio;
	@Autowired
	private FinanceiroDAO dao;
	
	public List<Object[]> getFluxoReceita(LocalDate inicio, LocalDate fim) throws Throwable {
		if (inicio.isAfter(fim)) {
			throw new ValidacaoException("A data inicial deve ser igual ou anterior à data final");
		}
		
		return dao.getFluxoReceita(inicio, fim);
	}
	
	public int getMaiorAno() {
		LocalDate maiorPgto = boletoRepositorio.getMaximoPagamento();
		
		if (maiorPgto == null) {
			maiorPgto = LocalDate.now();
		}
		
		LocalDate maiorVcto = boletoRepositorio.getMaximoVencimento();
		
		if (maiorVcto == null) {
			maiorVcto = LocalDate.now();
		}
		
		LocalDate maior = null;
		
		if (maiorVcto.isAfter(maiorPgto)) {
			maior = maiorVcto;
			
		} else {
			maior = maiorPgto;
		}
		
		return maior.getYear();
	}
	
	public int getMenorAno() {
		LocalDate menorEmissao = boletoRepositorio.getMinimoEmissao();
		
		if (menorEmissao == null) {
			menorEmissao = LocalDate.now();
		}
		
		return menorEmissao.getYear();
	}
	
	public List<Inadimplente> pesquisarInadimplentes(Long idCategoria) throws Throwable {
		List<Object[]> dados = dao.getInadimplentes(idCategoria);
		List<Inadimplente> inadimplentes = new ArrayList<>(dados.size());
		dados.stream().forEach(dado -> {
			inadimplentes.add(new Inadimplente(dado[0].toString(), dado[1].toString(), Integer.parseInt(dado[2].toString())));
		});
		return inadimplentes;
	}
	
}
