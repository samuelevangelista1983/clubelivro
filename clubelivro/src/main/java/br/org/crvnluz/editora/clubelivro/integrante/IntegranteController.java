package br.org.crvnluz.editora.clubelivro.integrante;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;
import br.org.crvnluz.editora.clubelivro.integrante.contato.Contato;
import br.org.crvnluz.editora.clubelivro.integrante.contato.ContatoDAO;
import br.org.crvnluz.editora.clubelivro.integrante.documento.Documento;
import br.org.crvnluz.editora.clubelivro.integrante.documento.DocumentoDAO;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.Endereco;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.EnderecoDAO;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.Pessoa;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.PessoaDAO;

@RestController
public class IntegranteController extends CrudController<Integrante> {
	
	@Autowired
	private IntegranteDAO dao;
	@Autowired
	private PessoaDAO pessoaDao;
	@Autowired
	private DocumentoDAO documentoDao;
	@Autowired
	private ContatoDAO contatoDao;
	@Autowired
	private EnderecoDAO enderecoDao;
	
	// MÉTODOS PRIVADOS
	
	private Integrante ativarDesativarIntegrante(Long id) throws Exception {
		Integrante integrante = get(id);
		
		if (integrante.getAtivo()) {
			integrante.setAtivo(false);
			integrante.setDtDesativacao(LocalDate.now());
			
		} else {
			integrante.setAtivo(true);
			integrante.setDtDesativacao(null);
		}
		
		dao.update(integrante);
		return integrante;
	}
	
	private void salvarContatos(Pessoa pessoa) {
		List<Contato> contatosCadastrados = contatoDao.selectByPessoa(pessoa.getId());
		List<Contato> contatos = pessoa.getContatos();
		List<Contato> contatosSalvar = new ArrayList<>();
		List<Contato> contatosRemover = new ArrayList<>();
		
		if (contatosCadastrados.isEmpty()) {
			contatosSalvar.addAll(contatos);
			
		} else {
			for (Contato contato: contatos) {
				if (contato.getId() != null) {
					for (Contato cadastrado: contatosCadastrados) {
						if (contato.getId().equals(cadastrado.getId())) {
							contatosSalvar.add(contato);
							break;
						}
					}
					
				} else {
					contatosSalvar.add(contato);
				}
			}
			
			for (Contato cadastrado: contatosCadastrados) {
				boolean remover = true;
				
				for (Contato contato: contatosSalvar) {
					if (contato.getId() != null && contato.getId().equals(cadastrado.getId())) {
						remover = false;
						break;
					}
				}
				
				if (remover) {
					contatosRemover.add(cadastrado);
				}
			}
		}
		
		for (Contato contato: contatosSalvar) {
			contato.setIdPessoa(pessoa.getId());
			
			if (contato.getId() == null) {
				contatoDao.save(contato);
				
			} else {
				contatoDao.update(contato);
			}
		}
		
		for (Contato contato: contatosRemover) {
			contatoDao.delete(contato.getId());
		}
	}
	
