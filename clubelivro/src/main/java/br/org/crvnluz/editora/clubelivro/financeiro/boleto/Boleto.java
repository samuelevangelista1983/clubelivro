package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.validation.ValidationException;

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
	
	public static void validar(Boleto boleto) throws ValidationException {
		LocalDate pgto = boleto.pgto;
		
		if (pgto != null && pgto.isBefore(boleto.getEmissao())) {
			throw new ValidationException("A data de pagamento do boleto não pode ser anterior à data de emissão do mesmo");
		}
		
		BigDecimal zero = new BigDecimal(0);
		BigDecimal valorPago = boleto.valorPago;
		
		if (valorPago != null && valorPago.compareTo(zero) == -1) {
			throw new ValidationException("O valor pago do boleto não pode ser um valor negativo");
		}
		
		BigDecimal valorTarifa = boleto.valorTarifa;
		
		if (valorTarifa != null && valorTarifa.compareTo(zero) == -1) {
			throw new ValidationException("O valor da tarifa do boleto não pode ser um valor negativo");
		}
		
		BigDecimal valorCreditado = boleto.valorCreditado;
		
		if (valorCreditado != null && valorCreditado.compareTo(zero) == -1) {
			throw new ValidationException("O valor creditado do boleto não pode ser um valor negativo");
		}
		
		if (valorPago != null && valorTarifa != null && valorCreditado != null) {
			if (valorCreditado.compareTo(valorPago.subtract(valorTarifa)) != 0) {
				throw new ValidationException("O valor creditado do boleto deve ser igual ao valor do pagamento menos o valor da tarifa do mesmo");
			}
			
		} else if (valorPago != null && valorTarifa == null && valorCreditado != null) {
			throw new ValidationException("O valor da tarifa do boleto deve ser informado");
			
		} else if (valorPago != null && valorTarifa != null && valorCreditado == null) {
			throw new ValidationException("O valor creditado do boleto deve ser informado");
			
		} else if (valorPago == null && valorTarifa != null && valorCreditado != null) {
			throw new ValidationException("O valor pago do boleto deve ser informado");
			
		} else if (valorPago == null && valorTarifa == null && valorCreditado != null) {
			throw new ValidationException("O valor pago e o valor da tarifa do boleto devem ser informados");
			
		} else if (valorPago == null && valorTarifa != null && valorCreditado == null) {
			throw new ValidationException("O valor pago e o valor creditado do boleto devem ser informados");
		}
		
		LocalDate efetivacaoCredito = boleto.efetivacaoCredito;
		
		if (efetivacaoCredito != null && efetivacaoCredito.isBefore(pgto)) {
			throw new ValidationException("A data de efetivação do crédito do boleto não pode ser anterior à data de pagamento do mesmo");
		}
	}
	
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
