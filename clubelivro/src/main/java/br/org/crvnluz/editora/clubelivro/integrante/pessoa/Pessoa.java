package br.org.crvnluz.editora.clubelivro.integrante.pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;
import br.org.crvnluz.editora.clubelivro.integrante.contato.Contato;
import br.org.crvnluz.editora.clubelivro.integrante.documento.Documento;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.Endereco;

public class Pessoa extends Persistente {
	
	private static final long serialVersionUID = 7319668545026583205L;
	
	private String nome;
	private LocalDate nascimento;
	private List<Contato> contatos;
	private List<Documento> documentos;
	private List<Endereco> enderecos;
	
	// CONSTRUTORES PÚBLICOS
	
	public Pessoa() {}
	
	public Pessoa(Long id) {
		super(id);
	}
	
	public Pessoa(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", nascimento=" + nascimento + ", contatos=" + contatos + ", documentos="
				+ documentos + ", enderecos=" + enderecos + ", id=" + id + "]";
	}
	
	public static void validar(Pessoa pessoa) throws ValidacaoException {
		if (pessoa == null) {
			throw new ValidacaoException("Os dados pessoais do integrante do Clube do Livro devem ser informados");
		}
		
		LocalDate nascimento = pessoa.getNascimento();
		
		if (nascimento != null) {
			String data = DataUtil.formatarData(nascimento);
			String dataPattern = "^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$";
			
			if (!Pattern.matches(dataPattern, data)) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro não é válida");
			}
			
			if (nascimento.isEqual(LocalDate.now()) || nascimento.isAfter(LocalDate.now())) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro deve ser anterior ao dia de hoje");
			}
		}
		
		if (StringUtil.stringNulaOuVazia(pessoa.getNome())) {
			throw new ValidacaoException("O nome do integrante do Clube do Livro deve ser informado");
		}
		
		List<Documento> documentos = pessoa.getDocumentos();
		boolean possuiDocumento = false;
		
		if (documentos != null) {
			
			for (Documento documento: documentos) {
				if (documento != null && StringUtil.stringNaoNulaENaoVazia(documento.getValor())) {
					possuiDocumento = true;
					String cpf = documento.getValor();
					// TODO validar o CPF
					
					if (cpf.length() != 11) {
						throw new ValidacaoException("O CPF informado não é válido");
					}
				}
			}
		}
		
		if (!possuiDocumento) {
			throw new ValidacaoException("O CPF do integrante do Clube do Livro deve ser informado");
		}
		
		List<Endereco> enderecos = pessoa.getEnderecos();
		
		if (enderecos != null) {
			for (Endereco endereco: enderecos) {
				if (endereco != null) {
					Endereco.validar(endereco);
				}
			}
		}
		
		List<Contato> contatos = pessoa.getContatos();
		
		if (contatos != null) {
			for (Contato contato: contatos) {
				if (contato != null) {
					Contato.validar(contato);
				}
			}
		}
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	
	public void setNascimento(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			this.nascimento = DataUtil.parserData(data);
		}
	}
	
	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	
	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

}
