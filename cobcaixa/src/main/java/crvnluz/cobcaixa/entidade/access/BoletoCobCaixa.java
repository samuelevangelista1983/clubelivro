package crvnluz.cobcaixa.entidade.access;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tabTitulos")
public class BoletoCobCaixa implements Serializable {

	private static final long serialVersionUID = 2609717442452847581L;
	
	@EmbeddedId
	private BoletoCobCaixaPK id;
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "lcedeSeqCedente", insertable = false, updatable = false),
		@JoinColumn(name = "ssacaCodSacado", insertable = false, updatable = false)
	})
	private SacadoCobCaixa sacado;
	@Column(name = "itituOpcVcto")
	private Integer opcaoVencimento;
	@Column(name = "ttituDtVcto")
	private LocalDate dataVencimento;
	@Column(name = "ttituDtDoc")
	private LocalDate dataDocumento;
	@Column(name = "stituEspecie")
	private String especie;
	@Column(name = "btituAceite")
	private Boolean aceite;
	@Column(name = "ttituDtProc")
	private LocalDate dataProcessamento;
	@Column(name = "stituNossoNum")
	private String nossoNumero;
	@Column(name = "ytitutpCart")
	private Integer tipoCobranca;
	@Column(name = "dtituValDoc")
	private Double valorDocumento;
	@Column(name = "dtituValDesc")
	private Double valorDesconto;
	@Column(name = "ttituDtDesc")
	private LocalDate dataLimiteDesconto;
	@Column(name = "dtituValMulta")
	private Double valorMulta;
	@Column(name = "ttituDtMulta")
	private LocalDate dataLimiteMulta;
	@Column(name = "dtituValJuros")
	private Double valorJuros;
	@Column(name = "dtituValAbat")
	private Double valorAbatimento;
	@Column(name = "dtituAcres")
	private Double outrosAcrescimos;
	@Column(name = "stituMensRec1")
	private String mensagemReciboSacado1;
	@Column(name = "stituMensRec2")
	private String mensagemReciboSacado2;
	@Column(name = "stituMensRec3")
	private String mensagemReciboSacado3;
	@Column(name = "stituMensRec4")
	private String mensagemReciboSacado4;
	@Column(name = "stituFicComp1")
	private String mensagemFichaCompensacao1;
	@Column(name = "stituFicComp2")
	private String mensagemFichaCompensacao2;
	@Column(name = "stituNomeAval")
	private String nomeSacadorAvalista;
	@Column(name = "btituTpAval")
	private Boolean tipoSacadorAvalista;
	@Column(name = "stituCPFCNPJAval")
	private String cpfCnpjSacadorAvalista;
	@Column(name = "itituTotParc")
	private Integer totalParcelas;
	@Column(name = "stituCodRem")
	private String codigoRemessa;
	@Column(name = "stituCodRet")
	private String codigoRetorno;
	@Column(name = "btituFlagRem")
	private Boolean remetido;
	@Column(name = "btituFlagRet")
	private Boolean retornado;
	@Column(name = "stituCodRej1")
	private String codigoRejeicao1;
	@Column(name = "stituCodRej2")
	private String codigoRejeicao2;
	@Column(name = "stituCodRej3")
	private String codigoRejeicao3;
	@Column(name = "ttituDtOco")
	private LocalDate dataOcorrencia;
	@Column(name = "ttituDtPgto")
	private LocalDate dataPagamento;
	@Column(name = "dtituValPgto")
	private Double valorPago;
	@Column(name = "dtituValDescEf")
	private Double valorDescontoEfetivado;
	@Column(name = "dtituValAcrEf")
	private Double valorAcrescimoEfetivado;
	@Column(name = "dtituValAbaEf")
	private Double valorAbatimentoEfetivado;
	@Column(name = "btituMovManual")
	private Boolean movimentacaoManual;
	@Column(name = "dtituValJurEf")
	private Double valorJurosEfetivado;
	@Column(name = "dtituValMulEf")
	private Double valorMultaEfetivada;
	@Column(name = "dtituValCredEf")
	private Double valorCreditoEfetivado;
	@Column(name = "dtituValTarifa")
	private Double valorTarifa;
	@Column(name = "DtCredEf")
	private LocalDate dataEfetivacaoCredito;
	@Column(name = "DtTariEf")
	private LocalDate dataEfetivacaoTarifa;
	@Column(name = "CanalPgto")
	private String canalPagamento;
	@Column(name = "FormaPgto")
	private String formaPagamento;
	@Column(name = "FloatPgto")
	private String floatPagamento;
	@Column(name = "ttituDtDesc2")
	private LocalDate tituDtDesc2;
	@Column(name = "ttituDtDesc3")
	private LocalDate tituDtDesc3;
	@Column(name = "dtituValDesc2")
	private Double tituValDesc2;
	@Column(name = "dtituValDesc3")
	private Double tituValDesc3;
	@Column(name = "btituMovimenta")
	private Boolean movimentacao;
	@Column(name = "stituMotivo")
	private String motivo;
	@Column(name = "stituCodAceito")
	private String codigoAceito;
	@Column(name = "stituCodRejeitado")
	private String codigoRejeitado;
	
	@Override
	public String toString() {
		return String.format(
				"BoletoCobCaixa [id=%s, sacado=%s, opcaoVencimento=%s, dataVencimento=%s, dataDocumento=%s, especie=%s, aceite=%s, dataProcessamento=%s, nossoNumero=%s, tipoCobranca=%s, valorDocumento=%s, valorDesconto=%s, dataLimiteDesconto=%s, valorMulta=%s, dataLimiteMulta=%s, valorJuros=%s, valorAbatimento=%s, outrosAcrescimos=%s, mensagemReciboSacado1=%s, mensagemReciboSacado2=%s, mensagemReciboSacado3=%s, mensagemReciboSacado4=%s, mensagemFichaCompensacao1=%s, mensagemFichaCompensacao2=%s, nomeSacadorAvalista=%s, tipoSacadorAvalista=%s, cpfCnpjSacadorAvalista=%s, totalParcelas=%s, codigoRemessa=%s, codigoRetorno=%s, remetido=%s, retornado=%s, codigoRejeicao1=%s, codigoRejeicao2=%s, codigoRejeicao3=%s, dataOcorrencia=%s, dataPagamento=%s, valorPago=%s, valorDescontoEfetivado=%s, valorAcrescimoEfetivado=%s, valorAbatimentoEfetivado=%s, movimentacaoManual=%s, valorJurosEfetivado=%s, valorMultaEfetivada=%s, valorCreditoEfetivado=%s, valorTarifa=%s, dataEfetivacaoCredito=%s, dataEfetivacaoTarifa=%s, canalPagamento=%s, formaPagamento=%s, floatPagamento=%s, tituDtDesc2=%s, tituDtDesc3=%s, tituValDesc2=%s, tituValDesc3=%s, movimentacao=%s, motivo=%s, codigoAceito=%s, codigoRejeitado=%s]",
				id, sacado, opcaoVencimento, dataVencimento, dataDocumento, especie, aceite, dataProcessamento,
				nossoNumero, tipoCobranca, valorDocumento, valorDesconto, dataLimiteDesconto, valorMulta,
				dataLimiteMulta, valorJuros, valorAbatimento, outrosAcrescimos, mensagemReciboSacado1,
				mensagemReciboSacado2, mensagemReciboSacado3, mensagemReciboSacado4, mensagemFichaCompensacao1,
				mensagemFichaCompensacao2, nomeSacadorAvalista, tipoSacadorAvalista, cpfCnpjSacadorAvalista,
				totalParcelas, codigoRemessa, codigoRetorno, remetido, retornado, codigoRejeicao1, codigoRejeicao2,
				codigoRejeicao3, dataOcorrencia, dataPagamento, valorPago, valorDescontoEfetivado,
				valorAcrescimoEfetivado, valorAbatimentoEfetivado, movimentacaoManual, valorJurosEfetivado,
				valorMultaEfetivada, valorCreditoEfetivado, valorTarifa, dataEfetivacaoCredito, dataEfetivacaoTarifa,
				canalPagamento, formaPagamento, floatPagamento, tituDtDesc2, tituDtDesc3, tituValDesc2, tituValDesc3,
				movimentacao, motivo, codigoAceito, codigoRejeitado);
	}

	public BoletoCobCaixaPK getId() {
		return id;
	}

	public void setId(BoletoCobCaixaPK id) {
		this.id = id;
	}

	public SacadoCobCaixa getSacado() {
		return sacado;
	}

	public void setSacado(SacadoCobCaixa sacado) {
		this.sacado = sacado;
	}

	public Integer getOpcaoVencimento() {
		return opcaoVencimento;
	}

	public void setOpcaoVencimento(Integer opcaoVencimento) {
		this.opcaoVencimento = opcaoVencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(LocalDate dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Boolean getAceite() {
		return aceite;
	}

	public void setAceite(Boolean aceite) {
		this.aceite = aceite;
	}

	public LocalDate getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(LocalDate dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Integer getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(Integer tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public Double getValorDocumento() {
		return valorDocumento;
	}

	public void setValorDocumento(Double valorDocumento) {
		this.valorDocumento = valorDocumento;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataLimiteDesconto() {
		return dataLimiteDesconto;
	}

	public void setDataLimiteDesconto(LocalDate dataLimiteDesconto) {
		this.dataLimiteDesconto = dataLimiteDesconto;
	}

	public Double getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(Double valorMulta) {
		this.valorMulta = valorMulta;
	}

	public LocalDate getDataLimiteMulta() {
		return dataLimiteMulta;
	}

	public void setDataLimiteMulta(LocalDate dataLimiteMulta) {
		this.dataLimiteMulta = dataLimiteMulta;
	}

	public Double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(Double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public Double getValorAbatimento() {
		return valorAbatimento;
	}

	public void setValorAbatimento(Double valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public Double getOutrosAcrescimos() {
		return outrosAcrescimos;
	}

	public void setOutrosAcrescimos(Double outrosAcrescimos) {
		this.outrosAcrescimos = outrosAcrescimos;
	}

	public String getMensagemReciboSacado1() {
		return mensagemReciboSacado1;
	}

	public void setMensagemReciboSacado1(String mensagemReciboSacado1) {
		this.mensagemReciboSacado1 = mensagemReciboSacado1;
	}

	public String getMensagemReciboSacado2() {
		return mensagemReciboSacado2;
	}

	public void setMensagemReciboSacado2(String mensagemReciboSacado2) {
		this.mensagemReciboSacado2 = mensagemReciboSacado2;
	}

	public String getMensagemReciboSacado3() {
		return mensagemReciboSacado3;
	}

	public void setMensagemReciboSacado3(String mensagemReciboSacado3) {
		this.mensagemReciboSacado3 = mensagemReciboSacado3;
	}

	public String getMensagemReciboSacado4() {
		return mensagemReciboSacado4;
	}

	public void setMensagemReciboSacado4(String mensagemReciboSacado4) {
		this.mensagemReciboSacado4 = mensagemReciboSacado4;
	}

	public String getMensagemFichaCompensacao1() {
		return mensagemFichaCompensacao1;
	}

	public void setMensagemFichaCompensacao1(String mensagemFichaCompensacao1) {
		this.mensagemFichaCompensacao1 = mensagemFichaCompensacao1;
	}

	public String getMensagemFichaCompensacao2() {
		return mensagemFichaCompensacao2;
	}

	public void setMensagemFichaCompensacao2(String mensagemFichaCompensacao2) {
		this.mensagemFichaCompensacao2 = mensagemFichaCompensacao2;
	}

	public String getNomeSacadorAvalista() {
		return nomeSacadorAvalista;
	}

	public void setNomeSacadorAvalista(String nomeSacadorAvalista) {
		this.nomeSacadorAvalista = nomeSacadorAvalista;
	}

	public Boolean getTipoSacadorAvalista() {
		return tipoSacadorAvalista;
	}

	public void setTipoSacadorAvalista(Boolean tipoSacadorAvalista) {
		this.tipoSacadorAvalista = tipoSacadorAvalista;
	}

	public String getCpfCnpjSacadorAvalista() {
		return cpfCnpjSacadorAvalista;
	}

	public void setCpfCnpjSacadorAvalista(String cpfCnpjSacadorAvalista) {
		this.cpfCnpjSacadorAvalista = cpfCnpjSacadorAvalista;
	}

	public Integer getTotalParcelas() {
		return totalParcelas;
	}

	public void setTotalParcelas(Integer totalParcelas) {
		this.totalParcelas = totalParcelas;
	}

	public String getCodigoRemessa() {
		return codigoRemessa;
	}

	public void setCodigoRemessa(String codigoRemessa) {
		this.codigoRemessa = codigoRemessa;
	}

	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}

	public Boolean getRemetido() {
		return remetido;
	}

	public void setRemetido(Boolean remetido) {
		this.remetido = remetido;
	}

	public Boolean getRetornado() {
		return retornado;
	}

	public void setRetornado(Boolean retornado) {
		this.retornado = retornado;
	}

	public String getCodigoRejeicao1() {
		return codigoRejeicao1;
	}

	public void setCodigoRejeicao1(String codigoRejeicao1) {
		this.codigoRejeicao1 = codigoRejeicao1;
	}

	public String getCodigoRejeicao2() {
		return codigoRejeicao2;
	}

	public void setCodigoRejeicao2(String codigoRejeicao2) {
		this.codigoRejeicao2 = codigoRejeicao2;
	}

	public String getCodigoRejeicao3() {
		return codigoRejeicao3;
	}

	public void setCodigoRejeicao3(String codigoRejeicao3) {
		this.codigoRejeicao3 = codigoRejeicao3;
	}

	public LocalDate getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(LocalDate dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getValorDescontoEfetivado() {
		return valorDescontoEfetivado;
	}

	public void setValorDescontoEfetivado(Double valorDescontoEfetivado) {
		this.valorDescontoEfetivado = valorDescontoEfetivado;
	}

	public Double getValorAcrescimoEfetivado() {
		return valorAcrescimoEfetivado;
	}

	public void setValorAcrescimoEfetivado(Double valorAcrescimoEfetivado) {
		this.valorAcrescimoEfetivado = valorAcrescimoEfetivado;
	}

	public Double getValorAbatimentoEfetivado() {
		return valorAbatimentoEfetivado;
	}

	public void setValorAbatimentoEfetivado(Double valorAbatimentoEfetivado) {
		this.valorAbatimentoEfetivado = valorAbatimentoEfetivado;
	}

	public Boolean getMovimentacaoManual() {
		return movimentacaoManual;
	}

	public void setMovimentacaoManual(Boolean movimentacaoManual) {
		this.movimentacaoManual = movimentacaoManual;
	}

	public Double getValorJurosEfetivado() {
		return valorJurosEfetivado;
	}

	public void setValorJurosEfetivado(Double valorJurosEfetivado) {
		this.valorJurosEfetivado = valorJurosEfetivado;
	}

	public Double getValorMultaEfetivada() {
		return valorMultaEfetivada;
	}

	public void setValorMultaEfetivada(Double valorMultaEfetivada) {
		this.valorMultaEfetivada = valorMultaEfetivada;
	}

	public Double getValorCreditoEfetivado() {
		return valorCreditoEfetivado;
	}

	public void setValorCreditoEfetivado(Double valorCreditoEfetivado) {
		this.valorCreditoEfetivado = valorCreditoEfetivado;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public LocalDate getDataEfetivacaoCredito() {
		return dataEfetivacaoCredito;
	}

	public void setDataEfetivacaoCredito(LocalDate dataEfetivacaoCredito) {
		this.dataEfetivacaoCredito = dataEfetivacaoCredito;
	}

	public LocalDate getDataEfetivacaoTarifa() {
		return dataEfetivacaoTarifa;
	}

	public void setDataEfetivacaoTarifa(LocalDate dataEfetivacaoTarifa) {
		this.dataEfetivacaoTarifa = dataEfetivacaoTarifa;
	}

	public String getCanalPagamento() {
		return canalPagamento;
	}

	public void setCanalPagamento(String canalPagamento) {
		this.canalPagamento = canalPagamento;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getFloatPagamento() {
		return floatPagamento;
	}

	public void setFloatPagamento(String floatPagamento) {
		this.floatPagamento = floatPagamento;
	}

	public LocalDate getTituDtDesc2() {
		return tituDtDesc2;
	}

	public void setTituDtDesc2(LocalDate tituDtDesc2) {
		this.tituDtDesc2 = tituDtDesc2;
	}

	public LocalDate getTituDtDesc3() {
		return tituDtDesc3;
	}

	public void setTituDtDesc3(LocalDate tituDtDesc3) {
		this.tituDtDesc3 = tituDtDesc3;
	}

	public Double getTituValDesc2() {
		return tituValDesc2;
	}

	public void setTituValDesc2(Double tituValDesc2) {
		this.tituValDesc2 = tituValDesc2;
	}

	public Double getTituValDesc3() {
		return tituValDesc3;
	}

	public void setTituValDesc3(Double tituValDesc3) {
		this.tituValDesc3 = tituValDesc3;
	}

	public Boolean getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Boolean movimentacao) {
		this.movimentacao = movimentacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getCodigoAceito() {
		return codigoAceito;
	}

	public void setCodigoAceito(String codigoAceito) {
		this.codigoAceito = codigoAceito;
	}

	public String getCodigoRejeitado() {
		return codigoRejeitado;
	}

	public void setCodigoRejeitado(String codigoRejeitado) {
		this.codigoRejeitado = codigoRejeitado;
	}
	
}
