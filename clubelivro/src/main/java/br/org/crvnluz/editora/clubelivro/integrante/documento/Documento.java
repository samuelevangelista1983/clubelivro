package br.org.crvnluz.editora.clubelivro.integrante.documento;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Documento extends Persistente {
	
	private static final long serialVersionUID = 6319628949480799148L;
	
	private Long idPessoa;
	private Long idTipo;
	private String valor;
	
	// CONSTRUTORES PÚBLICOS
	
	public Documento() {}
	
	public Documento(Long id) {
		super(id);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Documento [id_pessoa=" + idPessoa + ", id_tipo=" + idTipo + ", valor=" + valor + ", id=" + id + "]";
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public Long getIdTipo() {
		return idTipo;
	}
	
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
	
}