	private void salvarEnderecos(Integrante integrante) {
		Pessoa pessoa = integrante.getPessoa();
		List<Endereco> enderecosCadastrados = enderecoDao.selectByPessoa(pessoa.getId());
		List<Endereco> enderecos = pessoa.getEnderecos();
		List<Endereco> enderecosSalvar = new ArrayList<>();
		List<Endereco> enderecosRemover = new ArrayList<>();
		
		if (enderecosCadastrados.isEmpty()) {
			enderecosSalvar.addAll(enderecos);
			
		} else {
			for (Endereco endereco: enderecos) {
				if (endereco.getId() != null) {
					for (Endereco cadastrado: enderecosCadastrados) {
						if (endereco.getId().equals(cadastrado.getId())) {
							enderecosSalvar.add(endereco);
							break;
						}
					}
					
				} else {
					enderecosSalvar.add(endereco);
				}
			}
			
			for (Endereco cadastrado: enderecosCadastrados) {
				boolean remover = true;
				
				for (Endereco endereco: enderecosSalvar) {
					if (endereco.getId() != null && endereco.getId().equals(cadastrado.getId())) {
						remover = false;
						break;
					}
				}
				
				if (remover) {
					enderecosRemover.add(cadastrado);
				}
			}
		}
		
		for (Endereco endereco: enderecosSalvar) {
			Endereco cobranca = integrante.getEnderecoCobranca();
			cobranca.setCep(cobranca.getCep().replace(".", "").replace("-", ""));
			Endereco entrega = integrante.getEnderecoEntrega();
			entrega.setCep(entrega.getCep().replace(".", "").replace("-", ""));
			endereco.setIdPessoa(pessoa.getId());
			
			if (endereco.getId() == null) {
				enderecoDao.save(endereco);
				
			} else {
				enderecoDao.update(endereco);
			}
			
			if (endereco.equals(cobranca)) {
				integrante.setEnderecoCobranca(endereco);
			}
			
			if (endereco.equals(entrega)) {
				integrante.setEnderecoEntrega(endereco);
			}
		}
		
		if (integrante.getId() != null) {
			dao.update(integrante);
		}
		
		for (Endereco endereco: enderecosRemover) {
			enderecoDao.delete(endereco.getId());
		}
	}
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Integrante get(Long id) throws Exception {
		Integrante integrante = dao.selectById(id);
		Pessoa pessoa = integrante.getPessoa();
		pessoa.setDocumentos(documentoDao.selectByPessoa(pessoa.getId()));
		pessoa.setContatos(contatoDao.selectByPessoa(pessoa.getId()));
		pessoa.setEnderecos(enderecoDao.selectByPessoa(pessoa.getId()));
		Endereco cobranca = integrante.getEnderecoCobranca();
		
		if (cobranca != null && cobranca.getId() != null) {
			integrante.setEnderecoCobranca(enderecoDao.selectById(cobranca.getId()));
		}
		
		Endereco entrega = integrante.getEnderecoEntrega();
		
		if (entrega != null && entrega.getId() != null) {
			integrante.setEnderecoEntrega(enderecoDao.selectById(entrega.getId()));
		}
		
		return integrante;
	}
	
