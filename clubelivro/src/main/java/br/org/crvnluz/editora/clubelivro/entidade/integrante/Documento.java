package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Deprecated
public class Documento implements Serializable {
	
	private static final long serialVersionUID = 397634443871769586L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_pessoa", nullable = false)
	private Pessoa pessoa;
	
	private String valor;
	
	// CONSTRUTORES PÚBLICOS
	
	public Documento() {}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return String.format("Documento [id=%s, pessoa=%s, tipo=%s, valor=%s]", id, pessoa, valor);
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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


}
