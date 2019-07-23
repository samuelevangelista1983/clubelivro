package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.eti.sen.endereco.Enderecavel;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;

@Entity
public class Endereco implements Enderecavel, Serializable, Cloneable {
	
	private static final long serialVersionUID = -2035685489734474389L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_integrante")
	private Integrante integrante;
	private int tipo; // 0 - residencial; 1 - comercial
	private boolean cobranca;
	private boolean entrega;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public Endereco() {}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Endereco clone = new Endereco();
		clone.id = id;
		clone.tipo = tipo;
		clone.cobranca = cobranca;
		clone.entrega = entrega;
		clone.cep = cep;
		clone.logradouro = logradouro;
		clone.numero = numero;
		clone.complemento = complemento;
		clone.bairro = bairro;
		clone.cidade = cidade;
		clone.uf = uf;
		clone.observacao = observacao;
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + tipo;
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Endereco)) {
			return false;
		}
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		} else if (!bairro.equals(other.bairro)) {
			return false;
		}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (cidade == null) {
			if (other.cidade != null) {
				return false;
			}
		} else if (!cidade.equals(other.cidade)) {
			return false;
		}
		if (complemento == null) {
			if (other.complemento != null) {
				return false;
			}
		} else if (!complemento.equals(other.complemento)) {
			return false;
		}
		if (logradouro == null) {
			if (other.logradouro != null) {
				return false;
			}
		} else if (!logradouro.equals(other.logradouro)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (observacao == null) {
			if (other.observacao != null) {
				return false;
			}
		} else if (!observacao.equals(other.observacao)) {
			return false;
		}
		if (tipo != other.tipo) {
			return false;
		}
		if (uf == null) {
			if (other.uf != null) {
				return false;
			}
		} else if (!uf.equals(other.uf)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Endereco [id=%s, tipo=%s, cobranca=%s, entrega=%s, cep=%s, logradouro=%s, numero=%s, complemento=%s, bairro=%s, cidade=%s, uf=%s, observacao=%s]",
				id, tipo, cobranca, entrega, cep, logradouro, numero, complemento, bairro, cidade, uf, observacao);
	}
	
	public static void validar(Endereco endereco) throws ValidacaoException {
		int tipo = endereco.tipo;
		
		if (tipo < 0 || tipo > 1) {
			throw new ValidacaoException("O tipo do endereço do integrante do Clube do Livro deve ser residencial ou comercial");
		}
		
		String cep = endereco.getCep();
		
		if (StringUtil.stringNulaOuVazia(cep)) {
			throw new ValidacaoException("O CEP do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (cep.length() != 8) {
			throw new ValidacaoException("O CEP do endereço do integrante do Clube do Livro não é válido");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getLogradouro())) {
			throw new ValidacaoException("O logradouro do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getNumero())) {
			throw new ValidacaoException("O número do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getBairro())) {
			throw new ValidacaoException("O bairro do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getCidade())) {
			throw new ValidacaoException("A cidade do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getUf())) {
			throw new ValidacaoException("A UF do endereço do integrante do Clube do Livro deve ser informada");
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getCidade() {
		return cidade;
	}

	@Override
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isCobranca() {
		return cobranca;
	}

	public void setCobranca(boolean cobranca) {
		this.cobranca = cobranca;
	}

	public boolean isEntrega() {
		return entrega;
	}

	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

}
