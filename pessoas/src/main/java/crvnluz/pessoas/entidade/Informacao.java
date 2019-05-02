package crvnluz.pessoas.entidade;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.util.StringUtils;

public class Informacao implements Serializable {

	private static final long serialVersionUID = 4552451709047895164L;
	
	private String nome;
	private String valor;
	private String observacao;
	@Transient
	private String operacao;
	
	public Informacao() {}

	public Informacao(String nome, String valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public Informacao(String nome, String valor, String operacao) {
		this.nome = nome;
		this.valor = valor;
		this.operacao = operacao;
	}

	public Informacao(String nome, String valor, String operacao, String observacao) {
		this.nome = nome;
		this.valor = valor;
		this.operacao = operacao;
		this.observacao = observacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informacao other = (Informacao) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Informacao [nome=" + nome + ", valor=" + valor + ", observacao=" + observacao + ", operacao=" + operacao
				+ "]";
	}

	public static void validar(Informacao informacao) {
		if (informacao == null) {
			throw new IllegalArgumentException("A informação pessoal não pode ser nula");
		}

		if (!StringUtils.hasText(informacao.operacao)) {
			throw new IllegalArgumentException("A operação não pode ser vazia");
		}
		
		if (!StringUtils.hasText(informacao.nome)) {
			throw new IllegalArgumentException("O nome da informação pessoal não pode ser vazio");
		}
		
		if (!StringUtils.hasText(informacao.valor)) {
			throw new IllegalArgumentException("O valor da informação pessoal não pode ser vazio");
		}
		
		if (informacao.observacao != null) {
			if (!StringUtils.hasText(informacao.observacao)) {
				informacao.observacao = null;
			}
		}
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

}
