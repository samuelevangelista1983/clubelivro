package br.org.crvnluz.editora.clubelivro.configuracao.documento;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class TipoDocumento extends Persistente {
	
	private static final long serialVersionUID = -7055647960969273822L;
	
	private String nome;
	private Boolean ativo;
	
	// CONSTRUTORES PÚBLICOS
	
	public TipoDocumento() {}

	public TipoDocumento(Long id, String nome, Boolean ativo) {
		super(id);
		this.nome = nome;
		this.ativo = ativo;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "TipoDocumento [nome=" + nome + ", ativo=" + ativo + ", id=" + id + "]";
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
