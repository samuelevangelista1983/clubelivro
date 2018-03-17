package br.org.crvnluz.editora.clubelivro.financeiro.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.configuracao.formapgto.FormaPgto;
import br.org.crvnluz.editora.clubelivro.financeiro.boleto.Boleto;
import br.org.crvnluz.editora.clubelivro.financeiro.mensalidade.Mensalidade;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

@Entity
@Table(name = "clube_livro_pgto")
public class Pagamento extends Persistente {
	
	private static final long serialVersionUID = 8888603465785031583L;
	
	@OneToOne
	@JoinColumn(name = "id_mensalidade", nullable = false)
	private Mensalidade mensalidade;
	@Column
	private LocalDate data;
	@Column(nullable = false)
	private BigDecimal valor;
	@OneToOne
	@JoinColumn(name = "id_forma_pgto", nullable = false)
	private FormaPgto formaPgto;
	@OneToOne
	@JoinColumn(name = "id_boleto", nullable = false)
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
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
