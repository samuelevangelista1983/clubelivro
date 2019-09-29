package br.org.crvnluz.editora.clubelivro.controlador.financeiro.inadimplencia;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Inadimplente implements Serializable {
	
	private static final long serialVersionUID = 7830710861247543715L;
	
	private String nome;
	private String categoria;
	private int qtdEmAtraso;
	private Double valor;
	private NumberFormat format;
	
	public Inadimplente() {
		format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	}

	public Inadimplente(String nome, String categoria, int qtdEmAtraso, Double valor) {
		this();
		this.nome = nome;
		this.categoria = categoria;
		this.qtdEmAtraso = qtdEmAtraso;
		this.valor = valor;
	}

	@Override
	public String toString() {
		return String.format("Inadimplente [nome=%s, categoria=%s, qtdEmAtraso=%s, valor=%s]", nome, categoria, qtdEmAtraso, valor);
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

	public Double getValor() {
		return valor;
	}
	
	public String getValorDevido() {
		return format.format(valor);
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
