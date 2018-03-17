package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.io.Serializable;
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

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/financeiro")
public class BoletoController extends BaseController<Boleto> {
	
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
		return dao.save(boleto);
	}
	
	@Override
	protected void validarAtualizacao(Boleto boleto) {}
	
	@Override
	protected void validarInclusao(Boleto boleto) {
		throw new UnsupportedOperationException();
	}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/boletos")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> adicionarBoleto(@RequestBody Boleto boleto) {
		return criarOuAtualizar(boleto);
	}
	
	@PutMapping("/boletos")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> atualizarBoleto(@RequestBody Boleto boleto) {
		throw new UnsupportedOperationException();
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
			/*
			 * Categoria:
			 * 1 - Estuddo
			 * 2 - Romance
			 * 3 - Estudo e romance
			 * 4 - Estudo e romance alternado
			 */
			Map<String, Object> map = new ObjectMapper().readValue(request, HashMap.class);
			String nome = map.get("nome") != null ? map.get("nome").toString() : null;
			Long idCategoria = StringUtil.stringNaoNulaENaoVazia(map.get("categoria").toString()) ? Long.valueOf(map.get("categoria").toString()) : null;
			Long idSituacao = StringUtil.stringNaoNulaENaoVazia(map.get("situacao").toString()) ? Long.valueOf(map.get("situacao").toString()) : null;
			List<Boleto> list = new ArrayList<>();
			
			if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria != null && idSituacao != null) {
				list = dao.pesquisar(nome, idCategoria, idSituacao);
				
			} else if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria != null && idSituacao == null) {
				list = dao.pesquisarNomeClassificacao(nome, idCategoria);
				
			} else if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria == null && idSituacao != null) {
				list = dao.pesquisarNomeSituacao(nome, idSituacao);
				
			} else if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria == null && idSituacao == null) {
				list = dao.pesquisarNome(nome);
				
			} else if (StringUtil.stringNulaOuVazia(nome) && idCategoria != null && idSituacao != null) {
				list = dao.pesquisar(idCategoria, idSituacao);
				
			} else if (StringUtil.stringNulaOuVazia(nome) && idCategoria != null && idSituacao == null) {
				list = dao.pesquisarClassificacao(idCategoria);
				
			} else if (StringUtil.stringNulaOuVazia(nome) && idCategoria == null && idSituacao != null) {
				list = dao.pesquisarSituacao(idSituacao);
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
	}
	
}
