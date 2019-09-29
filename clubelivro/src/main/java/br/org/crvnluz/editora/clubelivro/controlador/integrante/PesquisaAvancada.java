package br.org.crvnluz.editora.clubelivro.controlador.integrante;

import java.io.Serializable;

public class PesquisaAvancada implements Serializable {
	
	private static final long serialVersionUID = 1212263473824961244L;
	
	private String nome;
	private String cpf;
	private Long idCategoria;
	private Long idFrequencia;
	private Long idFormaEntrega;
	private Long idFormaPgto;
	private Integer situacao;
	private Integer ordenacao;
	
	@Override
	public String toString() {
		return String.format(
				"PesquisaAvancada [nome=%s, cpf=%s, idCategoria=%s, idFrequencia=%s, idFormaEntrega=%s, idFormaPgto=%s, situacao=%s, ordenacao=%s]",
				nome, cpf, idCategoria, idFrequencia, idFormaEntrega, idFormaPgto, situacao, ordenacao);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdFrequencia() {
		return idFrequencia;
	}

	public void setIdFrequencia(Long idFrequencia) {
		this.idFrequencia = idFrequencia;
	}

	public Long getIdFormaEntrega() {
		return idFormaEntrega;
	}

	public void setIdFormaEntrega(Long idFormaEntrega) {
		this.idFormaEntrega = idFormaEntrega;
	}

	public Long getIdFormaPgto() {
		return idFormaPgto;
	}

	public void setIdFormaPgto(Long idFormaPgto) {
		this.idFormaPgto = idFormaPgto;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Integer getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}
	
}
