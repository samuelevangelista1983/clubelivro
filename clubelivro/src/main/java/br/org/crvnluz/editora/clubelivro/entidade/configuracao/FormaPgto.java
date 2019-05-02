package br.org.crvnluz.editora.clubelivro.entidade.configuracao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FormaPgto implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 2538948760526056266L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double custo;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaPgto() {}
	
	public FormaPgto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public FormaPgto(Long id, String nome, Double custo) {
		this.id = id;
		this.nome = nome;
		this.custo = custo;
	}

	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new FormaPgto(id, nome, custo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custo == null) ? 0 : custo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (!(obj instanceof FormaPgto)) {
			return false;
		}
		FormaPgto other = (FormaPgto) obj;
		if (custo == null) {
			if (other.custo != null) {
				return false;
			}
		} else if (!custo.equals(other.custo)) {
			return false;
		}
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
		return true;
	}

	@Override
	public String toString() {
		return String.format("FormaPgto [id=%s, nome=%s, custo=%s]", id, nome, custo);
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
	
	public Double getCusto() {
		return custo;
	}
	
	public void setCusto(Double custo) {
		this.custo = custo != null ? custo : Double.valueOf(0.0);
	}
	
}
