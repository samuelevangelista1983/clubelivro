package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;

@Entity
public class Contato implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -8353384709611010050L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_integrante", nullable = false)
	private Integrante integrante;
	@ManyToOne
	@JoinColumn(name = "id_tipo", nullable = false)
	private TipoContato tipo;
	private String valor;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS

	public Contato() {}
	
	public Contato(TipoContato tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	}

	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Contato clone = new Contato();
		clone.id = id;
		clone.valor = valor;
		clone.observacao = observacao;
		clone.tipo = tipo != null ? (TipoContato) tipo.clone() : null;
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((integrante == null) ? 0 : integrante.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (!(obj instanceof Contato)) {
			return false;
		}
		Contato other = (Contato) obj;
		if (integrante == null) {
			if (other.integrante != null) {
				return false;
			}
		} else if (!integrante.equals(other.integrante)) {
			return false;
		}
		if (observacao == null) {
			if (other.observacao != null) {
				return false;
			}
		} else if (!observacao.equals(other.observacao)) {
			return false;
		}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		} else if (!tipo.equals(other.tipo)) {
			return false;
		}
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Contato [id=%s, integrante=%s, tipo=%s, valor=%s, observacao=%s]", id, integrante, tipo,
				valor, observacao);
	}
	
	public static void validar(Contato contato) throws ValidacaoException {
		TipoContato tipo = contato.getTipo();
		
		if (tipo == null || tipo.getId() == null) {
			throw new ValidacaoException("O tipo do contato do integrante do Clube do Livro deve ser informado");
		}
		
		Long idTipo = tipo.getId();
		String valor = contato.getValor();
		
		if (idTipo == 1) {
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches("[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}", valor)) {
				throw new ValidacaoException("O telefone fixo do integrante do Clube do Livro não é válido");
			}
			
		} else if (idTipo == 2 || idTipo == 4) {
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches("[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}", valor)) {
				throw new ValidacaoException("O telefone celular do integrante do Clube do Livro não é válido");
			}
			
			if (idTipo == 5) {
				contato.setId(null);
			}
			
		} else if (idTipo == 3) {
			String mailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches(mailPattern, valor)) {
				throw new ValidacaoException("O e-mail do integrante do Clube do Livro não é válido");
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoContato getTipo() {
		return tipo;
	}

	public void setTipo(TipoContato tipo) {
		this.tipo = tipo;
	}

	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

}
