package br.org.crvnluz.editora.clubelivro.servico.financeiro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Boleto;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.BoletoRepositorio;

@Service
public class BoletoService {
	
	@Autowired
	private BoletoRepositorio repositorio;
	
	private LocalDate getLocalDate(String dtEmissaoInicial, String msgErro) throws ValidacaoException {
		LocalDate emissaoInicial = null;
		
		if (StringUtil.stringNaoNulaENaoVazia(dtEmissaoInicial)) {
			try {
				emissaoInicial = LocalDate.parse(dtEmissaoInicial, DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
				
			} catch (Throwable t) {
				throw new ValidacaoException(msgErro);
			}
		}
		
		return emissaoInicial;
	}
	
	public Boleto getBoleto(Long id) {
		Optional<Boleto> optional = repositorio.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public List<Boleto> pesquisar(String nome, String numeroBoleto) {
		List<Boleto> list = new ArrayList<>();
		
		if (StringUtil.stringNaoNulaENaoVazia(nome) && StringUtil.stringNaoNulaENaoVazia(numeroBoleto)) {
			list = repositorio.pesquisarPorNomeENumeroBoleto(nome, numeroBoleto);
			
		} else if (StringUtil.stringNaoNulaENaoVazia(nome) && StringUtil.stringNulaOuVazia(numeroBoleto)) {
			list = repositorio.pesquisarPorNome(nome);
			
		} else if (StringUtil.stringNulaOuVazia(nome) && StringUtil.stringNaoNulaENaoVazia(numeroBoleto)) {
			list = repositorio.pesquisarPorNumeroBoleto(numeroBoleto);
		}
		
		return list;
	}
	
	public List<Boleto> pesquisar(String sacado, Long idCategoria, Integer situacao, String dtEmissaoInicial, String dtEmissaoFinal,
			String dtVctoInicial, String dtVctoFinal, Integer ordenacao, Integer campoOrdenacao) throws ValidacaoException {
		LocalDate emissaoInicial = getLocalDate(dtEmissaoInicial, "A data de emissão inicial informada não é válida");
		LocalDate emissaoFinal = getLocalDate(dtEmissaoFinal, "A data de emissão final informada não é válida");
		
		if (emissaoInicial != null && emissaoFinal != null) {
			if (emissaoInicial.isAfter(emissaoFinal)) {
				throw new ValidacaoException("A data de emissao inicial deve ser igual ou anterior à data de emissão final");
			}
		}
		
		LocalDate vctoInicial = getLocalDate(dtVctoInicial, "A data de vencimento inicial informada não é válida");
		LocalDate vctoFinal = getLocalDate(dtVctoFinal, "A data de vencimento final informada não é válida");
		
		if (vctoInicial != null && vctoFinal != null) {
			if (vctoInicial.isAfter(vctoFinal)) {
				throw new ValidacaoException("A data de vencimento inicial deve ser igual ou anterior à data de vencimento final");
			}
		}
		
		/*
		 * Categoria:
		 * 1 - Estudo
		 * 2 - Romance
		 * 3 - Estudo e romance
		 * 4 - Estudo e romance alternado
		 *
		 * Situação:
		 * 0 - Aberto
		 * 1 - Baixado
		 * 2 - Baixado manualmente
		 * 3 - Cancelado
		 * 4 - Cancelado manualmente
		 * 5 - Erro processamento
		 *
		 * Tipo de ordenacao:
		 * 0 - Ascendente
		 * 1 - Descendente
		 * 
		 * Campo de ordenação
		 * 0 - Sacado
		 * 1 - Número do boleto
		 * 2 - Data de emissão
		 * 3 - Data de vencimento
		 */
		if (ordenacao == null) {
			ordenacao = 0;
		}
		
		String campo = null;
		
		if (campoOrdenacao == null || campoOrdenacao.equals(0)) {
			campo = "integrante.nome";
			
		} else if(campoOrdenacao.equals(1)) {
			campo = "nossoNumero";
			
		} else if (campoOrdenacao.equals(2)) {
			campo = "emissao";
			
		} else if (campoOrdenacao.equals(3)) {
			campo = "vcto";
		}
		
		Boleto boleto = new Boleto();
		boleto.setSituacao(situacao);
		
		if (StringUtil.stringNaoNulaENaoVazia(sacado)) {
			Integrante integrante = new Integrante();
			integrante.setNome(sacado);
			boleto.setIntegrante(integrante);
		}
		
		if (idCategoria != null) {
			Integrante integrante = boleto.getIntegrante();
			
			if (integrante == null) {
				integrante = new Integrante();
				boleto.setIntegrante(integrante);
			}
			
			integrante.setCategoria(new Categoria(idCategoria, null));
		}
		
		Sort sort = ordenacao.equals(0) ? Sort.by(Sort.Direction.ASC, campo) : Sort.by(Sort.Direction.DESC, campo);
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase();
		Example<Boleto> exemplo = Example.of(boleto, matcher);
		List<Boleto> boletos = repositorio.findAll(exemplo, sort);
		
		if (!boletos.isEmpty()) {
			if (emissaoInicial != null && emissaoFinal != null) {
				boletos = boletos.stream()
						.filter(b -> b.getEmissao().compareTo(emissaoInicial) > -1 && b.getEmissao().compareTo(emissaoFinal) < 1)
						.collect(Collectors.toList());
				
			} else if (emissaoInicial != null && emissaoFinal == null) {
				boletos = boletos.stream()
						.filter(b -> b.getEmissao().compareTo(emissaoInicial) > -1)
						.collect(Collectors.toList());
				
			} else if (emissaoInicial == null && emissaoFinal != null) {
				boletos = boletos.stream()
						.filter(b -> b.getEmissao().compareTo(emissaoFinal) < 1)
						.collect(Collectors.toList());
			}
			
			if (vctoInicial != null && vctoFinal != null) {
				boletos = boletos.stream()
						.filter(b -> b.getVcto().compareTo(vctoInicial) > -1 && b.getVcto().compareTo(vctoFinal) < 1)
						.collect(Collectors.toList());
				
			} else if (vctoInicial != null && vctoFinal == null) {
				boletos = boletos.stream()
						.filter(b -> b.getVcto().compareTo(vctoInicial) > -1)
						.collect(Collectors.toList());
				
			} else if (vctoInicial == null && vctoFinal != null) {
				boletos = boletos.stream()
						.filter(b -> b.getVcto().compareTo(vctoFinal) < 1)
						.collect(Collectors.toList());
			}
		}
		
		return boletos;
	}
	
}
