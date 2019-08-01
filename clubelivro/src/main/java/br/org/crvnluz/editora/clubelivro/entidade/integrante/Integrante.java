package br.org.crvnluz.editora.clubelivro.entidade.integrante;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	@OneToMany(mappedBy = "integrante", cascade = CascadeType.ALL)
	private List<Contato> contatos;
	@OneToMany(mappedBy = "integrante", cascade = CascadeType.ALL)
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
			contatos.add(contato);
			contato.setIntegrante(this);
		}
	}
	
	public void adicionarEndereco(Endereco endereco) {
		if (enderecos == null) {
			enderecos = new ArrayList<>();
		}
		
		if (!enderecos.contains(endereco)) {
			enderecos.add(endereco);
			endereco.setIntegrante(this);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((cadastro == null) ? 0 : cadastro.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((contatos == null) ? 0 : contatos.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((desativacao == null) ? 0 : desativacao.hashCode());
		result = prime * result + ((diaVctoPreferencial == null) ? 0 : diaVctoPreferencial.hashCode());
		result = prime * result + ((enderecos == null) ? 0 : enderecos.hashCode());
		result = prime * result + ((formaEntrega == null) ? 0 : formaEntrega.hashCode());
		result = prime * result + ((formaPgtoPref == null) ? 0 : formaPgtoPref.hashCode());
		result = prime * result + ((frequencia == null) ? 0 : frequencia.hashCode());
		result = prime * result + ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (!(obj instanceof Integrante)) {
			return false;
		}
		Integrante other = (Integrante) obj;
		if (ativo == null) {
			if (other.ativo != null) {
				return false;
			}
		} else if (!ativo.equals(other.ativo)) {
			return false;
		}
		if (cadastro == null) {
			if (other.cadastro != null) {
				return false;
			}
		} else if (!cadastro.equals(other.cadastro)) {
			return false;
		}
		if (categoria == null) {
			if (other.categoria != null) {
				return false;
			}
		} else if (!categoria.equals(other.categoria)) {
			return false;
		}
		if (contatos == null) {
			if (other.contatos != null) {
				return false;
			}
		} else if (!contatos.equals(other.contatos)) {
			return false;
		}
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!cpf.equals(other.cpf)) {
			return false;
		}
		if (desativacao == null) {
			if (other.desativacao != null) {
				return false;
			}
		} else if (!desativacao.equals(other.desativacao)) {
			return false;
		}
		if (diaVctoPreferencial == null) {
			if (other.diaVctoPreferencial != null) {
				return false;
			}
		} else if (!diaVctoPreferencial.equals(other.diaVctoPreferencial)) {
			return false;
		}
		if (enderecos == null) {
			if (other.enderecos != null) {
				return false;
			}
		} else if (!enderecos.equals(other.enderecos)) {
			return false;
		}
		if (formaEntrega == null) {
			if (other.formaEntrega != null) {
				return false;
			}
		} else if (!formaEntrega.equals(other.formaEntrega)) {
			return false;
		}
		if (formaPgtoPref == null) {
			if (other.formaPgtoPref != null) {
				return false;
			}
		} else if (!formaPgtoPref.equals(other.formaPgtoPref)) {
			return false;
		}
		if (frequencia == null) {
			if (other.frequencia != null) {
				return false;
			}
		} else if (!frequencia.equals(other.frequencia)) {
			return false;
		}
		if (nascimento == null) {
			if (other.nascimento != null) {
				return false;
			}
		} else if (!nascimento.equals(other.nascimento)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
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
