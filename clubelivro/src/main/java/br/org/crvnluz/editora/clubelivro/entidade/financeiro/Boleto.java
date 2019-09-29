package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;

@Entity
public class Boleto implements Serializable {
	
	private static final long serialVersionUID = -1305485111932045140L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_integrante", nullable = false)
	private Integrante integrante;
	private String numeroBanco;
	private String nossoNumero;
	private LocalDate emissao;
	private LocalDate vcto;
	private LocalDate pgto;
	private LocalDate efetCredito;
	private BigDecimal valor;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Integer situacao; // 0 - emitido, 1 - baixado, 2 - baixado manualmente, 3 - cancelado, 4 - cancelado manualmente, 5 - desconhecido, 6 - erro processamento
	
	// MÉTODOS PRIVADOS
	
	private BigDecimal converterStringEmBigDecimal(String valor) {
		StringBuilder str = new StringBuilder(valor);
		str.insert(str.length() - 2, '.');
		return new BigDecimal(str.toString());
	}
	
	@Override
	public String toString() {
		return String.format(
				"Boleto [id=%s, integrante=%s, numeroBanco=%s, nossoNumero=%s, emissao=%s, vcto=%s, pgto=%s, efetCredito=%s, valor=%s, valorPago=%s, valorTarifa=%s, valorCreditado=%s, situacao=%s]",
				id, integrante, numeroBanco, nossoNumero, emissao, vcto, pgto, efetCredito, valor, valorPago,
				valorTarifa, valorCreditado, situacao);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(String numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public LocalDate getEmissao() {
		return emissao;
	}
	
	public String getEmissaoStr() {
		String emissaoStr = null;
		
		if (emissao != null) {
			emissaoStr = emissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
		}
		
		return emissaoStr;
	}
	
	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}

	public void setEmissao(String emissaoStr) {
		emissao = LocalDate.parse(emissaoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pt", "BR")));
	}
	
	public LocalDate getVcto() {
		return vcto;
	}

	public String getVctoStr() {
		String vctoStr = null;
		
		if (vcto != null) {
			vctoStr = vcto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
		}
		
		return vctoStr;
	}
	
	public void setVcto(LocalDate vcto) {
		this.vcto = vcto;
	}

	public void setVcto(String vctoStr) {
		vcto = LocalDate.parse(vctoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pt", "BR")));
	}
	
	public LocalDate getPgto() {
		return pgto;
	}
	
	public String getPgtoStr() {
		String pgtoStr = null;
		
		if (pgto != null) {
			pgtoStr = pgto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
		}
		
		return pgtoStr;
	}
	
	public void setPgto(LocalDate pgto) {
		this.pgto = pgto;
	}

	public void setPgto(String pgtoStr) {
		pgto = LocalDate.parse(pgtoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pt", "BR")));
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public String getValorStr() {
		String valorStr = null;
		
		if (valor != null) {
			valorStr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor.doubleValue());
		}
		
		return valorStr;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setValor(String valorStr) {
		valor = converterStringEmBigDecimal(valorStr);
	}
	
	public BigDecimal getValorPago() {
		return valorPago;
	}
	
	public String getValorPagoStr() {
		String valorStr = null;
		
		if (valorPago != null) {
			valorStr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorPago.doubleValue());
		}
		
		return valorStr;
	}
	
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public void setValorPago(String valorPagoStr) {
		valorPago = converterStringEmBigDecimal(valorPagoStr);
	}
	
	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public LocalDate getEfetCredito() {
		return efetCredito;
	}
	
	public String getEfetCreditoStr() {
		String eftCreditoStr = null;
		
		if (efetCredito != null) {
			eftCreditoStr = efetCredito.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
		}
		
		return eftCreditoStr;
	}
	
	public void setEfetCredito(LocalDate efetCredito) {
		this.efetCredito = efetCredito;
	}

	public void setEfetCredito(String efetivacaoCreditoStr) {
		try {
			efetCredito = LocalDate.parse(efetivacaoCreditoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pt", "BR")));
		} catch (DateTimeParseException ex) {
			
		}
	}
	
	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}

	public String getValorTarifaStr() {
		String valorStr = null;
		
		if (valorTarifa != null) {
			valorStr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorTarifa.doubleValue());
		}
		
		return valorStr;
	}
	
	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public BigDecimal getValorCreditado() {
		return valorCreditado;
	}

	public String getValorCreditadoStr() {
		String valorStr = null;
		
		if (valorCreditado != null) {
			valorStr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorCreditado.doubleValue());
		}
		
		return valorStr;
	}
	
	public void setValorCreditado(BigDecimal valorCreditado) {
		this.valorCreditado = valorCreditado;
	}
	
	
}
