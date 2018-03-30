package br.org.crvnluz.editora.clubelivro.financeiro.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.org.crvnluz.editora.clubelivro.configuracao.formapgto.FormaPgto;
import br.org.crvnluz.editora.clubelivro.financeiro.boleto.Boleto;
import br.org.crvnluz.editora.clubelivro.financeiro.mensalidade.Mensalidade;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Pagamento extends Persistente {
	
	private static final long serialVersionUID = 8888603465785031583L;
	
	private Mensalidade mensalidade;
	private LocalDate data;
	private BigDecimal valor;
	private FormaPgto formaPgto;
	private Boleto boleto;
	
	// CONSTRUTORES PÚBLICOS
	
	public Pagamento() {}

	public Pagamento(Long id, Mensalidade mensalidade, LocalDate data, BigDecimal valor, FormaPgto formaPgto, Boleto boleto) {
		super(id);
		this.mensalidade = mensalidade;
		this.data = data;
		this.valor = valor;
		this.formaPgto = formaPgto;
		this.boleto = boleto;
	}
	
	// MÉTODOS PÚBLICOS
	/*
	@Override
	public Object clone() throws CloneNotSupportedException {
		Mensalidade mensalidadeClonada = mensalidade != null ? (Mensalidade) mensalidade.clone() : null;
		FormaPgto formaPgtoClonada = formaPgto != null ? (FormaPgto) formaPgto.clone() : null;
		Boleto boletoClonado = boleto != null ? (Boleto) boleto.clone() : null;
		return new Pagamento(id, mensalidadeClonada, data, valor, formaPgtoClonada, boletoClonado);
	}
	*/
	@Override
	public String toString() {
		return "Pagamento [mensalidade=" + mensalidade + ", data=" + data + ", valor=" + valor + ", formaPgto="
				+ formaPgto + ", boleto=" + boleto + ", id=" + id + "]";
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public FormaPgto getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

}
