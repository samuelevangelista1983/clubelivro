package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;

public class Inadimplencia implements Serializable {
	
	private static final long serialVersionUID = -5974433474618677798L;
	
	private InadimplenciaCategoria inadimplenciaCategoria;
	private InadimplenciaMensal inadimplenciaMensal;
	
	// MÉTODOS PÚBLICOS
	
	public InadimplenciaCategoria getInadimplenciaCategoria() {
		return inadimplenciaCategoria;
	}
	
	public void setInadimplenciaCategoria(InadimplenciaCategoria inadimplenciaCategoria) {
		this.inadimplenciaCategoria = inadimplenciaCategoria;
	}
	
	public InadimplenciaMensal getInadimplenciaMensal() {
		return inadimplenciaMensal;
	}
	
	public void setInadimplenciaMensal(InadimplenciaMensal inadimplenciaMensal) {
		this.inadimplenciaMensal = inadimplenciaMensal;
	}
	
}
