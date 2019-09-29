package br.org.crvnluz.editora.clubelivro.controlador.financeiro.inadimplencia;

import java.io.Serializable;

public class Inadimplente implements Serializable {
	
	private static final long serialVersionUID = 7830710861247543715L;
	
	private String nome;
	private String categoria;
	private int qtdEmAtraso;
	
	public Inadimplente() {}

	public Inadimplente(String nome, String categoria, int qtdEmAtraso) {
		this.nome = nome;
		this.categoria = categoria;
		this.qtdEmAtraso = qtdEmAtraso;
	}

	@Override
	public String toString() {
		return String.format("Inadimplente [nome=%s, categoria=%s, qtdEmAtraso=%s]", nome, categoria, qtdEmAtraso);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getQtdEmAtraso() {
		return qtdEmAtraso;
	}

	public void setQtdEmAtraso(int qtdEmAtraso) {
		this.qtdEmAtraso = qtdEmAtraso;
	}
	
}
