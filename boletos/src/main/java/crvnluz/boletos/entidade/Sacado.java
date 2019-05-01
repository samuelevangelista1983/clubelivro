package crvnluz.boletos.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.util.StringUtils;

@Entity
public class Sacado implements Serializable {

	private static final long serialVersionUID = 8421756970708951264L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	private Long versao;
	private String nome;
	private String documento;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	
	public Sacado() {}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Sacado)) {
			return false;
		}
		Sacado other = (Sacado) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		} else if (!bairro.equals(other.bairro)) {
			return false;
		}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (cidade == null) {
			if (other.cidade != null) {
				return false;
			}
		} else if (!cidade.equals(other.cidade)) {
			return false;
		}
		if (complemento == null) {
			if (other.complemento != null) {
				return false;
			}
		} else if (!complemento.equals(other.complemento)) {
			return false;
		}
		if (documento == null) {
			if (other.documento != null) {
				return false;
			}
		} else if (!documento.equals(other.documento)) {
			return false;
		}
		if (logradouro == null) {
			if (other.logradouro != null) {
				return false;
			}
		} else if (!logradouro.equals(other.logradouro)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (uf == null) {
			if (other.uf != null) {
				return false;
			}
		} else if (!uf.equals(other.uf)) {
			return false;
		}
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
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("Sacado [id=%s, versao=%s, nome=%s, documento=%s, cep=%s, logradouro=%s, numero=%s, complemento=%s, bairro=%s, cidade=%s, uf=%s]",
				id, versao, nome, documento, cep, logradouro, numero, complemento, bairro, cidade, uf);
	}
	
	public static void validar(Sacado sacado) {
		if (sacado == null) {
			throw new IllegalArgumentException("O sacado não pode ser nulo");
		}
		
		if (!StringUtils.hasText(sacado.nome)) {
			throw new IllegalArgumentException("O nome do sacado não pode ser vazio");
		}
		
		if (!StringUtils.hasText(sacado.documento)) {
			throw new IllegalArgumentException("O documento do sacado não pode ser vazio");
		}
		
		if (!StringUtils.hasText(sacado.cep)) {
			throw new IllegalArgumentException("O CEP do endereço do sacado não pode ser vazio");
		}
		
		if (sacado.cep.length() != 8) {
			throw new IllegalArgumentException("O CEP do endereço do sacado deve ter 8 dígitos");
		}
		
		if (!StringUtils.hasText(sacado.logradouro)) {
			throw new IllegalArgumentException("O logradouro do endereço do sacado não pode ser vazio");
		}
		
		if (!StringUtils.hasText(sacado.numero)) {
			throw new IllegalArgumentException("O número do endereço do sacado não pode ser vazio");
		}
		
		if (sacado.complemento != null) {
			if (!StringUtils.hasText(sacado.complemento)) {
				sacado.setComplemento(null);
			}
		}
		
		if (!StringUtils.hasText(sacado.bairro)) {
			throw new IllegalArgumentException("O bairro do endereço do sacado não pode ser vazio");
		}
		
		if (!StringUtils.hasText(sacado.cidade)) {
			throw new IllegalArgumentException("A cidade do endereço do sacado não pode ser vazia");
		}
		
		if (!StringUtils.hasText(sacado.uf)) {
			throw new IllegalArgumentException("A UF do endereço do sacado não pode ser vazia");
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
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
	
}
