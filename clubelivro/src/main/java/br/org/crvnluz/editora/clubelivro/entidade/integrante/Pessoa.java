package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.eti.sen.utilitarios.documento.CPF;
import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;

@Deprecated
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = -7656051841601022472L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String nascimentoStr;
	private LocalDate nascimento;
	@OneToMany(mappedBy="id_pessoa")
	private List<Contato> contatos;
	@OneToMany(mappedBy="id_pessoa")
	private List<Documento> documentos;
	@OneToMany(mappedBy="id_pessoa")
	private List<Endereco> enderecos;
	
	// CONSTRUTORES PÚBLICOS
	
	public Pessoa() {}
	
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
		
		if (StringUtil.stringNaoNulaENaoVazia(pessoa.nascimentoStr)) {
			LocalDate nascimento = pessoa.nascimento;
			
			if (nascimento == null) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro não é válida");
			}
			
			String data = DataUtil.formatarData(nascimento);
			
			if (!pessoa.nascimentoStr.equals(data)) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro não é válida");
			}
			
			if (nascimento.isEqual(LocalDate.now()) || nascimento.isAfter(LocalDate.now())) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro deve ser anterior ao dia de hoje");
			}
		}
		
		List<Documento> documentos = pessoa.documentos;
		boolean possuiDocumento = false;
		
		if (documentos != null) {
			
			for (Documento documento: documentos) {
				if (documento != null && StringUtil.stringNaoNulaENaoVazia(documento.getValor())) {
					possuiDocumento = true;
					String cpf = documento.getValor();
					
					if (!CPF.validar(cpf)) {
						throw new ValidacaoException("O CPF informado não é válido");
					}
				}
			}
		}
		
		if (!possuiDocumento) {
			throw new ValidacaoException("O CPF do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(pessoa.nome)) {
			throw new ValidacaoException("O nome do integrante do Clube do Livro deve ser informado");
		}
		
		List<Endereco> enderecos = pessoa.enderecos;
		
		if (enderecos != null) {
			for (Endereco endereco: enderecos) {
				if (endereco != null) {
					Endereco.validar(endereco);
				}
			}
		}
		
		List<Contato> contatos = pessoa.contatos;
		
		if (contatos != null) {
			for (Contato contato: contatos) {
				if (contato != null) {
					Contato.validar(contato);
				}
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
			nascimentoStr = data;
			
			try {
				nascimento = DataUtil.parserData(data);
				
			} catch (Exception e) {
				// TODO: registrar em log a falha em converter a String em LocalDate
			}
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
