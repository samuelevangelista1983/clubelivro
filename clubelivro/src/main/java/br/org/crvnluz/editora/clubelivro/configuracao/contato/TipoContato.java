package br.org.crvnluz.editora.clubelivro.configuracao.contato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "tipo_contato")
public class TipoContato extends Persistente {
	
	private static final long serialVersionUID = 765250808196637755L;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private Boolean ativo;
	
	// CONSTRUTORES PÚBLICOS
	
	public TipoContato() {}
	
	public TipoContato(Long id, String nome, Boolean ativo) {
		super(id);
		this.nome = nome;
		this.ativo = ativo;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TipoContato(id, nome, ativo);
	}
	
	@Override
	public String toString() {
		return "TipoContato [nome=" + nome + ", ativo=" + ativo + ", id=" + id + "]";
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
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}
