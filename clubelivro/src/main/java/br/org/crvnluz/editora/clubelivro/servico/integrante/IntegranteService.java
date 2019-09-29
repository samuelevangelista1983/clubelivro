package br.org.crvnluz.editora.clubelivro.servico.integrante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.eti.sen.utilitarios.documento.CPF;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.IntegranteRepositorio;

@Service
public class IntegranteService {
	
	@Autowired
	private IntegranteRepositorio repositorio;
	
	private void validar(Integrante integrante) throws ValidacaoException {
		Integrante.validar(integrante);
		
		if (integrante.getId() != null) {
			validarAtualizacao(integrante);
			
		} else {
			validarInclusao(integrante);
		}
	}
	
	private void validarAtualizacao(Integrante integrante) throws ValidacaoException {
		if (repositorio.findByNomeAndIdNot(integrante.getNome(), integrante.getId()) != null) {
			throw new ValidacaoException(String.format("Já existe um integrante do Clube do Livro cadastrado com o nome %s", integrante.getNome()));
		}
		
		if (repositorio.findByCpfAndIdNot(integrante.getCpf(), integrante.getId()) != null) {
			throw new ValidacaoException(String.format("Já existe um integrante do Clube do Livro cadastrado com o CPF %s", integrante.getCpf()));
		}
	}
	
	private void validarInclusao(Integrante integrante) throws ValidacaoException {
		if (repositorio.findByNome(integrante.getNome()) != null) {
			throw new ValidacaoException(String.format("Já existe um integrante do Clube do Livro cadastrado com o nome %s", integrante.getNome()));
		}
		
		if (repositorio.findByCpf(integrante.getCpf()) != null) {
			throw new ValidacaoException(String.format("Já existe um integrante do Clube do Livro cadastrado com o CPF %s", integrante.getCpf()));
		}
	}
	
	public Integrante ativarDesativarIntegrante(Long id) {
		Integrante integrante = repositorio.getOne(id);
		
		if (integrante.getAtivo()) {
			integrante.setAtivo(false);
			integrante.setDesativacao(LocalDate.now());
			
		} else {
			integrante.setAtivo(true);
			integrante.setDesativacao(null);
		}
		
		return repositorio.saveAndFlush(integrante);
	}
	
	public Integrante getIntegrante(Long id) {
		Optional<Integrante> optional = repositorio.findById(id);
		Integrante integrante = optional.isPresent() ? optional.get() : null;
		
		if (integrante != null) {
			List<Contato> contatos = integrante.getContatos();
			List<Contato> telefones = contatos.stream().filter(contato -> contato.getTipo().getId().equals(1l)).collect(Collectors.toList());
			List<Contato> celulares = contatos.stream().filter(contato -> contato.getTipo().getId().equals(2l)).collect(Collectors.toList());
			List<Contato> emails = contatos.stream().filter(contato -> contato.getTipo().getId().equals(3l)).collect(Collectors.toList());
			integrante.setEmails(emails);
			integrante.setTelefonesCelulares(celulares);
			integrante.setTelefonesFixos(telefones);
			integrante.getEnderecos().stream().forEach(endereco -> {
				StringBuilder cep = new StringBuilder(endereco.getCep());
				cep = cep.insert(2, '.');
				cep = cep.insert(6, '-');
				endereco.setCep(cep.toString());
			});
			StringBuilder cpf = new StringBuilder(integrante.getCpf());
			cpf = cpf.insert(3, '.');
			cpf = cpf.insert(7, '.');
			cpf = cpf.insert(11, '-');
			integrante.setCpf(cpf.toString());
		}
		
		return integrante;
	}
	
	public List<Integrante> pesquisar(String nome, Long idCategoria) {
		/*
		 * Categoria:
		 * 1 - Estuddo
		 * 2 - Romance
		 * 3 - Estudo e romance
		 * 4 - Estudo e romance alternado
		 */
		List<Integrante> list = new ArrayList<>();
		
		if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria != null) {
			list = repositorio.pesquisar(nome, idCategoria);
			
		} else if (StringUtil.stringNaoNulaENaoVazia(nome) && idCategoria == null) {
			list = repositorio.pesquisar(nome);
			
		} else if (StringUtil.stringNulaOuVazia(nome) && idCategoria != null) {
			list = repositorio.pesquisar(idCategoria);
		}
		
		return list;
	}
	
	public List<Integrante> pesquisar(String nome, String cpf, Long idCategoria, Long idFrequencia, 
			Long idFormaPgto, Long idFormaEntrega, Integer situacao, Integer ordenacao) throws ValidacaoException {
		
		if (StringUtil.stringNaoNulaENaoVazia(cpf)) {
			if (!CPF.validar(cpf)) {
				throw new ValidacaoException("O CPF informado não é válido");
			}
		}
		
		/*
		 * Categoria:
		 * 1 - Estudo
		 * 2 - Romance
		 * 3 - Estudo e romance
		 * 4 - Estudo e romance alternado
		 *
		 * Freqüência:
		 * 1 - Mensal
		 * 2 - Bimestral
		 *
		 * Forma de pagamento:
		 * 1 - Boleto
		 * 2 - Cartão de débito
		 * 3 - Cartão de crédito
		 * 4 - Dinheiro
		 * 5 - Cheque à vista
		 *
		 * Forma de entrega:
		 * 1 - Correios
		 * 2 - Presencial
		 *
		 * Situação:
		 * 0 - Ativo
		 * 1 - Inativo
		 *
		 * Tipo de ordenacao:
		 * 0 - Ascendente
		 * 1 - Descendente
		 */
		
		if (ordenacao == null) {
			ordenacao = 0;
		}
		
		Integrante integrante = new Integrante();
		integrante.setNome(StringUtil.stringNaoNulaENaoVazia(nome) ? nome : null);
		integrante.setCpf(StringUtil.stringNaoNulaENaoVazia(cpf) ? cpf : null);
		integrante.setCategoria(new Categoria(idCategoria, null));
		integrante.setFrequencia(new Frequencia(idFrequencia, null));
		integrante.setFormaEntrega(new FormaEntrega(idFormaEntrega, null));
		integrante.setFormaPgtoPref(new FormaPgto(idFormaPgto, null));
		
		if (situacao != null) {
			integrante.setAtivo(situacao.equals(0) ? true : false);
		}
		
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase();
		Example<Integrante> exemplo = Example.of(integrante, matcher);
		Sort sort = ordenacao == 0 ? Sort.by(Sort.Direction.ASC, "nome") : Sort.by(Sort.Direction.DESC, "nome");
		return repositorio.findAll(exemplo, sort);
	}
	
	public Integrante salvar(Integrante integrante) throws ValidacaoException {
		integrante.getEmails().stream().forEach(email -> {
			email.setTipo(new TipoContato(3l, null, null));
			integrante.adicionarContato(email);
		});
		integrante.getTelefonesCelulares().stream().forEach(celular -> {
			celular.setTipo(new TipoContato(2l, null, null));
			integrante.adicionarContato(celular);
		});
		integrante.getTelefonesFixos().stream().forEach(telefone -> {
			telefone.setTipo(new TipoContato(1l, null, null));
			integrante.adicionarContato(telefone);
		});
		integrante.getEnderecos().stream().forEach(endereco -> {
			String cep = endereco.getCep();
			cep = cep.replace(".", "");
			cep = cep.replace("-", "");
			endereco.setCep(cep);
			endereco.setIntegrante(integrante);
		});
		validar(integrante);
		String cpf = integrante.getCpf();
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		integrante.setCpf(cpf);
		return repositorio.saveAndFlush(integrante);
	}
	
}
