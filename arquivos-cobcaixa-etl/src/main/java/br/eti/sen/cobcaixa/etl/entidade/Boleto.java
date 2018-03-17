package br.eti.sen.cobcaixa.etl.entidade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Boleto {
	
	private Long id;
	private Date emissao;
	private Date vcto;
	private BigDecimal valorNominal;
	private String numeroBanco;
	private String nossoNumero;
	private Date pgto;
	private Long idSacado;
	private String cpfSacado;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Date efetivacaoCredito;
	private int status; // 0 sacado encontrado, 1 sacado não encontrado
	
	// CONSTRUTORES PÚBLICOS
	
	public Boleto() {}
	
	// MÉTODO PRIVADO
	
	private Date convertStringToDate(String pattern, String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(data);
	}
	
	private BigDecimal convertStringToBigDecimal(String valor) {
		StringBuilder str = new StringBuilder(valor);
		str.insert(str.length() - 2, '.');
		return new BigDecimal(str.toString());
	}
	
	// MÉTODOS PÚBLICOS
	
	public static String getComandoAtualizarBoleto(Boleto boleto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql = new StringBuilder("update clube_livro_boleto set pgto = '");
		sql.append(sdf.format(boleto.pgto)).append("', valor_pago = '");
		sql.append(boleto.valorPago).append("', valor_tarifa = '");
		sql.append(boleto.valorTarifa).append("', valor_creditado = '");
		sql.append(boleto.valorCreditado).append("', efet_credito = '");
		sql.append(sdf.format(boleto.efetivacaoCredito));
		sql.append("' where id = ").append(boleto.id);
		return sql.toString();
	}
	
	public static String getComandoInserirBoleto(Boleto boleto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql = new StringBuilder("insert into clube_livro_boleto (id_sacado, numero_banco, numero_beneficiario, emissao, vcto, valor_nominal) values (");
		sql.append(boleto.idSacado).append(", '").append(boleto.numeroBanco).append("', '").append(boleto.nossoNumero).append("', '");
		sql.append(sdf.format(boleto.emissao)).append("', '").append(sdf.format(boleto.vcto)).append("', '");
		sql.append(boleto.valorNominal).append("')");
		return sql.toString();
	}
	
	public static String getConsultaIdSacado(Boleto boleto) {
		StringBuilder sql = new StringBuilder("select i.id from clube_livro_integrante i inner join documento d on d.id = i.id_documento where d.valor = '");
		sql.append(boleto.cpfSacado).append("'");
		return sql.toString();
	}
	
	public static String getConsultaNumeroBanco(Boleto boleto) {
		StringBuilder sql = new StringBuilder("select id from clube_livro_boleto where numero_banco = '");
		sql.append(boleto.numeroBanco).append("'");
		return sql.toString();
	}
	
	@Override
	public String toString() {
		return "Boleto [emissao=" + emissao + ", vcto=" + vcto + ", valorNominal=" + valorNominal + ", numeroBanco="
				+ numeroBanco + ", nossoNumero=" + nossoNumero + ", pgto=" + pgto + ", idSacado=" + idSacado
				+ ", cpfSacado=" + cpfSacado + ", valorPago=" + valorPago + ", valorTarifa=" + valorTarifa
				+ ", valorCreditado=" + valorCreditado + ", efetivacaoCredito=" + efetivacaoCredito + ", status="
				+ status + "]";
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public void setEmissao(String emissao) throws ParseException {
		this.emissao = convertStringToDate("ddMMyyyy", emissao);
	}
	
	public Date getVcto() {
		return vcto;
	}

	public void setVcto(Date vcto) {
		this.vcto = vcto;
	}

	public void setVcto(String vcto) throws ParseException {
		this.vcto = convertStringToDate("ddMMyyyy", vcto);
	}
	
	public BigDecimal getValorNominal() {
		return valorNominal;
	}

	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	public void setValorNominal(String valorNominal) {
		this.valorNominal = convertStringToBigDecimal(valorNominal);
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

	public Date getPgto() {
		return pgto;
	}

	public void setPgto(Date pgto) {
		this.pgto = pgto;
	}

	public void setPgto(String pgto) throws ParseException {
		this.pgto = convertStringToDate("ddMMyyyy", pgto);
	}
	
	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public void setValorPago(String valorPago) {
		this.valorPago = convertStringToBigDecimal(valorPago);
	}
	
	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public void setValorTarifa(String valorTarifa) {
		this.valorTarifa = convertStringToBigDecimal(valorTarifa);
	}
	
	public BigDecimal getValorCreditado() {
		return valorCreditado;
	}

	public void setValorCreditado(BigDecimal valorCreditado) {
		this.valorCreditado = valorCreditado;
	}

	public void setValorCreditado(String valorCreditado) {
		this.valorCreditado = convertStringToBigDecimal(valorCreditado);
	}
	
	public Date getEfetivacaoCredito() {
		return efetivacaoCredito;
	}

	public void setEfetivacaoCredito(Date efetivacaoCredito) {
		this.efetivacaoCredito = efetivacaoCredito;
	}

	public void setEfetivacaoCredito(String efetivacaoCredito) throws ParseException {
		this.efetivacaoCredito = convertStringToDate("ddMMyyyy", efetivacaoCredito);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCpfSacado() {
		return cpfSacado;
	}

	public void setCpfSacado(String cpfSacado) {
		this.cpfSacado = cpfSacado;
	}

	public Long getIdSacado() {
		return idSacado;
	}

	public void setIdSacado(Long idSacado) {
		this.idSacado = idSacado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
