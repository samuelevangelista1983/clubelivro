package br.org.crvnluz.editora.clubelivro.controlador;

import java.io.Serializable;

public class Parametro implements Serializable {
	
	private static final long serialVersionUID = 2237554038199834197L;
	
	private Long id;
	private String nome;
	
	public Parametro() {}

	public Parametro(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return String.format("Parametro [id=%s, nome=%s]", id, nome);
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
	
}
