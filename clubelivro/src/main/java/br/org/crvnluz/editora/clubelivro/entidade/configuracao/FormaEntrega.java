package br.org.crvnluz.editora.clubelivro.entidade.configuracao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FormaEntrega implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 3261634376324885241L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaEntrega() {}
	
	public FormaEntrega(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public FormaEntrega(Long id, String nome, String observacao) {
		this.id = id;
		this.nome = nome;
		this.observacao = observacao;
	}

	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new FormaEntrega(id, nome, observacao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
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
		if (!(obj instanceof FormaEntrega)) {
			return false;
		}
		FormaEntrega other = (FormaEntrega) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (observacao == null) {
			if (other.observacao != null) {
				return false;
			}
		} else if (!observacao.equals(other.observacao)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("FormaEntrega [id=%s, nome=%s, observacao=%s]", id, nome, observacao);
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
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
