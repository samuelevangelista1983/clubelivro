package br.org.crvnluz.editora.clubelivro.configuracao.endereco;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class TipoEndereco extends Persistente {
	
	private static final long serialVersionUID = -7421661452736520471L;
	
	private String nome;
	private Boolean ativo;
	
	// MÉTODOS PÚBLICOS
	
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
