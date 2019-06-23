package crvnluz.cobcaixa.entidade.hsql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BoletoPendente implements Serializable {
	
	private static final long serialVersionUID = 5334063145018437374L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_boleto", nullable = false)
	private Boleto boleto;
	private LocalDateTime momentoEnvio;
	private String erro;
	private Boolean enviado;
	
	@Override
	public String toString() {
		return String.format("BoletoPendente [id=%s, boleto=%s, momentoEnvio=%s, erro=%s, enviado=%s]", id, boleto,
				momentoEnvio, erro, enviado);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public LocalDateTime getMomentoEnvio() {
		return momentoEnvio;
	}

	public void setMomentoEnvio(LocalDateTime momentoEnvio) {
		this.momentoEnvio = momentoEnvio;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public Boolean getEnviado() {
		return enviado;
	}

	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}
	
}