	@Override
	protected List<Integrante> listar() throws Exception {
		/*
		List<Integrante> integrantes = dao.selectAll();
		
		for (Integrante integrante: integrantes) {
			Pessoa pessoa = integrante.getPessoa();
			pessoa.setDocumentos(documentoDao.selectByPessoa(pessoa.getId()));
			pessoa.setContatos(contatoDao.selectByPessoa(pessoa.getId()));
			pessoa.setEnderecos(enderecoDao.selectByPessoa(pessoa.getId()));
		}
		
		return integrantes;
		*/
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Integrante salvar(Integrante integrante) {
		Pessoa pessoa = integrante.getPessoa();
		
		if (pessoa.getId() == null) {
			pessoa = pessoaDao.save(pessoa);
			
		} else {
			pessoaDao.update(pessoa);
		}
		
		List<Documento> documentos = pessoa.getDocumentos();
		
		if (documentos != null && !documentos.isEmpty()) {
			Documento cpf = documentos.get(0);
			cpf.setIdPessoa(pessoa.getId());
			
			if (cpf.getId() == null) {
				cpf = documentoDao.save(cpf);
				
			} else {
				documentoDao.update(cpf);
			}
			
			integrante.setDocumento(cpf);
		}
		
		salvarContatos(pessoa);
		salvarEnderecos(integrante);
		
		if (integrante.getId() == null) {
			integrante = dao.save(integrante);
			
		} else {
			dao.update(integrante);
		}
		
		return integrante;
	}
	
	@Override
	protected void validarAtualizacao(Integrante integrante) throws ValidacaoException {
		if (integrante.getId() == null) {
			throw new ValidacaoException("O integrante informado não possui um id, neste caso deve ser utilizado o método de inclusão");
		}
		
		Integrante.validar(integrante);
		Pessoa pessoa = integrante.getPessoa();
		
		if (pessoaDao.findByNomeAndIdNot(pessoa.getNome(), pessoa.getId()) != null) {
			StringBuilder msg = new StringBuilder("Já existe um integrante do Clube do Livro cadastrado com o nome ");
			msg.append(pessoa.getNome());
			throw new ValidacaoException(msg.toString());
		}
		
		List<Documento> documentos = pessoa.getDocumentos();
		
		if (!documentos.isEmpty()) {
			Documento cpf = documentos.get(0);
			Integer pessoasCpfExistente = pessoaDao.contarPessoasMesmoCpf(cpf.getValor().replace(".", "").replace("-", ""), pessoa.getId());
			
			if (pessoasCpfExistente > 0) {
				StringBuilder msg = new StringBuilder("Já existe um integrante do Clube do Livro cadastrado com o CPF ");
				msg.append(cpf.getValor());
				throw new ValidacaoException(msg.toString());
			}
			
		}
	}
	
	@Override
	protected void validarInclusao(Integrante integrante) throws ValidacaoException {
		if (integrante.getId() != null) {
			throw new ValidacaoException("O integrante informado possui um id, neste caso deve ser utilizado o método de atualização");
		}
		
		Integrante.validar(integrante);
		Pessoa pessoa = integrante.getPessoa();
		
		if (!pessoaDao.findByNome(pessoa.getNome()).isEmpty()) {
			StringBuilder msg = new StringBuilder("Já existe um integrante do Clube do Livro cadastrado com o nome ");
			msg.append(pessoa.getNome());
			throw new ValidacaoException(msg.toString());
		}
		

		List<Documento> documentos = pessoa.getDocumentos();
		
		if (!documentos.isEmpty()) {
			Documento cpf = documentos.get(0);
			
			if (cpf.getValor() != null && documentoDao.findByValor(cpf.getValor()) != null) {
				StringBuilder msg = new StringBuilder("Já existe um integrante do Clube do Livro cadastrado com o CPF ");
				msg.append(cpf.getValor());
				throw new ValidacaoException(msg.toString());
			}
		}
	}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/integrantes")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> adicionarIntegrante(@RequestBody Integrante integrante) {
		return criarOuAtualizar(integrante);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping("/integrantes/ativar")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> ativarIntegrante(@RequestBody Long id) {
		ResponseEntity response;
		
		try {
			ativarDesativarIntegrante(id);
			response = new ResponseEntity("Ok", HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@PutMapping("/integrantes")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> atualizarIntegrante(@RequestBody String request) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping("/integrantes/desativar")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> desativarIntegrante(@RequestBody Long id) {
		ResponseEntity response;
		
		try {
			ativarDesativarIntegrante(id);
			response = new ResponseEntity("Ok", HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@GetMapping("/integrantes/{id}")
	public ResponseEntity<Serializable> getIntegrante(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/integrantes")
	public ResponseEntity<?> getIntegrantes() {
		//return listarTodos();
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/integrantes/pesquisa")
	public ResponseEntity getIntegrantes(@RequestBody String request) {
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
			List<Integrante> list = new ArrayList<>();
			
			if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria != null) {
				list = dao.pesquisar(nome, idCategoria);
				
			} else if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria == null) {
				list = dao.pesquisar(nome);
				
			} else if (StringUtil.stringNulaOuVazia(nome) && idCategoria != null) {
				list = dao.pesquisar(idCategoria);
			}
			
			response = new ResponseEntity(list, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	@DeleteMapping("/integrantes/{id}")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResponseEntity<Serializable> removerIntegrante(@PathVariable("id") Long id) {
		throw new UnsupportedOperationException();
	}
	
}
