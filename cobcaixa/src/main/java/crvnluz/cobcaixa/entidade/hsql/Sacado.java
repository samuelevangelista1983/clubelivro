package crvnluz.cobcaixa.entidade.hsql;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.eti.sen.utilitarios.texto.StringUtil;
import crvnluz.cobcaixa.entidade.access.SacadoCobCaixa;

@Entity
public class Sacado implements Serializable {

	private static final long serialVersionUID = 572378222794489301L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String documento;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String email;
	private String telefone;
	
	public Sacado() {}
	
	public static Sacado converter(SacadoCobCaixa sacadoCobCaixa) {
		Sacado sacado = new Sacado();
		sacado.setNome(sacadoCobCaixa.getNome().trim());
		sacado.setDocumento(sacadoCobCaixa.getCpfCnpj().trim());
		sacado.setCep(sacadoCobCaixa.getCep().trim());
		String[] dadosEndereco = sacadoCobCaixa.getEndereco().split(" ");
		StringBuilder logradouro = new StringBuilder();
		StringBuilder complemento = new StringBuilder();
		String numero = null;
		
		for (String dado: dadosEndereco) {
			if (!StringUtil.contemDigito(dado) && numero == null) {
				String tmp = dado.trim();
				
				if (StringUtil.stringNaoNulaENaoVazia(tmp)) {
					logradouro.append(dado).append(StringUtil.ESPACO);
				}
				
			} else if (StringUtil.contemDigito(dado) && numero == null) {
				String tmp = dado.trim();
				
				if (StringUtil.stringNaoNulaENaoVazia(tmp)) {
					numero = tmp;
				}
				
			} else {
				String tmp = dado.trim();
				
				if (StringUtil.stringNaoNulaENaoVazia(tmp)) {
					complemento.append(tmp).append(StringUtil.ESPACO);
				}
			}
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(logradouro.toString())) {
			sacado.setLogradouro(logradouro.toString().trim());
		}
		
		sacado.setNumero(numero);
		
		if (StringUtil.stringNaoNulaENaoVazia(complemento.toString())) {
			sacado.setComplemento(complemento.toString().trim());
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(sacadoCobCaixa.getBairro().trim())) {
			sacado.setBairro(sacadoCobCaixa.getBairro().trim());
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(sacadoCobCaixa.getCidade().trim())) {
			sacado.setCidade(sacadoCobCaixa.getCidade().trim());
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(sacadoCobCaixa.getUf().trim())) {
			sacado.setUf(sacadoCobCaixa.getUf().trim());
		}
		
		String email = sacadoCobCaixa.getEmail().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(email)) {
			sacado.setEmail(email);
		}
		
		String telefone = sacadoCobCaixa.getTelefone().trim();
		
		if (StringUtil.stringNaoNulaENaoVazia(telefone)) {
			sacado.setTelefone(telefone);
		}
		
		return sacado;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Sacado [id=%s, nome=%s, documento=%s, cep=%s, logradouro=%s, numero=%s, complemento=%s, bairro=%s, cidade=%s, uf=%s, email=%s, telefone=%s]",
				id, nome, documento, cep, logradouro, numero, complemento, bairro, cidade, uf, email, telefone);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
