package br.org.crvnluz.editora.clubelivro.configuracao.classificacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "clube_livro_classificacao")
public class Classificacao extends Persistente {
	
	private static final long serialVersionUID = -7104112976155779623L;
	
	@Column(nullable = false)
	private String nome;
	
	// CONSTRUTORES PÚBLICOS
	
	public Classificacao() {}
	
	public Classificacao(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Classificacao(id, nome);
	}
	
	@Override
	public String toString() {
		return "Classificacao [nome=" + nome + ", id=" + id + "]";
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
	
}
