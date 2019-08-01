package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;

public class PainelIntegrante implements Serializable {
	
	private static final long serialVersionUID = -3617459505732054832L;
	
	private Integer ativos;
	private Integer inativos;
	private Integer mensal;
	private Integer bimestral;
	private Integer correios;
	private Integer presencial;
	private Integer boleto;
	private Integer debito;
	private Integer credito;
	private Integer dinheiro;
	private Integer cheque;
	private Integer estudo;
	private Integer romance;
	private Integer estudoRomance;
	private Integer estudoRomanceAlternado;
	
	// CONSTRUTORES PÚBLICOS
	
	public PainelIntegrante() {}
	
	// MÉTODOS PÚBLICOS
	
	public Integer getAtivos() {
		return ativos;
	}

	public void setAtivos(Integer ativos) {
		this.ativos = ativos;
	}

	public Integer getInativos() {
		return inativos;
	}

	public void setInativos(Integer inativos) {
		this.inativos = inativos;
	}

	public Integer getMensal() {
		return mensal;
	}

	public void setMensal(Integer mensal) {
		this.mensal = mensal;
	}

	public Integer getBimestral() {
		return bimestral;
	}

	public void setBimestral(Integer bimestral) {
		this.bimestral = bimestral;
	}

	public Integer getCorreios() {
		return correios;
	}

	public void setCorreios(Integer correios) {
		this.correios = correios;
	}

	public Integer getPresencial() {
		return presencial;
	}

	public void setPresencial(Integer presencial) {
		this.presencial = presencial;
	}

	public Integer getBoleto() {
		return boleto;
	}

	public void setBoleto(Integer boleto) {
		this.boleto = boleto;
	}

	public Integer getDebito() {
		return debito;
	}

	public void setDebito(Integer debito) {
		this.debito = debito;
	}

	public Integer getCredito() {
		return credito;
	}

	public void setCredito(Integer credito) {
		this.credito = credito;
	}

	public Integer getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(Integer dinheiro) {
		this.dinheiro = dinheiro;
	}

	public Integer getCheque() {
		return cheque;
	}

	public void setCheque(Integer cheque) {
		this.cheque = cheque;
	}

	public Integer getEstudo() {
		return estudo;
	}

	public void setEstudo(Integer estudo) {
		this.estudo = estudo;
	}

	public Integer getRomance() {
		return romance;
	}

	public void setRomance(Integer romance) {
		this.romance = romance;
	}

	public Integer getEstudoRomance() {
		return estudoRomance;
	}

	public void setEstudoRomance(Integer estudoRomance) {
		this.estudoRomance = estudoRomance;
	}

	public Integer getEstudoRomanceAlternado() {
		return estudoRomanceAlternado;
	}

	public void setEstudoRomanceAlternado(Integer estudoRomanceAlternado) {
		this.estudoRomanceAlternado = estudoRomanceAlternado;
	}
	
}
