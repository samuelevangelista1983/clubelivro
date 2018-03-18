package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;
import br.org.crvnluz.editora.clubelivro.integrante.Integrante;

public class Boleto extends Persistente {
	
	private static final long serialVersionUID = -2848569869141842785L;
	
	private Integrante sacado;
	private String numeroBanco;
	private String numeroBeneficiario;
	private LocalDate emissao;
	private LocalDate vcto;
	private BigDecimal valorNomimal;
	private LocalDate pgto;
	private LocalDate efetivacaoCredito;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Integer situacao; // 0 - emitido, 1 - baixado, 2 - baixado manualmente
	
	// CONSTRUTORES PÚBLICOS
	
	public Boleto() {
		situacao = 0;
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Boleto [sacado=" + sacado + ", numeroBanco=" + numeroBanco + ", numeroBeneficiario="
				+ numeroBeneficiario + ", emissao=" + emissao + ", vcto=" + vcto + ", valorNomimal=" + valorNomimal
				+ ", pgto=" + pgto + ", efetivacaoCredito=" + efetivacaoCredito + ", valorPago=" + valorPago
				+ ", valorTarifa=" + valorTarifa + ", valorCreditado=" + valorCreditado + ", situacao=" + situacao
				+ "]";
	}
	
	public Integrante getSacado() {
		return sacado;
	}

	public void setSacado(Integrante sacado) {
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
/*
	public String getEmissaoStr() {
		String valor = null;
		
		if (emissao != null) {
			valor = DataUtil.formatarData(emissao);
		}
		
		return valor;
	}
	*/
	public LocalDate getEmissao() {
		return emissao;
	}

	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}
/*
	public String getVctoStr() {
		String valor = null;
		
		if (vcto != null) {
			valor = DataUtil.formatarData(vcto);
		}
		
		return valor;
	}
*/	
	public LocalDate getVcto() {
		return vcto;
	}

	public void setVcto(LocalDate vcto) {
		this.vcto = vcto;
	}

	public String getValorNominalStr() {
		String valor = null;
		
		if (valorNomimal != null) {
			NumberFormat format = NumberFormat.getCurrencyInstance();
			valor = format.format(valorNomimal.doubleValue());
		}
		
		return valor;
	}
	
	public BigDecimal getValorNomimal() {
		return valorNomimal;
	}

	public void setValorNomimal(BigDecimal valorNomimal) {
		this.valorNomimal = valorNomimal;
	}
/*
	public String getPgtoStr() {
		String valor = null;
		
		if (pgto != null) {
			valor = DataUtil.formatarData(pgto);
		}
		
		return valor;
	}
	*/
	public LocalDate getPgto() {
		return pgto;
	}

	public void setPgto(LocalDate pgto) {
		this.pgto = pgto;
	}
/*
	public String getEfetivacaoCreditoStr() {
		String valor = null;
		
		if (efetivacaoCredito != null) {
			valor = DataUtil.formatarData(efetivacaoCredito);
		}
		
		return valor;
	}
	*/
	public LocalDate getEfetivacaoCredito() {
		return efetivacaoCredito;
	}

	public void setEfetivacaoCredito(LocalDate efetivacaoCredito) {
		this.efetivacaoCredito = efetivacaoCredito;
	}

	public String getValorPagoStr() {
		String valor = null;
		
		if (valorPago != null) {
			NumberFormat format = NumberFormat.getCurrencyInstance();
			valor = format.format(valorPago.doubleValue());
		} 
		
		return valor;
	}
	
	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public String getValorTarifaStr() {
		String valor = null;
		
		if (valorTarifa != null) {
			NumberFormat format = NumberFormat.getCurrencyInstance();
			valor = format.format(valorTarifa.doubleValue());
		}
		
		return valor;
	}
	
	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public String getValorCreditadoStr() {
		String valor = null;
		
		if (valorCreditado != null) {
			NumberFormat format = NumberFormat.getCurrencyInstance();
			valor = format.format(valorCreditado.doubleValue());
		}
		
		return valor;
	}
	
	public BigDecimal getValorCreditado() {
		return valorCreditado;
	}

	public void setValorCreditado(BigDecimal valorCreditado) {
		this.valorCreditado = valorCreditado;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

}
