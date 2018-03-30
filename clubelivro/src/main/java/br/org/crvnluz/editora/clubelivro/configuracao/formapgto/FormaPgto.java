package br.org.crvnluz.editora.clubelivro.configuracao.formapgto;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class FormaPgto extends Persistente {
	
	private static final long serialVersionUID = 2196715163766520711L;
	
	private String nome;
	private Double custo;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaPgto() {}
	
	public FormaPgto(Long id) {
		super(id);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "FormaPgto [nome=" + nome + ", custo=" + custo + ", id=" + id + "]";
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getCusto() {
		return custo;
	}
	
	public void setCusto(Double custo) {
		this.custo = custo != null ? custo : Double.valueOf(0.0);
	}
	
}
