package crvnluz.cobcaixa.entidade.hsql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.eti.sen.utilitarios.texto.StringUtil;
import crvnluz.cobcaixa.entidade.access.BoletoCobCaixa;

@Entity
public class Boleto implements Serializable {
	
	private static final long serialVersionUID = -1089968392763698616L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_sacado", nullable = false)
	private Sacado sacado;
	private String numeroBanco;
	private String numeroBeneficiario;
	private LocalDate emissao;
	private LocalDate vcto;
	private BigDecimal valorNominal;
	private LocalDate pgto;
	private LocalDate efetivacaoCredito;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Integer situacao; // 0 - emitido, 1 - liquidado, 2 - liquidado manualmente, 3 - cancelado, 4 - cancelado manualmente, 5 - desconhecido, 6 - erro processamento
	private String mensagemReciboSacado;
	private String mensagemFichaCompensacao;
	
	public Boleto() {}
	
	// MÉTODOS PRIVADOS
	
	private BigDecimal converterStringEmBigDecimal(String valor) {
		StringBuilder str = new StringBuilder(valor);
		str.insert(str.length() - 2, '.');
		return new BigDecimal(str.toString());
	}
	
	// MÉTODOS PÚBLICOS
	
	public static Boleto converter(BoletoCobCaixa boletoCobCaixa) {
		Boleto boleto = new Boleto();
		boleto.setSacado(Sacado.converter(boletoCobCaixa.getSacado()));
		boleto.setEfetivacaoCredito(boletoCobCaixa.getDataEfetivacaoCredito());
		boleto.setEmissao(boletoCobCaixa.getDataDocumento());
		boleto.setNumeroBanco(boletoCobCaixa.getNossoNumero());
		boleto.setNumeroBeneficiario(boletoCobCaixa.getId().getNumeroDocumento());
		boleto.setPgto(boletoCobCaixa.getDataPagamento());
		boleto.setValorCreditado(BigDecimal.valueOf(boletoCobCaixa.getValorCreditoEfetivado()));
		boleto.setValorNominal(BigDecimal.valueOf(boletoCobCaixa.getValorDocumento()));
		boleto.setValorPago(BigDecimal.valueOf(boletoCobCaixa.getValorPago()));
		boleto.setValorTarifa(BigDecimal.valueOf(boletoCobCaixa.getValorTarifa()));
		boleto.setVcto(boletoCobCaixa.getDataVencimento());
		String codigoRetorno = boletoCobCaixa.getCodigoRetorno();
		
		if ("01".equals(codigoRetorno) || "02".equals(codigoRetorno)) {
			boleto.setSituacao(0);
			
		} else if ("06".equals(codigoRetorno)) {
			boleto.setSituacao(1);
			
			if (boleto.pgto == null) {
				boleto.pgto = boletoCobCaixa.getDataOcorrencia();
			}
			
		} else if ("09".equals(codigoRetorno)) {
			boleto.setSituacao(3);
			
		} else {
			boleto.setSituacao(5);
		}
		
		String fichaCompensacao1 = boletoCobCaixa.getMensagemFichaCompensacao1().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(fichaCompensacao1)) {
			boleto.setMensagemFichaCompensacao(fichaCompensacao1);
		}
		
