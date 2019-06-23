package crvnluz.cobcaixa.entidade.access;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tabSacado")
public class SacadoCobCaixa implements Serializable {

	private static final long serialVersionUID = -29580384361420405L;
	
	@EmbeddedId
	private SacadoCobCaixaPK id;
	@Column(name = "ssacaCodGrupo")
	private String codigoGrupoSacado;
	@Column(name = "bsacaTpPessoa")
	private Boolean tipoPessoa;
	@Column(name = "ssacaCPFCNPJ")
	private String cpfCnpj;
	@Column(name = "ssacaNome")
	private String nome;
	@Column(name = "ssacaNomFant")
	private String nomeFantasia;
	@Column(name = "ssacaEnd")
	private String endereco;
	@Column(name = "ssacaBairro")
	private String bairro;
	@Column(name = "ssacaCidade")
	private String cidade;
	@Column(name = "ssacaUF")
	private String uf;
	@Column(name = "ssacaCEP")
	private String cep;
	@Column(name = "ssacaFone")
	private String telefone;
	@Column(name = "ssacaEmail")
	private String email;
	@Column(name = "ISACADDDCel")
	private String dddCelular;
	@Column(name = "ISACACelular")
	private String celular;
	@Column(name = "ssacaNomeAval")
	private String nomeSacadorAvalista;
	@Column(name = "bsacaTpAval")
	private String tipoPessoaSacadorAvalista;
	@Column(name = "ssacaCPFCNPJAval")
	private String cpfCnpjSacadorAvalista;
	
	@Override
	public String toString() {
		return String.format(
				"Sacado [id=%s, codigoGrupoSacado=%s, tipoPessoa=%s, cpfCnpj=%s, nome=%s, nomeFantasia=%s, endereco=%s, bairro=%s, cidade=%s, uf=%s, cep=%s, telefone=%s, email=%s, dddCelular=%s, celular=%s, nomeSacadorAvalista=%s, tipoPessoaSacadorAvalista=%s, cpfCnpjSacadorAvalista=%s]",
				id, codigoGrupoSacado, tipoPessoa, cpfCnpj, nome, nomeFantasia, endereco, bairro, cidade, uf, cep,
				telefone, email, dddCelular, celular, nomeSacadorAvalista, tipoPessoaSacadorAvalista,
				cpfCnpjSacadorAvalista);
	}

	public SacadoCobCaixaPK getId() {
		return id;
	}

	public void setId(SacadoCobCaixaPK id) {
		this.id = id;
	}

	public String getCodigoGrupoSacado() {
		return codigoGrupoSacado;
	}

	public void setCodigoGrupoSacado(String codigoGrupoSacado) {
		this.codigoGrupoSacado = codigoGrupoSacado;
	}

	public Boolean getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Boolean tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNomeSacadorAvalista() {
		return nomeSacadorAvalista;
	}

	public void setNomeSacadorAvalista(String nomeSacadorAvalista) {
		this.nomeSacadorAvalista = nomeSacadorAvalista;
	}

	public String getTipoPessoaSacadorAvalista() {
		return tipoPessoaSacadorAvalista;
	}

	public void setTipoPessoaSacadorAvalista(String tipoPessoaSacadorAvalista) {
		this.tipoPessoaSacadorAvalista = tipoPessoaSacadorAvalista;
	}

	public String getCpfCnpjSacadorAvalista() {
		return cpfCnpjSacadorAvalista;
	}

	public void setCpfCnpjSacadorAvalista(String cpfCnpjSacadorAvalista) {
		this.cpfCnpjSacadorAvalista = cpfCnpjSacadorAvalista;
	}
	
}
