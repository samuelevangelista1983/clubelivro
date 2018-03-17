package br.org.crvnluz.editora.clubelivro.integrante.endereco;

import br.eti.sen.endereco.Enderecavel;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Endereco extends Persistente implements Enderecavel {
	
	private static final long serialVersionUID = -8514427045221317659L;
	
	private Long idPessoa;
	private Long idTipo;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String uf;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public Endereco() {}
	
	public Endereco(Long id) {
		super(id);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((idTipo == null) ? 0 : idTipo.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (municipio == null) {
			if (other.municipio != null)
				return false;
		} else if (!municipio.equals(other.municipio))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (idTipo == null) {
			if (other.idTipo != null)
				return false;
		} else if (!idTipo.equals(other.idTipo))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [idPessoa=" + idPessoa + "idTipo=" + idTipo + ", cep=" + cep + ", logradouro=" + logradouro + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", municipio=" + municipio + ", uf=" + uf
				+ ", observacao=" + observacao + ", id=" + id + "]";
	}
	
	public static void validar(Endereco endereco) throws ValidacaoException {
		Long idTipo = endereco.getIdTipo();
		
		if (idTipo == null) {
			throw new ValidacaoException("O tipo do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		String cep = endereco.getCep();
		
		if (StringUtil.stringNulaOuVazia(cep)) {
			throw new ValidacaoException("O CEP do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (cep.length() != 8) {
			throw new ValidacaoException("O CEP do endereço do integrante do Clube do Livro não é válido");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getLogradouro())) {
			throw new ValidacaoException("O logradouro do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getNumero())) {
			throw new ValidacaoException("O número do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getBairro())) {
			throw new ValidacaoException("O bairro do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getMunicipio())) {
			throw new ValidacaoException("O município do endereço do integrante do Clube do Livro deve ser informado");
		}
		
		if (StringUtil.stringNulaOuVazia(endereco.getUf())) {
			throw new ValidacaoException("A UF do endereço do integrante do Clube do Livro deve ser informada");
		}
		
		endereco.setCep(cep);
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

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setCidade(String cidade) {
		setMunicipio(cidade);
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public Long getIdTipo() {
		return idTipo;
	}
	
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

}
