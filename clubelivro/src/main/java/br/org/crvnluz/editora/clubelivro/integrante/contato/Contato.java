package br.org.crvnluz.editora.clubelivro.integrante.contato;

import java.util.regex.Pattern;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;

public class Contato extends Persistente {
	
	private static final long serialVersionUID = -60709550708444470L;
	
	private Long idPessoa;
	private Long idTipo;
	private String valor;
	private String observacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public Contato() {}

	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Contato [idPessoa=" + idPessoa + ", idTipo=" + idTipo + ", valor=" + valor + ", observacao=" + observacao
				+ ", id=" + id + "]";
	}
	
	public static void validar(Contato contato) throws ValidacaoException {
		Long idTipo = contato.getIdTipo();
		
		if (idTipo == null) {
			throw new ValidacaoException("O tipo do contato do integrante do Clube do Livro deve ser informado");
		}
		
		String valor = contato.getValor();
		
		if (idTipo == 1) {
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches("[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}", valor)) {
				throw new ValidacaoException("O telefone fixo do integrante do Clube do Livro não é válido");
			}
			
		} else if (idTipo == 3 || idTipo == 5) {
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches("[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}", valor)) {
				throw new ValidacaoException("O telefone celular do integrante do Clube do Livro não é válido");
			}
			
			if (idTipo == 5) {
				contato.setId(null);
			}
			
		} else if (idTipo == 4) {
			String mailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			
			if (StringUtil.stringNaoNulaENaoVazia(valor) && !Pattern.matches(mailPattern, valor)) {
				throw new ValidacaoException("O e-mail do integrante do Clube do Livro não é válido");
			}
		}
	}
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
