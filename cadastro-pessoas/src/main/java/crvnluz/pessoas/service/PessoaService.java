package crvnluz.pessoas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import crvnluz.pessoas.entidade.Atualizacao;
import crvnluz.pessoas.entidade.Endereco;
import crvnluz.pessoas.entidade.Informacao;
import crvnluz.pessoas.entidade.Pessoa;
import crvnluz.pessoas.repositorio.PessoaRepositorio;

@Component
public class PessoaService {
	
	private final Logger logger = LoggerFactory.getLogger(PessoaService.class);

	@Autowired
	private PessoaRepositorio repositorio;
	
	private Pessoa mesclar(Pessoa atual, Pessoa nova) {
		Pessoa mescla = new Pessoa();
		mescla.setId(atual.getId());
		mescla.setNome(nova.getNome());
		
		if (nova.getNascimento() != null) {
			mescla.setNascimento(nova.getNascimento());
			
		} else {
			mescla.setNascimento(atual.getNascimento());
		}
		
		mescla.setAlteracoes(mesclarAlteracoes(atual.getAlteracoes(), nova.getAlteracoes()));
		mescla.setContatos(mesclarInformacoes(atual.getMapContatos(), nova.getMapContatos()));
		mescla.setDocumentos(mesclarInformacoes(atual.getMapDocumentos(), nova.getMapDocumentos()));
		mescla.setEnderecos(mesclarEnderecos(atual.getMapEnderecos(), nova.getMapEnderecos()));
		return mescla;
	}
	
	private List<Atualizacao> mesclarAlteracoes(List<Atualizacao> alteracoesAtuais, List<Atualizacao> alteracoesNovas) {
		List<Atualizacao> alteracoes = new ArrayList<>(alteracoesAtuais);
		alteracoes.addAll(alteracoesNovas);
		return alteracoes;
	}
	
	private List<Informacao> mesclarInformacoes(Map<String, Informacao> informacoesAtuais, Map<String, Informacao> informacoesNovas) {
		List<Informacao> mescla = new ArrayList<>();
		
		if (!informacoesAtuais.isEmpty() && informacoesNovas.isEmpty()) {
			mescla = new ArrayList<>(informacoesAtuais.values());
			
		} else if (informacoesAtuais.isEmpty() && !informacoesNovas.isEmpty()) {
			mescla = new ArrayList<>(informacoesNovas.values());
			
		} else if (!informacoesAtuais.isEmpty() && !informacoesNovas.isEmpty()) {
			Set<String> chaves = informacoesNovas.keySet();
			
			for (String chave: chaves) {
				Informacao informacao = informacoesNovas.get(chave);
				String operacao = informacao.getOperacao();
				
				if (!operacao.equalsIgnoreCase("remover")) {
					mescla.add(informacao);
				}
				
				informacoesAtuais.remove(chave);
			}
			
			mescla.addAll(informacoesAtuais.values());
		}
		
		return mescla;
	}
	
	private List<Endereco> mesclarEnderecos(Map<String, Endereco> enderecosAtuais, Map<String, Endereco> enderecosNovos) {
		List<Endereco> mescla = new ArrayList<>();
		
		if (!enderecosAtuais.isEmpty() && enderecosNovos.isEmpty()) {
			mescla = new ArrayList<>(enderecosAtuais.values());
			
		} else if (enderecosAtuais.isEmpty() && !enderecosNovos.isEmpty()) {
			mescla = new ArrayList<>(enderecosNovos.values());
			
		} else if (!enderecosAtuais.isEmpty() && !enderecosNovos.isEmpty()) {
			Set<String> chaves = enderecosNovos.keySet();
			
			for (String chave: chaves) {
				Endereco endereco = enderecosNovos.get(chave);
				String operacao = endereco.getOperacao();
				
				if (!operacao.equalsIgnoreCase("remover")) {
					mescla.add(endereco);
				}
				
				enderecosAtuais.remove(chave);
			}
			
			mescla.addAll(enderecosAtuais.values());
		}
		
		return mescla;
	}
	
	public Pessoa adicionar(Pessoa pessoa) {
		if (StringUtils.hasText(pessoa.getId())) {
			throw new IllegalArgumentException("O ID da pessoa não está nulo, nesse caso deve ser utilizado método de atualização");
		}
		
		Pessoa.validar(pessoa);
		Pessoa pessoaEncontrada = null;
		List<Informacao> documentos = pessoa.getDocumentos();
		
		for (Informacao documento: documentos) {
			pessoaEncontrada = repositorio.findByDocumento(documento.getNome(), documento.getValor());
			
			if (pessoaEncontrada != null) {
				break;
			}
		}
		
		Pessoa pessoaSalva = null;
		
		if (pessoaEncontrada == null) {
			pessoaSalva = repositorio.save(pessoa);
			
		} else {
			Pessoa pessoaMesclada = mesclar(pessoaEncontrada, pessoa);
			pessoaSalva = repositorio.save(pessoaMesclada);
		}
		
		return pessoaSalva;
	}
	
	public Pessoa atualizar(Pessoa pessoa) {
		if (!StringUtils.hasText(pessoa.getId())) {
			throw new IllegalArgumentException("O ID da pessoa está nulo, nesse caso deve ser utilizado método de adição");
		}
		
		Pessoa pessoaAtualizada = null;
		
		try {
			pessoaAtualizada = repositorio.findById(pessoa.getId()).get();
			pessoaAtualizada = mesclar(pessoaAtualizada, pessoa);
			pessoaAtualizada = repositorio.save(pessoaAtualizada);
			
		} catch (NoSuchElementException ex) {
			logger.warn(String.format("Tentou atualizar uma pessoa que não está cadastrada. Dados da pessoa %s", pessoa.toString()));
		}
		
		return pessoaAtualizada;
	}
	
	public Pessoa pesquisar(String id) {
		if (!StringUtils.hasText(id)) {
			throw new IllegalArgumentException("O ID informado está nulo ou vazio");
		}
		
		Pessoa pessoa = null;
		
		try {
			pessoa = repositorio.findById(id).get();
			
		} catch (NoSuchElementException ex) {
			logger.warn(String.format("Não foi encontrada uma pessoa com o ID %s", id));
		};
		
		return pessoa;
	}
	
	public Pessoa pesquisarPorDocumento(String nomeDocumento, String valorDocumento) {
		if (!StringUtils.hasText(nomeDocumento)) {
			throw new IllegalArgumentException("O nome do documento informado está nulo ou vazio");
		}
		
		if (!StringUtils.hasText(valorDocumento)) {
			throw new IllegalArgumentException("O valor do documento informado está nulo ou vazio");
		}
		
		return repositorio.findByDocumento(nomeDocumento, valorDocumento);
	}
	
	public List<Pessoa> pesquisarPorNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("O nome informado está nulo ou vazio");
		}
		
		return repositorio.findByNomeLikeOrderByNome(nome);
	}
	
	public void remover(Pessoa pessoa) {
		if (pessoa != null && pessoa.getId() != null) {
			repositorio.deleteById(pessoa.getId());
		}
	}
	
}
