package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

@RestController
@RequestMapping("/financeiro")
public class BoletoController extends CrudController<Boleto> {
	
	@Autowired
	private BoletoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Boleto get(Long id) throws Exception {
		return dao.selectById(id);
	}
	
	@Override
	protected List<Boleto> listar() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Boleto salvar(Boleto boleto) {
		if (boleto.getId() != null) {
			dao.update(boleto);
			
		} else {
			dao.save(boleto);
		}
		
		return boleto;
	}
	
	@Override
	protected void validarAtualizacao(Boleto boleto) throws ValidacaoException {
		if (boleto.getId() == null) {
			throw new ValidacaoException("O boleto informado não possui um id, neste caso deve ser utilizado o método de inclusão");
		}
		
		Boleto.validar(boleto);
	}
	
	@Override
	protected void validarInclusao(Boleto boleto) throws ValidacaoException {
		if (boleto.getId() != null) {
			throw new ValidacaoException("O boleto informado possui um id, neste caso deve ser utilizado o método de atualização");
		}
		
		Boleto.validar(boleto);
		
		if (dao.verificarExistenciaBoleto(boleto.getNumeroBeneficiario(), boleto.getNumeroBanco())) {
			throw new ValidacaoException("Já existe boleto cadastrado com o número do banco e/ou o número do boleto informados");
		}
	}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/boletos")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> adicionarBoleto(@RequestBody Boleto boleto) {
		return criarOuAtualizar(boleto);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/boletos/ativar/{id}")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> ativarBoleto(@PathVariable("id") Long id) {
		ResponseEntity response;
		
