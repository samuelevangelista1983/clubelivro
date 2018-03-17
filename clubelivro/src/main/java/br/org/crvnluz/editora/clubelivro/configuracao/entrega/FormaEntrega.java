package br.org.crvnluz.editora.clubelivro.configuracao.entrega;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "clube_livro_entrega")
public class FormaEntrega extends Persistente {
	
	private static final long serialVersionUID = 1112957052909094804L;
	
	@Column(nullable = false)
	private String nome;
	@Column
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaEntrega() {}
	
	public FormaEntrega(Long id) {
		super(id);
	}
	
	public FormaEntrega(Long id, String nome, String observacao) {
		super(id);
		this.nome = nome;
		this.observacao = observacao;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new FormaEntrega(id, nome, observacao);
	}
	
	@Override
	public String toString() {
		return "FormaEntrega [nome=" + nome + ", observacao=" + observacao + ", id=" + id + "]";
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
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
