package crvnluz.pessoas.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

public class Atualizacao implements Serializable {
	
	private static final long serialVersionUID = 1612209533774427688L;
	
	private LocalDateTime momento;
	private String origem;
	
	public Atualizacao() {}

	public Atualizacao(LocalDateTime momento, String origem) {
		this.momento = momento;
		this.origem = origem;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atualizacao other = (Atualizacao) obj;
		if (momento == null) {
			if (other.momento != null)
				return false;
		} else if (!momento.equals(other.momento))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((momento == null) ? 0 : momento.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Atualizacao [momento=" + momento + ", origem=" + origem + "]";
	}

	public static void validar(Atualizacao atualizacao) {
		if (atualizacao == null) {
			throw new IllegalArgumentException("O registro de atualização da pessoa não pode ser nulo");
		}
		
		if (!StringUtils.hasText(atualizacao.origem)) {
			throw new IllegalArgumentException("A origem da atualização da pessoa não pode ser vazia");
		}
		
		if (atualizacao.momento == null) {
			throw new IllegalArgumentException("O momento da atualização da pessoa não pode ser nulo");
		}
		
		if (atualizacao.momento.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("O momento da atualização da pessoa não pode ser um instante no futuro");
		}
	}
	
	public LocalDateTime getMomento() {
		return momento;
	}

	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
}