		try {
			dao.atualizarSituacao(id, 0);
			response = new ResponseEntity(true, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@PutMapping("/boletos")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> atualizarBoleto(@RequestBody Boleto boleto) {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/boletos/cancelar/{id}")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> cancelarBoleto(@PathVariable("id") Long id) {
		ResponseEntity response;
		
		try {
			dao.atualizarSituacao(id, 4);
			response = new ResponseEntity(true, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@GetMapping("/boletos/{id}")
	public ResponseEntity<Serializable> getBoleto(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/boletos")
	public ResponseEntity<?> getBoletos() {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/boletos/pesquisa")
	public ResponseEntity getBoletos(@RequestBody String request) {
		ResponseEntity response;
		
		try {
			Map<String, Object> map = new ObjectMapper().readValue(request, HashMap.class);
			String nome = map.get("nome") != null ? map.get("nome").toString() : null;
			String numBoleto = map.get("numBoleto") != null ? map.get("numBoleto").toString() : null;
			List<Boleto> list = new ArrayList<>();
			
			if (StringUtil.stringNaoNulaENaoVazia(nome) || StringUtil.stringNaoNulaENaoVazia(numBoleto)) {
				list = dao.pesquisar(nome, numBoleto);
			}
			
			response = new ResponseEntity(list, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/boletos/pesquisa/avancada")
	public ResponseEntity pesquisarBoletos(@RequestBody String request) {
		ResponseEntity response;
		
		try {
			Map<String, Object> map = new ObjectMapper().readValue(request, HashMap.class);
			String nome = map.get("nome") != null ? map.get("nome").toString() : null;
			/*
			 * Categoria:
			 * 1 - Estudo
			 * 2 - Romance
			 * 3 - Estudo e romance
			 * 4 - Estudo e romance alternado
			 */
			Long idCategoria = map.get("categoria") != null && StringUtil.stringNaoNulaENaoVazia(map.get("categoria").toString()) ? Long.valueOf(map.get("categoria").toString()) : null;
			/*
			 * Situação:
			 * 0 - Aberto
			 * 1 - Baixado
			 * 2 - Baixado manualmente
			 */
			Long idSituacao = map.get("situacao") != null && StringUtil.stringNaoNulaENaoVazia(map.get("situacao").toString()) ? Long.valueOf(map.get("situacao").toString()) : null;
			/*
			 * Tipo de ordenacao:
			 * 0 - Ascendente
			 * 1 - Descendente
			 */
			Long ordenacao = map.get("tipoOrdenacao") != null && StringUtil.stringNaoNulaENaoVazia(map.get("tipoOrdenacao").toString()) ? Long.valueOf(map.get("tipoOrdenacao").toString()) : 0;
			/*
			 * Campo de ordenação:
			 * 0 - Sacado
			 * 1 - Número do boleto
			 * 2 - Data de emissão
			 * 3 - Data de vencimento
			 */
			Long campoOrdenacao = map.get("campoOrdenacao") != null && StringUtil.stringNaoNulaENaoVazia(map.get("campoOrdenacao").toString()) ? Long.valueOf(map.get("campoOrdenacao").toString()) : 3;
			LocalDate dtEmissaoInicial = null;
			
			if (map.get("dtEmissaoInicial") != null && StringUtil.stringNaoNulaENaoVazia(map.get("dtEmissaoInicial").toString())) {
				try {
					dtEmissaoInicial = DataUtil.parserData(map.get("dtEmissaoInicial").toString());
					
				} catch (Exception ex) {
					throw new ValidacaoException("A data de emissão inicial não é válida");
				}
			}
			
			LocalDate dtEmissaoFinal = null;
			
			if (map.get("dtEmissaoFinal") != null && StringUtil.stringNaoNulaENaoVazia(map.get("dtEmissaoFinal").toString())) {
				try {
					dtEmissaoFinal = DataUtil.parserData(map.get("dtEmissaoFinal").toString());
					
				} catch (Exception ex) {
					throw new ValidacaoException("A data de emissão final não é válida");
				}
			}
			
			LocalDate dtVctoInicial = null;
			
			if (map.get("dtVctoInicial") != null && StringUtil.stringNaoNulaENaoVazia(map.get("dtVctoInicial").toString())) {
				try {
					dtVctoInicial = DataUtil.parserData(map.get("dtVctoInicial").toString());
					
				} catch (Exception ex) {
					throw new ValidacaoException("A data de vencimento inicial não é válida");
				}
			}
			
			LocalDate dtVctoFinal = null;
			
			if (map.get("dtVctoFinal") != null && StringUtil.stringNaoNulaENaoVazia(map.get("dtVctoFinal").toString())) {
				try {
					dtVctoFinal = DataUtil.parserData(map.get("dtVctoFinal").toString());
					
				} catch (Exception ex) {
					throw new ValidacaoException("A data de vencimento final não é válida");
				}
			}
			
			List<Boleto> list = new ArrayList<>();
			
			if (StringUtil.stringNaoNulaENaoVazia(nome) || idCategoria != null || idSituacao != null 
					|| dtEmissaoInicial != null || dtEmissaoFinal != null || dtVctoInicial != null || dtVctoFinal != null) {
				
				if (dtEmissaoInicial != null && dtEmissaoFinal != null && dtEmissaoInicial.isAfter(dtEmissaoFinal)) {
					throw new ValidacaoException("A data de emissão inicial não pode ser posterior à data de emissão final");
				}
				
				if (dtVctoInicial != null && dtVctoFinal != null && dtVctoInicial.isAfter(dtVctoFinal)) {
					throw new ValidacaoException("A data de vencimento inicial não pode ser posterior à data de vencimento final");
				}
				
				list = dao.pesquisar(nome, idCategoria, idSituacao, dtEmissaoInicial, dtEmissaoFinal, dtVctoInicial, dtVctoFinal, ordenacao, campoOrdenacao);
			}
			
			response = new ResponseEntity(list, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@DeleteMapping("/boletos/{id}")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> removerBoleto(@PathVariable("id") Long id) {
		throw new UnsupportedOperationException();
		/*
		ResponseEntity response;
		
		try {
			dao.delete(id);
			response = new ResponseEntity(true, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
		*/
	}
	
}
