package br.org.crvnluz.editora.clubelivro.integrante;

import java.time.LocalDate;
import java.util.regex.Pattern;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.configuracao.classificacao.Classificacao;
import br.org.crvnluz.editora.clubelivro.configuracao.entrega.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.configuracao.formapgto.FormaPgto;
import br.org.crvnluz.editora.clubelivro.configuracao.frequencia.Frequencia;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.Persistente;
import br.org.crvnluz.editora.clubelivro.integrante.documento.Documento;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.Endereco;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.Pessoa;

public class Integrante extends Persistente {
	
	private static final long serialVersionUID = -3324709635172775416L;
	
	private Pessoa pessoa;
	private Documento documento;
	private Endereco enderecoCobranca;
	private Endereco enderecoEntrega;
	private Frequencia frequencia;
	private FormaEntrega formaEntrega;
	private FormaPgto formaPgtoPref;
	private Integer diaPgtoPref;
	private Classificacao classificacao;
	private Boolean ativo;
	private LocalDate dtCadastro;
	private LocalDate dtDesativacao;
	
	// CONSTRUTORES PÚBLICOS
	
	public Integrante() {}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public String toString() {
		return "Integrante [pessoa=" + pessoa + ", documento=" + documento + ", enderecoCobranca=" + enderecoCobranca
				+ ", enderecoEntrega=" + enderecoEntrega + ", formaEntrega=" + formaEntrega + ", formaPgtoPref="
				+ formaPgtoPref + ", diaPgtoPref=" + diaPgtoPref + ", ativo=" + ativo + ", dtCadastro=" 
				+ dtCadastro + ", dtDesativacao=" + dtDesativacao + ", classificacao=" 
				+ classificacao + ", id=" + id + "]";
	}
	
	public static void validar(Integrante integrante) throws ValidacaoException {
		LocalDate dtCadastro = integrante.getDtCadastro();
		
		if (dtCadastro == null) {
			throw new ValidacaoException("A data de cadastro do integrante do Clube do Livro deve ser informada");
		}
		
		String data = DataUtil.formatarData(dtCadastro);
		String dataPattern = "^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$";
		
		if (!Pattern.matches(dataPattern, data)) {
			throw new ValidacaoException("A data de cadastro do integrante do Clube do Livro não é válida");
		}
		
		Pessoa.validar(integrante.getPessoa());
		
		Classificacao classificacao = integrante.getClassificacao();
		
		if (classificacao == null || classificacao.getId() == null) {
			throw new ValidacaoException("A categoria do integrante do Clube do Livro deve ser informada");
		}
		
		Frequencia frequencia = integrante.getFrequencia();
		
		if (frequencia == null || frequencia.getId() == null) {
			throw new ValidacaoException("A freqüência do integrante do Clube do Livro deve ser informada");
		}
		
		FormaEntrega entrega = integrante.getFormaEntrega();
		
		if (entrega == null || entrega.getId() == null) {
			throw new ValidacaoException("A forma de entrega dos livros ao integrante do Clube do Livro deve ser informada");
		}
		
		Endereco enderecoEntrega = integrante.getEnderecoEntrega();
		
		if (entrega.getId() == 1 && (enderecoEntrega == null || enderecoEntrega.getCep() == null)) {
			throw new ValidacaoException("O endereço de entrega dos livros do integrante do Clube do Livro deve ser informado");
		}
		
		FormaPgto formaPgto = integrante.getFormaPgtoPref();
		
		if (formaPgto == null || formaPgto.getId() == null) {
			throw new ValidacaoException("A forma de pagamento ao Clube do Livro deve ser informada");
		}
		
		Endereco enderecoCobranca = integrante.getEnderecoCobranca();
		
		if (formaPgto.getId() == 1 && (enderecoCobranca == null || enderecoCobranca.getCep() == null)) {
			throw new ValidacaoException("O endereço de cobrança do integrante do Clube do Livro deve ser informado");
		}
		
		Integer diaPgto = integrante.getDiaPgtoPref();
		
		if (diaPgto == null) {
			throw new ValidacaoException("O dia preferencial de pagamento deve ser informamdo");
		}
		
		if (diaPgto < 1 || diaPgto > 31) {
			throw new ValidacaoException("O dia preferencial de pagamento deve ter um valor entre 1 e 31");
		}
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Endereco getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Endereco enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public FormaEntrega getFormaEntrega() {
		return formaEntrega;
	}

	public void setFormaEntrega(FormaEntrega formaEntrega) {
		this.formaEntrega = formaEntrega;
	}

	public FormaPgto getFormaPgtoPref() {
		return formaPgtoPref;
	}

	public void setFormaPgtoPref(FormaPgto formaPgtoPref) {
		this.formaPgtoPref = formaPgtoPref;
	}

	public Integer getDiaPgtoPref() {
		return diaPgtoPref;
	}

	public void setDiaPgtoPref(Integer diaPgtoPref) {
		this.diaPgtoPref = diaPgtoPref;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDtDesativacao() {
		return dtDesativacao;
	}

	public void setDtDesativacao(LocalDate dtDesativacao) {
		this.dtDesativacao = dtDesativacao;
	}

	public LocalDate getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(LocalDate dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	
	public void setDtCadastro(String data) {
		if (StringUtil.stringNaoNulaENaoVazia(data)) {
			dtCadastro = DataUtil.parserData(data);
		}
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

	public Classificacao getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

}
