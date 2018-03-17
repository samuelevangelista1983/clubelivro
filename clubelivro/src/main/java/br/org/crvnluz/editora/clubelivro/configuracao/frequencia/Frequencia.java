package br.org.crvnluz.editora.clubelivro.configuracao.frequencia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "clube_livro_frequencia")
public class Frequencia extends Persistente {
	
	private static final long serialVersionUID = 925973652926318355L;
	
	@Column(nullable = false)
	private String nome;
	@Column(name = "freq_mensal", nullable = false)
	private Integer freqMensal;
	
	// CONSTRUTORES PÚBLICOS
	
	public Frequencia() {}
	
	public Frequencia(Long id) {
		super(id);
	}
	
	public Frequencia(Long id, String nome, Integer freqMensal) {
		super(id);
		this.nome = nome;
		this.freqMensal = freqMensal;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Frequencia(id, nome, freqMensal);
	}
	
	@Override
	public String toString() {
		return "Frequencia [nome=" + nome + ", freqMensal=" + freqMensal + ", id=" + id + "]";
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
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
