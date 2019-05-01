package br.eti.sen.cobcaixa.etl.entidade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	private String nomeSacado;
	private String emailSacado;
	private String telefoneSacado;
	private String celularSacado;
	private String logradouroSacado;
	private String numeroSacado;
	private String complementoSacado;
	private String bairroSacado;
	private String cidadeSacado;
	private String ufSacado;
	private String cepSacado;
	private Long categoriaSacado;
	private BigDecimal valorPago;
	private BigDecimal valorTarifa;
	private BigDecimal valorCreditado;
	private Date efetivacaoCredito;
	private int status; // 0 sacado encontrado, 1 sacado não encontrado
	private String codigoRetorno; // 06 liquidado, 09 baixa
	
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
		String update = null;
		
		if (boleto.codigoRetorno.equals("06")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuilder sql = new StringBuilder("update clube_livro_boleto set pgto = '");
			sql.append(sdf.format(boleto.pgto)).append("', valor_pago = '");
			sql.append(boleto.valorPago).append("', valor_tarifa = '");
			sql.append(boleto.valorTarifa).append("', valor_creditado = '");
			sql.append(boleto.valorCreditado).append("', efet_credito = '");
			sql.append(sdf.format(boleto.efetivacaoCredito));
			sql.append("', situacao = 1 where id = ").append(boleto.id);
			update = sql.toString();
			
		} else if (boleto.codigoRetorno.equals("09")) {
			update = new StringBuilder("update clube_livro_boleto set situacao = 3 where id = ").append(boleto.id).toString();
		}
		
		return update;
	}
	
	public static String getComandoInserirBoleto(Boleto boleto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql = new StringBuilder("insert into clube_livro_boleto (id_sacado, numero_banco, numero_beneficiario, emissao, vcto, valor_nominal) values (");
		sql.append(boleto.idSacado).append(", '").append(boleto.numeroBanco).append("', '").append(boleto.nossoNumero).append("', '");
		sql.append(sdf.format(boleto.emissao)).append("', '").append(sdf.format(boleto.vcto)).append("', '");
		sql.append(boleto.valorNominal).append("')");
		return sql.toString();
	}
	
	public static String getComandoInserirDocumento(Boleto boleto, Long idPessoa) {
		StringBuilder sql = new StringBuilder("insert into documento (id_pessoa, id_tipo, valor) values (");
		sql.append(idPessoa).append(", 1, '").append(boleto.getCpfSacado()).append("')");
		return sql.toString();
	}

	public static String getComandoInserirEmail(Boleto boleto, Long idPessoa) {
		StringBuilder sql = new StringBuilder("insert into contato (id_pessoa, id_tipo, valor) values (");
		sql.append(idPessoa).append(", 4, '").append(boleto.emailSacado).append("')");
		return sql.toString();
	}

	public static String getComandoInserirEndereco(Boleto boleto, Long idPessoa) {
		StringBuilder sql = new StringBuilder("insert into endereco (id_pessoa, id_tipo, cep, logradouro, numero, complemento, bairro, municipio, uf) values (");
		sql.append(idPessoa).append(", 1, '").append(boleto.getCepSacado()).append("', '").append(boleto.getLogradouroSacado()).append("', '");
		sql.append(boleto.getNumeroSacado()).append("', ");
		
		if (boleto.getComplementoSacado() != null) {
			sql.append("'");
		}
		
		sql.append(boleto.getComplementoSacado());
		
		if (boleto.getComplementoSacado() != null) {
			sql.append("'");
		}
		
		sql.append(", '").append(boleto.getBairroSacado()).append("', '");
		sql.append(boleto.getCidadeSacado()).append("', '").append(boleto.getUfSacado()).append("')");
		return sql.toString();
	}
	
	@SuppressWarnings("deprecation")
	public static String getComandoInserirIntegrante(Boleto boleto, Long idPessoa, Long idDocumento, Long idEndereco) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql = new StringBuilder("insert into clube_livro_integrante (id_entrega, id_frequencia, id_forma_pgto_pref, ativo, ");
		sql.append("dt_cadastro, id_pessoa, id_documento, id_endereco_cobranca, id_endereco_entrega, id_classificacao, dia_pgto_pref) values (1, 1, 1, 1, '");
		sql.append(sdf.format(new Date())).append("', ").append(idPessoa).append(", ").append(idDocumento).append(", ").append(idEndereco);
		sql.append(", ").append(idEndereco).append(", ").append(boleto.getCategoriaSacado()).append(", ").append(boleto.getVcto().getDate()).append(")");
		return sql.toString();
	}
	
	public static String getComandoInserirPessoa(Boleto boleto) {
		return new StringBuilder("insert into pessoa (nome) values ('").append(boleto.getNomeSacado()).append("')").toString();
	}

	public static String getComandoInserirTelefoneFixo(Boleto boleto, Long idPessoa) {
		StringBuilder sql = new StringBuilder("insert into contato (id_pessoa, id_tipo, valor) values (");
		sql.append(idPessoa).append(", 1, '").append(boleto.telefoneSacado).append("')");
		return sql.toString();
	}
	
	public static String getComandoInserirTelefoneCelular(Boleto boleto, Long idPessoa) {
		StringBuilder sql = new StringBuilder("insert into contato (id_pessoa, id_tipo, valor) values (");
		sql.append(idPessoa).append(", 3, '").append(boleto.celularSacado).append("')");
		return sql.toString();
	}
	
	public static String getConsultaIdDocumento(Long idPessoa) {
		return new StringBuilder("select id from documento where id_pessoa = ").append(idPessoa).toString();
	}
	
	public static String getConsultaIdEndereco(Long idPessoa) {
		return new StringBuilder("select id from endereco where id_pessoa = ").append(idPessoa).toString();
	}
	
	public static String getConsultaIdPessoa(Boleto boleto) {
		return new StringBuilder("select id from pessoa where nome = '").append(boleto.getNomeSacado()).append("'").toString();
	}
	
	public static String getConsultaIdSacado(Boleto boleto) {
		StringBuilder sql = new StringBuilder("select i.id from clube_livro_integrante i inner join documento d on d.id = i.id_documento where d.valor = '");
		sql.append(boleto.cpfSacado).append("'");
		return sql.toString();
	}
	
	public static String getConsultaNumeroBanco(Boleto boleto) {
		StringBuilder sql = new StringBuilder("select id from clube_livro_boleto where numero_beneficiario = '");
		sql.append(boleto.nossoNumero).append("'");
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
	
	public static boolean verificarAdicaoBoleto(Boleto boleto) {
		Calendar marco = Calendar.getInstance();
		marco.set(2018, 2, 1, 0, 0, 0); // 01/03/2018 00:00:00
		boolean emitidoAposFevereiro = marco.getTime().before(boleto.getEmissao());
		boolean retorno = emitidoAposFevereiro;
		
		if (!emitidoAposFevereiro) {
			//Calendar fevereiro = Calendar.getInstance();
			//fevereiro.set(2018, 1, 1, 0, 0, 0); // 01/02/2018 00:00:00
			//boolean emitidoEmFevereiro = fevereiro.getTime().before(boleto.getEmissao());
			boolean vctoAposFevereiro = marco.getTime().before(boleto.getVcto());
			retorno = /*emitidoEmFevereiro &&*/ vctoAposFevereiro;
		}
		
		return retorno;
	}
	
	public static void main(String[] a) throws ParseException {
		Boleto boleto = new Boleto();
		boleto.setEmissao("27022018");
		boleto.setVcto("01032018");
		System.out.println(Boleto.verificarAdicaoBoleto(boleto));
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

	public String getNomeSacado() {
		return nomeSacado;
	}

	public void setNomeSacado(String nomeSacado) {
		this.nomeSacado = nomeSacado;
	}

	public String getEmailSacado() {
		return emailSacado;
	}

	public void setEmailSacado(String emailSacado) {
		this.emailSacado = emailSacado;
	}

	public String getCidadeSacado() {
		return cidadeSacado;
	}

	public void setCidadeSacado(String cidadeSacado) {
		this.cidadeSacado = cidadeSacado;
	}

	public String getUfSacado() {
		return ufSacado;
	}

	public void setUfSacado(String ufSacado) {
		this.ufSacado = ufSacado;
	}

	public String getCepSacado() {
		return cepSacado;
	}

	public void setCepSacado(String cepSacado) {
		this.cepSacado = cepSacado;
	}

	public String getTelefoneSacado() {
		return telefoneSacado;
	}

	public void setTelefoneSacado(String telefoneSacado) {
		this.telefoneSacado = telefoneSacado;
	}

	public Long getCategoriaSacado() {
		return categoriaSacado;
	}

	public void setCategoriaSacado(Long categoriaSacado) {
		this.categoriaSacado = categoriaSacado;
	}

	public String getCelularSacado() {
		return celularSacado;
	}

	public void setCelularSacado(String celularSacado) {
		this.celularSacado = celularSacado;
	}

	public String getLogradouroSacado() {
		return logradouroSacado;
	}

	public void setLogradouroSacado(String logradouroSacado) {
		this.logradouroSacado = logradouroSacado;
	}

	public String getNumeroSacado() {
		return numeroSacado;
	}

	public void setNumeroSacado(String numeroSacado) {
		this.numeroSacado = numeroSacado;
	}

	public String getComplementoSacado() {
		return complementoSacado;
	}

	public void setComplementoSacado(String complementoSacado) {
		this.complementoSacado = complementoSacado;
	}

	public String getBairroSacado() {
		return bairroSacado;
	}

	public void setBairroSacado(String bairroSacado) {
		this.bairroSacado = bairroSacado;
	}

	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
	
}
