package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;

import javax.validation.ValidationException;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;
import br.org.crvnluz.editora.clubelivro.integrante.Integrante;

public class Boleto extends Persistente {
	
	private static final long serialVersionUID = 4448917680820698968L;
	
	private final NumberFormat format;
	
	private Integrante sacado;
	private String numeroBanco;
	private String numeroBeneficiario;
	private LocalDate emissao;
	private LocalDate vcto;
	private BigDecimal valorNomimal;
	private LocalDate pgto;
	private LocalDate efetivacaoCredito;
	private String efetivacaoCreditoStr;
	private String pgtoStr;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Integer situacao; // 0 - emitido, 1 - baixado, 2 - baixado manualmente
	
	// CONSTRUTORES PÚBLICOS
	
	public Boleto() {
		format = NumberFormat.getCurrencyInstance();
		situacao = 0;
	}
	
	// MÉTODOS PÚBLICOS
	
	public static void validar(Boleto boleto) throws ValidacaoException {
		if (StringUtil.stringNaoNulaENaoVazia(boleto.pgtoStr)) {
			LocalDate pgto = boleto.pgto;
			
			if (pgto == null) {
				throw new ValidacaoException("A data de pagamento do boleto não é válida");
			}
			
			String data = DataUtil.formatarData(pgto);
			
			if (!boleto.pgtoStr.equals(data)) {
				throw new ValidacaoException("A data de pagamento do boleto não é válida");
			}
			
			if (pgto.isBefore(boleto.getEmissao())) {
				throw new ValidacaoException("A data de pagamento do boleto não pode ser anterior à data de emissão do mesmo");
			}
			
		} else {
			throw new ValidacaoException("A data de pagamento do boleto deve ser informada");
		}
		
		BigDecimal valorPago = boleto.valorPago;
		
		if (valorPago == null) {
			throw new ValidacaoException("O valor pago do boleto deve ser informado");
		}
		
		BigDecimal zero = new BigDecimal(0);
		
		if (valorPago != null && valorPago.compareTo(zero) == -1) {
			throw new ValidacaoException("O valor pago do boleto não pode ser um valor negativo");
		}
		
		BigDecimal valorTarifa = boleto.valorTarifa;
		
		if (valorTarifa != null && valorTarifa.compareTo(zero) == -1) {
			throw new ValidacaoException("O valor da tarifa do boleto não pode ser um valor negativo");
		}
		
		BigDecimal valorCreditado = boleto.valorCreditado;
		
		if (valorCreditado != null && valorCreditado.compareTo(zero) == -1) {
			throw new ValidationException("O valor creditado do boleto não pode ser um valor negativo");
		}
		
		if (valorTarifa != null && valorCreditado != null) {
			if (valorCreditado.compareTo(valorPago.subtract(valorTarifa)) != 0) {
				throw new ValidacaoException("O valor creditado do boleto deve ser igual ao valor do pagamento menos o valor da tarifa do mesmo");
			}
			
		} else if (valorTarifa == null && valorCreditado != null) {
			throw new ValidacaoException("O valor da tarifa do boleto deve ser informado");
			
		} else if (valorTarifa != null && valorCreditado == null) {
			throw new ValidacaoException("O valor creditado do boleto deve ser informado");
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(boleto.efetivacaoCreditoStr)) {
			LocalDate efetivacaoCredito = boleto.efetivacaoCredito;
			
			if (efetivacaoCredito == null) {
				throw new ValidacaoException("A data de efetivação do crédito do boleto não é válida");
			}
			
			String data = DataUtil.formatarData(efetivacaoCredito);
			
			if (!boleto.efetivacaoCreditoStr.equals(data)) {
				throw new ValidacaoException("A data de efetivação do crédito do boleto não é válida");
			}
			
			if (efetivacaoCredito.isBefore(boleto.pgto)) {
				throw new ValidacaoException("A data de efetivação do crédito do boleto não pode ser anterior à data de pagamento do mesmo");
			}
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
	
	public LocalDate getEmissao() {
		return emissao;
	}

	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}
	
	public void setEmissao(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			emissao = DataUtil.parserData(data);
		}
	}
	
	public LocalDate getVcto() {
		return vcto;
	}

	public void setVcto(LocalDate vcto) {
		this.vcto = vcto;
	}
	
	public void setVcto(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			vcto = DataUtil.parserData(data);
		}
	}
	
	public String getValorNominalStr() {
		String valor = null;
		
		if (valorNomimal != null) {
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
	
	public LocalDate getPgto() {
		return pgto;
	}

	public void setPgto(LocalDate pgto) {
		this.pgto = pgto;
	}
	
	public void setPgto(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			pgtoStr = data;
			
			try {
				pgto = DataUtil.parserData(data);
				
			} catch (Exception e) {
				// TODO: registrar em log a falha em converter a String em LocalDate
			}
		}
	}
	
	public LocalDate getEfetivacaoCredito() {
		return efetivacaoCredito;
	}

	public void setEfetivacaoCredito(LocalDate efetivacaoCredito) {
		this.efetivacaoCredito = efetivacaoCredito;
	}
	
	public void setEfetivacaoCredito(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			efetivacaoCreditoStr = data;
			
			try {
				efetivacaoCredito = DataUtil.parserData(data);
				
			} catch (Exception e) {
				// TODO: registrar em log a falha em converter a String em LocalDate
			}
		}
	}
	
	public String getValorPagoStr() {
		String valor = null;
		
		if (valorPago != null) {
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
	
	public void setValorPagoStr(String valor) throws ParseException {
		if (StringUtil.stringNaoNulaENaoVazia(valor)) {
			valorPago = new BigDecimal(format.parse(valor).doubleValue());
		}
	}
	
	public String getValorTarifaStr() {
		String valor = null;
		
		if (valorTarifa != null) {
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
	
	public void setValorTarifaStr(String valor) throws ParseException {
		if (StringUtil.stringNaoNulaENaoVazia(valor)) {
			valorTarifa = new BigDecimal(format.parse(valor).doubleValue());
		}
	}
	
	public String getValorCreditadoStr() {
		String valor = null;
		
		if (valorCreditado != null) {
			valor = format.format(valorCreditado.doubleValue());
		}
		
		return valor;
	}
	
	public BigDecimal getValorCreditado() {
		return valorCreditado;
	}

	public void setValorCreditadoStr(String valor) throws ParseException {
		if (StringUtil.stringNaoNulaENaoVazia(valor)) {
			valorCreditado = new BigDecimal(format.parse(valor).doubleValue());
		}
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