		String fichaCompensacao2 = boletoCobCaixa.getMensagemFichaCompensacao2().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(fichaCompensacao2)) {
			String msg = boleto.getMensagemFichaCompensacao();
			boleto.setMensagemFichaCompensacao(StringUtil.stringNaoNulaENaoVazia(msg) ? msg.concat(" ").concat(fichaCompensacao2) : fichaCompensacao2);
		}
		
		String recibo1 = boletoCobCaixa.getMensagemReciboSacado1().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(recibo1)) {
			boleto.setMensagemReciboSacado(recibo1);
		}
		
		String recibo2 = boletoCobCaixa.getMensagemReciboSacado2().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(recibo2)) {
			String msg = boleto.getMensagemReciboSacado();
			boleto.setMensagemReciboSacado(StringUtil.stringNaoNulaENaoVazia(msg) ? msg.concat(" ").concat(recibo2) : recibo2);
		}
		
		String recibo3 = boletoCobCaixa.getMensagemReciboSacado3().trim();

		if (StringUtil.stringNaoNulaENaoVazia(recibo3)) {
			String msg = boleto.getMensagemReciboSacado();
			boleto.setMensagemReciboSacado(StringUtil.stringNaoNulaENaoVazia(msg) ? msg.concat(" ").concat(recibo3) : recibo3);
		}
		
		String recibo4 = boletoCobCaixa.getMensagemReciboSacado4().trim();

		if (StringUtil.stringNaoNulaENaoVazia(recibo4)) {
			String msg = boleto.getMensagemReciboSacado();
			boleto.setMensagemReciboSacado(StringUtil.stringNaoNulaENaoVazia(msg) ? msg.concat(" ").concat(recibo4) : recibo4);
		}
		
		return boleto;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Boleto [id=%s, sacado=%s, numeroBanco=%s, numeroBeneficiario=%s, emissao=%s, vcto=%s, valorNominal=%s, pgto=%s, efetivacaoCredito=%s, valorPago=%s, valorTarifa=%s, valorCreditado=%s, situacao=%s, mensagemReciboSacado=%s, mensagemFichaCompensacao=%s]",
				id, sacado, numeroBanco, numeroBeneficiario, emissao, vcto, valorNominal, pgto, efetivacaoCredito,
				valorPago, valorTarifa, valorCreditado, situacao, mensagemReciboSacado, mensagemFichaCompensacao);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sacado getSacado() {
		return sacado;
	}

	public void setSacado(Sacado sacado) {
		this.sacado = sacado;
	}

	public String getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(String numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public String getNumeroBeneficiario() {
		return numeroBeneficiario;
	}

	public void setNumeroBeneficiario(String numeroBeneficiario) {
		this.numeroBeneficiario = numeroBeneficiario;
	}

	public LocalDate getEmissao() {
		return emissao;
	}

	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}

	public void setEmissao(String emissaoStr) {
		emissao = LocalDate.parse(emissaoStr, DateTimeFormatter.ofPattern("ddMMyyyy", new Locale("pt", "BR")));
	}
	
	public LocalDate getVcto() {
		return vcto;
	}

	public void setVcto(LocalDate vcto) {
		this.vcto = vcto;
	}

	public void setVcto(String vctoStr) {
		vcto = LocalDate.parse(vctoStr, DateTimeFormatter.ofPattern("ddMMyyyy", new Locale("pt", "BR")));
	}
	
	public BigDecimal getValorNominal() {
		return valorNominal;
	}

	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	public void setValorNominal(String valorNominalStr) {
		valorNominal = converterStringEmBigDecimal(valorNominalStr);
	}
	
	public LocalDate getPgto() {
		return pgto;
	}

	public void setPgto(LocalDate pgto) {
		this.pgto = pgto;
	}

	public void setPgto(String pgtoStr) {
		pgto = LocalDate.parse(pgtoStr, DateTimeFormatter.ofPattern("ddMMyyyy", new Locale("pt", "BR")));
	}
	
	public LocalDate getEfetivacaoCredito() {
		return efetivacaoCredito;
	}

	public void setEfetivacaoCredito(LocalDate efetivacaoCredito) {
		this.efetivacaoCredito = efetivacaoCredito;
	}

	public void setEfetivacaoCredito(String efetivacaoCreditoStr) {
		try {
			efetivacaoCredito = LocalDate.parse(efetivacaoCreditoStr, DateTimeFormatter.ofPattern("ddMMyyyy", new Locale("pt", "BR")));
		} catch (DateTimeParseException ex) {
			
		}
	}
	
	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public void setValorPago(String valorPagoStr) {
		valorPago = converterStringEmBigDecimal(valorPagoStr);
	}
	
	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public void setValorTarifa(String valorTarifaStr) {
		valorTarifa = converterStringEmBigDecimal(valorTarifaStr);
	}
	
	public BigDecimal getValorCreditado() {
		return valorCreditado;
	}

	public void setValorCreditado(BigDecimal valorCreditado) {
		this.valorCreditado = valorCreditado;
	}

	public void setValorCreditado(String valorCreditadoStr) {
		valorCreditado = converterStringEmBigDecimal(valorCreditadoStr);
	}
	
	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getMensagemReciboSacado() {
		return mensagemReciboSacado;
	}

	public void setMensagemReciboSacado(String mensagemReciboSacado) {
		this.mensagemReciboSacado = mensagemReciboSacado;
	}

	public String getMensagemFichaCompensacao() {
		return mensagemFichaCompensacao;
	}

	public void setMensagemFichaCompensacao(String mensagemFichaCompensacao) {
		this.mensagemFichaCompensacao = mensagemFichaCompensacao;
	}

}
