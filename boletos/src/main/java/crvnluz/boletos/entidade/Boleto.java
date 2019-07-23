package crvnluz.boletos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.util.StringUtils;

@Entity
public class Boleto implements Serializable {
	
	private static final long serialVersionUID = 8659629572703154688L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	private Long versao;
	@ManyToOne
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
	private Integer situacao; // 0 - emitido, 1 - baixado, 2 - baixado manualmente, 3 - cancelado, 4 - cancelado manualmente, 5 - desconhecido, 6 - erro processamento
	@Column(name = "msg_recibo_sacado")
	private String mensagemReciboSacado;
	@Column(name = "msg_ficha_compensacao")
	private String mensagemFichaCompensacao;
	
	public Boleto() {}
	
	// MÉTODOS PRIVADOS
	
	private BigDecimal converterStringEmBigDecimal(String valor) {
		StringBuilder str = new StringBuilder(valor);
		str.insert(str.length() - 2, '.');
		return new BigDecimal(str.toString());
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		
		if (obj != null) {
			if (this != obj) {
				if (obj instanceof Boleto) {
					Boleto other = (Boleto) obj;
					
					if (numeroBanco == null && other.numeroBanco == null) {
						equals = true;
						
					} else {
						equals = numeroBanco.equals(other.numeroBanco);
					}
				}
				
			} else {
				equals = true;
			}
		}
		
		return equals;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroBanco == null) ? 0 : numeroBanco.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Boleto [id=%s, versao=%s, sacado=%s, numeroBanco=%s, numeroBeneficiario=%s, emissao=%s, vcto=%s, valorNominal=%s, pgto=%s, efetivacaoCredito=%s, valorPago=%s, valorTarifa=%s, valorCreditado=%s, situacao=%s, mensagemReciboSacado=%s, mensagemFichaCompensacao=%s]",
				id, versao, sacado, numeroBanco, numeroBeneficiario, emissao, vcto, valorNominal, pgto,
				efetivacaoCredito, valorPago, valorTarifa, valorCreditado, situacao, mensagemReciboSacado,
				mensagemFichaCompensacao);
	}

	public static void validar(Boleto boleto) {
		if (boleto.sacado == null || boleto.sacado.getId() == null) {
			throw new IllegalArgumentException("O sacado deve ser informado");
		}
		
		Sacado.validar(boleto.sacado);
		
		if (boleto.emissao == null) {
			throw new IllegalArgumentException("A data de emissão do boleto não é válida");
		}
		
		if (!StringUtils.hasText(boleto.numeroBeneficiario)) {
			throw new IllegalArgumentException("O número do boleto deve ser informado");
		}
		
		while (boleto.numeroBeneficiario.length() < 11) {
			boleto.numeroBeneficiario = new StringBuilder("0").append(boleto.numeroBeneficiario).toString();
		}
		
		if (!StringUtils.hasText(boleto.numeroBanco)) {
			throw new IllegalArgumentException("O número do banco deve ser informado");
		}
		
		boleto.numeroBanco = boleto.numeroBanco.replaceAll("/", "");
		
		if (boleto.numeroBanco.length() < 17 || boleto.numeroBanco.length() > 18) {
			throw new IllegalArgumentException("O número do banco deve ter 17 ou 18 dígitos");
		}
		
		while (boleto.numeroBanco.length() < 18) {
			boleto.numeroBanco = new StringBuilder("0").append(boleto.numeroBanco).toString();
		}
		
		if (boleto.valorNominal == null) {
			throw new IllegalArgumentException("O valor nominal do boleto deve ser informado");
		}
		
		BigDecimal zero = new BigDecimal(0);
		
		if (boleto.valorNominal.compareTo(zero) == 0) {
			throw new IllegalArgumentException("O valor nominal do boleto não pode ser zero");
		}
		
		if (boleto.valorNominal.compareTo(zero) == -1) {
			throw new IllegalArgumentException("O valor nominal do boleto não pode ser um valor negativo");
		}
		
		if (boleto.vcto == null) {
			throw new IllegalArgumentException("A data de vencimento do boleto não é válida");
		}
		
		if (boleto.vcto.isBefore(boleto.emissao)) {
			throw new IllegalArgumentException("A data de vencimento do boleto não pode ser anterior à data de emissão do mesmo");
		}
		
		if (boleto.situacao == 2) {
			if (boleto.pgto == null) {
				throw new IllegalArgumentException("A data de pagamento do boleto não é válida");
			}
			
			if (boleto.pgto.isBefore(boleto.emissao)) {
				throw new IllegalArgumentException("A data de pagamento do boleto não pode ser anterior à data de emissão do mesmo");
			}
			
			
			if (boleto.valorPago == null) {
				throw new IllegalArgumentException("O valor pago do boleto deve ser informado");
			}
			
			if (boleto.valorPago.compareTo(zero) == -1) {
				throw new IllegalArgumentException("O valor pago do boleto não pode ser um valor negativo");
			}
			
			if (boleto.valorPago.compareTo(boleto.valorNominal) == -1) {
				throw new IllegalArgumentException("O valor pago do boleto não pode ser um valor menor do que o valor nominal");
			}
			
			if (boleto.valorTarifa != null && boleto.valorTarifa.compareTo(zero) == -1) {
				throw new IllegalArgumentException("O valor da tarifa do boleto não pode ser um valor negativo");
			}
			
			if (boleto.valorCreditado != null && boleto.valorCreditado.compareTo(zero) == -1) {
				throw new IllegalArgumentException("O valor creditado do boleto não pode ser um valor negativo");
			}
			
			if (boleto.valorTarifa != null && boleto.valorCreditado != null) {
				if (boleto.valorCreditado.compareTo(boleto.valorPago.subtract(boleto.valorTarifa)) != 0) {
					throw new IllegalArgumentException("O valor creditado do boleto deve ser igual ao valor do pagamento menos o valor da tarifa do mesmo");
				}
				
			} else if (boleto.valorTarifa == null && boleto.valorCreditado != null) {
				throw new IllegalArgumentException("O valor da tarifa do boleto deve ser informado");
				
			} else if (boleto.valorTarifa != null && boleto.valorCreditado == null) {
				throw new IllegalArgumentException("O valor creditado do boleto deve ser informado");
			}
			
			if (boleto.efetivacaoCredito == null) {
				throw new IllegalArgumentException("A data de efetivação do crédito do boleto não é válida");
			}
			
			if (boleto.efetivacaoCredito.isBefore(boleto.pgto)) {
				throw new IllegalArgumentException("A data de efetivação do crédito do boleto não pode ser anterior à data de pagamento do mesmo");
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
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
		emissao = LocalDate.parse(emissaoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public LocalDate getVcto() {
		return vcto;
	}

	public void setVcto(LocalDate vcto) {
		this.vcto = vcto;
	}

	public void setVcto(String vctoStr) {
		vcto = LocalDate.parse(vctoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
		pgto = LocalDate.parse(pgtoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public LocalDate getEfetivacaoCredito() {
		return efetivacaoCredito;
	}

	public void setEfetivacaoCredito(LocalDate efetivacaoCredito) {
		this.efetivacaoCredito = efetivacaoCredito;
	}

	public void setEfetivacaoCredito(String efetivacaoCreditoStr) {
		try {
			efetivacaoCredito = LocalDate.parse(efetivacaoCreditoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
