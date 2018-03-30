package br.org.crvnluz.editora.clubelivro.configuracao.entrega;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class FormaEntrega extends Persistente {
	
	private static final long serialVersionUID = 1112957052909094804L;
	
	private String nome;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaEntrega() {}
	
	public FormaEntrega(Long id) {
		super(id);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "FormaEntrega [nome=" + nome + ", observacao=" + observacao + ", id=" + id + "]";
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
