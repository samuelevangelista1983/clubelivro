package br.org.crvnluz.editora.clubelivro.controlador.financeiro.fluxoreceita;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class FluxoReceita implements Serializable {
	
	private static final long serialVersionUID = 4123530299538887750L;
	
	private int ano;
	private String mes;
	private Double previsto;
	private Double realizado;
	private NumberFormat format;
	
	public FluxoReceita() {
		format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Double getPrevisto() {
		return previsto;
	}
	
	public String getValorPrevisto() {
		return format.format(previsto);
	}
	
	public void setPrevisto(Double previsto) {
		this.previsto = previsto;
	}

	public Double getRealizado() {
		return realizado;
	}

	public String getValorRealizado() {
		return format.format(realizado);
	}
	
	public void setRealizado(Double realizado) {
		this.realizado = realizado;
	}
	
	public String getDiferenca() {
		return format.format(realizado - previsto);
	}
	
}
