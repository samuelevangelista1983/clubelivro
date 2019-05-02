package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.util.StringUtils;

import br.eti.sen.utilitarios.documento.CPF;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;

@Entity
public class Integrante implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -8310215301832974447L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean ativo;
	private LocalDate cadastro;
	private LocalDate desativacao;
	private String nome;
	private String cpf;
	private LocalDate nascimento;
	@OneToMany(mappedBy = "integrante")
	private List<Contato> contatos;
	@OneToMany(mappedBy = "integrante")
	private List<Endereco> enderecos;
	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;
	@ManyToOne
	@JoinColumn(name = "id_frequencia", nullable = false)
	private Frequencia frequencia;
	@ManyToOne
	@JoinColumn(name = "id_entrega", nullable = false)
	private FormaEntrega formaEntrega;
	@ManyToOne
	@JoinColumn(name = "id_forma_pgto", nullable = false)
	private FormaPgto formaPgtoPref;
	private Integer diaVctoPreferencial;
	
	// CONSTRUTORES PÚBLICOS
	
	public Integrante() {
		contatos = new ArrayList<>();
		enderecos = new ArrayList<>();
	}
	
	// MÉTODOS PÚBLICOS
	
	public void adicionarContato(Contato contato) {
		if (contatos == null) {
			contatos = new ArrayList<>();
		}
		
		if (!contatos.contains(contato)) {
			contato.setIntegrante(this);
			contatos.add(contato);
		}
	}
	
	public void adicionarEndereco(Endereco endereco) {
		if (enderecos == null) {
			enderecos = new ArrayList<>();
		}
		
		if (!enderecos.contains(endereco)) {
			endereco.setIntegrante(this);
			enderecos.add(endereco);
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Integrante clone = new Integrante();
		clone.id = id;
		clone.ativo = ativo;
		clone.cadastro = cadastro;
		clone.desativacao = desativacao;
		clone.nome = nome;
		clone.cpf = cpf;
		clone.nascimento = nascimento;
		clone.diaVctoPreferencial = diaVctoPreferencial;
		clone.categoria = categoria != null ? (Categoria) categoria.clone() : null;
		clone.frequencia = frequencia != null ? (Frequencia) frequencia.clone() : null;
		clone.formaEntrega = formaEntrega != null ? (FormaEntrega) formaEntrega.clone() : null;
		clone.formaPgtoPref = formaPgtoPref != null ? (FormaPgto) formaPgtoPref.clone() : null;
		
		for (Contato contato : contatos) {
			clone.adicionarContato((Contato) contato.clone());
		}
		
		for (Endereco endereco : enderecos) {
			clone.adicionarEndereco((Endereco) endereco.clone());
		}
		
		return clone;
	}

	@Override
	public String toString() {
		return String.format(
				"Integrante [id=%s, ativo=%s, cadastro=%s, desativacao=%s, nome=%s, cpf=%s, nascimento=%s, contatos=%s, enderecos=%s, categoria=%s, frequencia=%s, formaEntrega=%s, formaPgtoPref=%s, diaVctoPreferencial=%s]",
				id, ativo, cadastro, desativacao, nome, cpf, nascimento, contatos, enderecos, categoria, frequencia,
				formaEntrega, formaPgtoPref, diaVctoPreferencial);
	}
	
	public static void validar(Integrante integrante) throws ValidacaoException {
		LocalDate dtCadastro = integrante.cadastro;
		
		if (dtCadastro == null) {
			throw new ValidacaoException("A data de cadastro do integrante do Clube do Livro não é válida");
		}
	
		if (!StringUtils.hasText(integrante.nome)) {
			throw new ValidacaoException("O nome do integrante do Clube do Livro não pode ser vazio");
		}
		
		if (integrante.nascimento != null) {
			if (integrante.nascimento.isAfter(LocalDate.now())) {
				throw new ValidacaoException("O nascimento do integrante do Clube do Livro não pode ser uma data futura");
			}
		}
		
		for (Contato contato: integrante.contatos) {
			Contato.validar(contato);
		}
		
		if (!StringUtils.hasText(integrante.cpf)) {
			throw new ValidacaoException("O CPF do integrante do Clube do Livro não pode ser vazio");
		}
		
		if (!CPF.validar(integrante.cpf)) {
			throw new ValidacaoException("O CPF do integrante do Clube do Livro não é válido");
		}
		
		Categoria categoria = integrante.categoria;
		
		if (categoria == null || categoria.getId() == null) {
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
		
		for (Endereco endereco: integrante.enderecos) {
			Endereco.validar(endereco);
		}
		
		boolean possuiEnderecoEntrega = integrante.enderecos.stream().filter(e -> e.isEntrega()).count() > 0;
		
		if (entrega.getId() == 1 && !possuiEnderecoEntrega) {
			throw new ValidacaoException("O endereço de entrega dos livros do integrante do Clube do Livro deve ser informado");
		}
		
		FormaPgto formaPgto = integrante.getFormaPgtoPref();
		
		if (formaPgto == null || formaPgto.getId() == null) {
			throw new ValidacaoException("A forma de pagamento ao Clube do Livro deve ser informada");
		}
		
		boolean possuiEnderecoCobranca = integrante.enderecos.stream().filter(e -> e.isCobranca()).count() > 0;
		
		if (formaPgto.getId() == 1 && !possuiEnderecoCobranca) {
			throw new ValidacaoException("O endereço de cobrança do integrante do Clube do Livro deve ser informado");
		}
		
		Integer diaPgto = integrante.getDiaVctoPreferencial();
		
		if (diaPgto != null && diaPgto < 1 || diaPgto > 31) {
			throw new ValidacaoException("O dia preferencial de pagamento deve ter um valor entre 1 e 31");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getCadastro() {
		return cadastro;
	}

	public void setCadastro(LocalDate cadastro) {
		this.cadastro = cadastro;
	}

	public LocalDate getDesativacao() {
		return desativacao;
	}

	public void setDesativacao(LocalDate desativacao) {
		this.desativacao = desativacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
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

	public Integer getDiaVctoPreferencial() {
		return diaVctoPreferencial;
	}

	public void setDiaVctoPreferencial(Integer diaVctoPreferencial) {
		this.diaVctoPreferencial = diaVctoPreferencial;
	}

	public List<Contato> getContatos() {
		if (contatos == null) {
			contatos = new ArrayList<>();
		}
		
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public List<Endereco> getEnderecos() {
		if (enderecos == null) {
			enderecos = new ArrayList<>();
		}
		
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	
}
