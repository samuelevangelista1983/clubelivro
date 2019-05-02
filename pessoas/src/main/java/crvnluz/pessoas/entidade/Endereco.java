package crvnluz.pessoas.entidade;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.util.StringUtils;

public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 498751081317873579L;
	
	private String id;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String observacao;
	@Transient
	private String operacao;
	
	public Endereco() {}

	public Endereco(String operacao) {
		this.operacao = operacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
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
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
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
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", cep=" + cep + ", logradouro=" + logradouro + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf
				+ ", observacao=" + observacao + ", operacao=" + operacao + "]";
	}

	public static void validar(Endereco endereco) {
		if (endereco == null) {
			throw new IllegalArgumentException("O endereço não pode ser nulo");
		}
		
		if (!StringUtils.hasText(endereco.operacao)) {
			throw new IllegalArgumentException("A operação não pode ser vazia");
		}
		
		if (!StringUtils.hasText(endereco.id)) {
			throw new IllegalArgumentException("O ID do endereço não pode ser vazio");
		}
		
		if (!StringUtils.hasText(endereco.cep)) {
			throw new IllegalArgumentException("O CEP do endereço não pode ser vazio");
		}
		
		if (endereco.cep.length() != 8) {
			throw new IllegalArgumentException("O CEP do endereço deve ter 8 dígitos");
		}
		
		if (!StringUtils.hasText(endereco.logradouro)) {
			throw new IllegalArgumentException("O logradouro do endereço não pode ser vazio");
		}
		
		if (!StringUtils.hasText(endereco.numero)) {
			throw new IllegalArgumentException("O número do endereço não pode ser vazio");
		}
		
		if (endereco.complemento != null) {
			if (!StringUtils.hasText(endereco.complemento)) {
				endereco.setComplemento(null);
			}
		}
		
		if (!StringUtils.hasText(endereco.bairro)) {
			throw new IllegalArgumentException("O bairro do endereço não pode ser vazio");
		}
		
		if (!StringUtils.hasText(endereco.cidade)) {
			throw new IllegalArgumentException("A cidade do endereço não pode ser vazia");
		}
		
		if (!StringUtils.hasText(endereco.uf)) {
			throw new IllegalArgumentException("A UF do endereço não pode ser vazia");
		}
		
		if (endereco.observacao != null) {
			if (!StringUtils.hasText(endereco.observacao)) {
				endereco.setObservacao(null);
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

}
