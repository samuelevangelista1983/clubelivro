package br.org.crvnluz.editora.clubelivro.configuracao.classificacao;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Classificacao extends Persistente {
	
	private static final long serialVersionUID = -7104112976155779623L;
	
	private String nome;
	
	// CONSTRUTORES PÚBLICOS
	
	public Classificacao() {}
	
	public Classificacao(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Classificacao [nome=" + nome + ", id=" + id + "]";
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
