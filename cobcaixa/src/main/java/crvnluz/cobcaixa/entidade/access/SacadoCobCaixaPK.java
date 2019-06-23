package crvnluz.cobcaixa.entidade.access;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SacadoCobCaixaPK implements Serializable {
	
	private static final long serialVersionUID = -1348369739563912957L;
	
	@Column(name = "lcedeSeqCedente")
	private Long codigoCedente;
	@Column(name = "ssacaCodSacado")
	private String codigoSacado;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCedente == null) ? 0 : codigoCedente.hashCode());
		result = prime * result + ((codigoSacado == null) ? 0 : codigoSacado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SacadoCobCaixaPK)) {
			return false;
		}
		SacadoCobCaixaPK other = (SacadoCobCaixaPK) obj;
		if (codigoCedente == null) {
			if (other.codigoCedente != null) {
				return false;
			}
		} else if (!codigoCedente.equals(other.codigoCedente)) {
			return false;
		}
		if (codigoSacado == null) {
			if (other.codigoSacado != null) {
				return false;
			}
		} else if (!codigoSacado.equals(other.codigoSacado)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("SacadoPK [codigoCedente=%s, codigoSacado=%s]", codigoCedente, codigoSacado);
	}

	public Long getCodigoCedente() {
		return codigoCedente;
	}

	public void setCodigoCedente(Long codigoCedente) {
		this.codigoCedente = codigoCedente;
	}

	public String getCodigoSacado() {
		return codigoSacado;
	}

	public void setCodigoSacado(String codigoSacado) {
		this.codigoSacado = codigoSacado;
	}
	
}
