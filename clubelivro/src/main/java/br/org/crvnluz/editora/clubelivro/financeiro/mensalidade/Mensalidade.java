package br.org.crvnluz.editora.clubelivro.financeiro.mensalidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;
import br.org.crvnluz.editora.clubelivro.integrante.Integrante;

@Entity
@Table(name = "clube_livro_mensalidade")
public class Mensalidade extends Persistente {
	
	private static final long serialVersionUID = -2279537060141334299L;
	
	@OneToOne
	@JoinColumn(name = "id_integrante", nullable = false)
	private Integrante integrante;
	@Column(nullable = false)
	private Integer ano;
	@Column(nullable = false)
	private Integer mes;
	@Column(nullable = false)
	private BigDecimal valor;
	@Column(name = "num_pgtos", nullable = false)
	private Integer numPgtos;
	@Column(nullable = false)
	private Integer situacao; // 0 - aberta, 1 - quite
	
	// CONSTRUTORES PÚBLICOS
	
	public Mensalidade() {
		situacao = 0;
	}
	
	public Mensalidade(Long id, Integrante integrante, Integer ano, Integer mes, BigDecimal valor, Integer numPgtos, Integer situacao) {
		super(id);
		this.integrante = integrante;
		this.ano = ano;
		this.mes = mes;
		this.valor = valor;
		this.numPgtos = numPgtos;
		this.situacao = situacao;
	}
	
	// MÉTODOS PÚBLICOS
	/*
	@Override
	public Object clone() throws CloneNotSupportedException {
		Integrante integranteClonado = integrante != null ? (Integrante) integrante.clone() : null;
		return new Mensalidade(id, integranteClonado, ano, mes, valor, numPgtos, situacao);
	}
	*/
	@Override
	public String toString() {
		return "Mensalidade [integrante=" + integrante + ", ano=" + ano + ", mes=" + mes + ", valor=" + valor
				+ ", numPgtos=" + numPgtos + ", situacao=" + situacao + ", id=" + id + "]";
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getNumPgtos() {
		return numPgtos;
	}

	public void setNumPgtos(Integer numPgtos) {
		this.numPgtos = numPgtos;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

}
