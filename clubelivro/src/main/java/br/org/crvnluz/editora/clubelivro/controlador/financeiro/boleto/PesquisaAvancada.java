package br.org.crvnluz.editora.clubelivro.controlador.financeiro.boleto;

import java.io.Serializable;

public class PesquisaAvancada implements Serializable {
	
	private static final long serialVersionUID = -8399340813360602708L;
	
	private String sacado;
	private Long idCategoria;
	private Integer situacao;
	private String dtEmissaoInicial;
	private String dtEmissaoFinal;
	private String dtVctoInicial;
	private String dtVctoFinal;
	private Integer campoOrdenacao;
	private Integer ordenacao;
	
	@Override
	public String toString() {
		return String.format(
				"PesquisaAvancada [sacado=%s, idCategoria=%s, situacao=%s, dtEmissaoInicial=%s, dtEmissaoFinal=%s, dtVctoInicial=%s, dtVctoFinal=%s, campoOrdenacao=%s, ordenacao=%s]",
				sacado, idCategoria, situacao, dtEmissaoInicial, dtEmissaoFinal, dtVctoInicial, dtVctoFinal,
				campoOrdenacao, ordenacao);
	}

	public String getSacado() {
		return sacado;
	}

	public void setSacado(String sacado) {
		this.sacado = sacado;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getDtEmissaoInicial() {
		return dtEmissaoInicial;
	}

	public void setDtEmissaoInicial(String dtEmissaoInicial) {
		this.dtEmissaoInicial = dtEmissaoInicial;
	}

	public String getDtEmissaoFinal() {
		return dtEmissaoFinal;
	}

	public void setDtEmissaoFinal(String dtEmissaoFinal) {
		this.dtEmissaoFinal = dtEmissaoFinal;
	}

	public String getDtVctoInicial() {
		return dtVctoInicial;
	}

	public void setDtVctoInicial(String dtVctoInicial) {
		this.dtVctoInicial = dtVctoInicial;
	}

	public String getDtVctoFinal() {
		return dtVctoFinal;
	}

	public void setDtVctoFinal(String dtVctoFinal) {
		this.dtVctoFinal = dtVctoFinal;
	}

	public Integer getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(Integer campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}

	public Integer getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}

}
