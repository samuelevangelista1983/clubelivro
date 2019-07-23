package br.org.crvnluz.editora.clubelivro.integracao.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;

@Entity
@Table(name="integrante_processado")
public class IntegranteProcessado implements Serializable {
	
	private static final long serialVersionUID = -4731450205229480853L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_integrante")
	private Integrante integrante;
	private int tentativas;
	private LocalDateTime proximaTentativa;
	private String msgErro;
	
	public IntegranteProcessado() {
		tentativas = 0;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integrante getIntegrante() {
		return integrante;
	}
	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}
	
	public int getTentativas() {
		return tentativas;
	}
	public void setTentativas(int tentativas) {
		this.tentativas = tentativas;
	}
	public LocalDateTime getProximaTentativa() {
		return proximaTentativa;
	}
	public void setProximaTentativa(LocalDateTime proximaTentativa) {
		this.proximaTentativa = proximaTentativa;
	}
	public String getMsgErro() {
		return msgErro;
	}
	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}
	
}
