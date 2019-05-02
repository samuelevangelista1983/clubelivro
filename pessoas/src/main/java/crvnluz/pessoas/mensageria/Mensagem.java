package crvnluz.pessoas.mensageria;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import crvnluz.pessoas.entidade.Pessoa;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Mensagem implements Serializable {

	private static final long serialVersionUID = 8244669349409464197L;
	
	public static final Integer ADICIONAR = 0;
	public static final Integer ATUALIZAR = 1;
	public static final Integer REMOVER = 2;
	
	private Integer operacao; // 0 - adicionar; 1 - atualizar; 2 - remover
	private Pessoa pessoa;
	
	public Mensagem() {}

	public Mensagem(Integer operacao, Pessoa pessoa) {
		this.operacao = operacao;
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return String.format("Mensagem [operacao=%s, pessoa=%s]", operacao, pessoa);
	}

	public Integer getOperacao() {
		return operacao;
	}

	public void setOperacao(Integer operacao) {
		this.operacao = operacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
