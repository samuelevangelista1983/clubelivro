package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import java.io.Serializable;

public abstract class Persistente implements Serializable {
	
	private static final long serialVersionUID = 5472361596638536334L;
	
	protected Long id;
	
	// CONSTRUTORES PUBLIC
	
	public Persistente() {}
	
	public Persistente(Long id) {
		this.id = id;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persistente other = (Persistente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
