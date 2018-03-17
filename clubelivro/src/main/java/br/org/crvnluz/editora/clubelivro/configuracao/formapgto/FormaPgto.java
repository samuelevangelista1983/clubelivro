package br.org.crvnluz.editora.clubelivro.configuracao.formapgto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "clube_livro_forma_pgto")
public class FormaPgto extends Persistente {
	
	private static final long serialVersionUID = 2196715163766520711L;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private Double custo;
	
	// CONSTRUTORES PÚBLICOS
	
	public FormaPgto() {}
	
	public FormaPgto(Long id) {
		super(id);
	}
	
	public FormaPgto(Long id, String nome, Double custo) {
		super(id);
		this.nome = nome;
		this.custo = custo;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new FormaPgto(id, nome, custo);
	}
	
	@Override
	public String toString() {
		return "FormaPgto [nome=" + nome + ", custo=" + custo + ", id=" + id + "]";
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
	
	public Double getCusto() {
		return custo;
	}
	
	public void setCusto(Double custo) {
		this.custo = custo != null ? custo : Double.valueOf(0.0);
	}
	
}
