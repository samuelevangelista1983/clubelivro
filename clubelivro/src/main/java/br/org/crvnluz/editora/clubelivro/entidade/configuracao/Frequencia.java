package br.org.crvnluz.editora.clubelivro.entidade.configuracao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Frequencia implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -208939543768172490L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Integer freqMensal;
	
	// CONSTRUTORES PÚBLICOS
	
	public Frequencia() {}
	
	public Frequencia(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Frequencia(Long id, String nome, Integer freqMensal) {
		this.id = id;
		this.nome = nome;
		this.freqMensal = freqMensal;
	}

	// MÉTODOS PÚBLICOS

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Frequencia(id, nome, freqMensal);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freqMensal == null) ? 0 : freqMensal.hashCode());
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
		if (!(obj instanceof Frequencia)) {
			return false;
		}
		Frequencia other = (Frequencia) obj;
		if (freqMensal == null) {
			if (other.freqMensal != null) {
				return false;
			}
		} else if (!freqMensal.equals(other.freqMensal)) {
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
		return String.format("Frequencia [id=%s, nome=%s, freqMensal=%s]", id, nome, freqMensal);
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
	
	public Integer getFreqMensal() {
		return freqMensal;
	}
	
	public void setFreqMensal(Integer freqMensal) {
		this.freqMensal = freqMensal;
	}
	
}
