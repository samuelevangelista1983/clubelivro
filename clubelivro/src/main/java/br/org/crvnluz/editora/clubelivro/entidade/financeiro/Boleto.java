package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;

@Entity
public class Boleto implements Serializable {
	
	private static final long serialVersionUID = 315952327846693659L;
	
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
	private BigDecimal valor;
	private BigDecimal valorPago;
	
	// MÉTODOS PRIVADOS
	
	private BigDecimal converterStringEmBigDecimal(String valor) {
		StringBuilder str = new StringBuilder(valor);
		str.insert(str.length() - 2, '.');
		return new BigDecimal(str.toString());
	}
	
	@Override
	public String toString() {
		return String.format(
				"Boleto [id=%s, numeroBanco=%s, nossoNumero=%s, emissao=%s, vcto=%s, pgto=%s, valor=%s, valorPago=%s, integrante=%s]",
				id, numeroBanco, nossoNumero, emissao, vcto, pgto, valor, valorPago, integrante);
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
	
	public LocalDate getPgto() {
		return pgto;
	}

	public void setPgto(LocalDate pgto) {
		this.pgto = pgto;
	}

	public void setPgto(String pgtoStr) {
		pgto = LocalDate.parse(pgtoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public BigDecimal getValor() {
		return valor;
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
	
	
}
