package crvnluz.cobcaixa.entidade.access;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoletoCobCaixaPK implements Serializable {
	
	private static final long serialVersionUID = -2842756449235737809L;
	
	@Column(name = "lcedeSeqCedente")
	private Long idCedente;
	@Column(name = "stituNumDoc")
	private String numeroDocumento;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCedente == null) ? 0 : idCedente.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
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
		if (!(obj instanceof BoletoCobCaixaPK)) {
			return false;
		}
		BoletoCobCaixaPK other = (BoletoCobCaixaPK) obj;
		if (idCedente == null) {
			if (other.idCedente != null) {
				return false;
			}
		} else if (!idCedente.equals(other.idCedente)) {
			return false;
		}
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null) {
				return false;
			}
		} else if (!numeroDocumento.equals(other.numeroDocumento)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("BoletoCobCaixaPK [idCedente=%s, numeroDocumento=%s]", idCedente, numeroDocumento);
	}

	public Long getIdCedente() {
		return idCedente;
	}

	public void setIdCedente(Long idCedente) {
		this.idCedente = idCedente;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
}
