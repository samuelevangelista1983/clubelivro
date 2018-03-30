package br.org.crvnluz.editora.clubelivro.configuracao.frequencia;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Frequencia extends Persistente {
	
	private static final long serialVersionUID = 925973652926318355L;
	
	private String nome;
	private Integer freqMensal;
	
	// CONSTRUTORES PÚBLICOS
	
	public Frequencia() {}
	
	public Frequencia(Long id) {
		super(id);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Frequencia [nome=" + nome + ", freqMensal=" + freqMensal + ", id=" + id + "]";
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getFreqMensal() {
		return freqMensal;
	}
	
	public void setFreqMensal(Integer freqMensal) {
		this.freqMensal = freqMensal;
	}
	
}
