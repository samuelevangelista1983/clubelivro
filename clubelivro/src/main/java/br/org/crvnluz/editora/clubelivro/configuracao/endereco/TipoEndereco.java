package br.org.crvnluz.editora.clubelivro.configuracao.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "tipo_endereco")
public class TipoEndereco extends Persistente {
	
	private static final long serialVersionUID = -7421661452736520471L;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String ativo;
	
	// CONSTRUTORES PÚBLICOS
	
	public TipoEndereco() {}
	
	public TipoEndereco(Long id,String nome, String ativo) {
		super(id);
		this.nome = nome;
		this.ativo = ativo;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TipoEndereco(id, nome, ativo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TipoEndereco other = (TipoEndereco) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TipoEndereco [nome=" + nome + ", ativo=" + ativo + ", id=" + id + "]";
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
	
	public String getAtivo() {
		return ativo;
	}
	
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
