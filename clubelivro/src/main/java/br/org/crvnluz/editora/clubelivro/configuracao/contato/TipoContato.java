package br.org.crvnluz.editora.clubelivro.configuracao.contato;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class TipoContato extends Persistente {
	
	private static final long serialVersionUID = 765250808196637755L;
	
	private String nome;
	private Boolean ativo;
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "TipoContato [nome=" + nome + ", ativo=" + ativo + ", id=" + id + "]";
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}
