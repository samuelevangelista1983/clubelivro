package br.org.crvnluz.editora.clubelivro.controlador.integrante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.eti.sen.endereco.Localizador;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.controlador.Parametro;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.CategoriaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaEntregaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaPgtoService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FrequenciaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.TipoContatoService;
import br.org.crvnluz.editora.clubelivro.servico.integrante.IntegranteService;

@Controller
public class IntegranteController  {
	
	private final Logger logger = LoggerFactory.getLogger(IntegranteController.class);
	
	@Autowired
	private IntegranteService service;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private FormaEntregaService formaEntregaService;
	@Autowired
	private FormaPgtoService formaPgtoService;
	@Autowired
	private FrequenciaService frequenciaService;
	@Autowired
	private TipoContatoService tipoContatoService;

	private Integrante getIntegranteInicial() {
		Integrante integrante = new Integrante();
		integrante.setAtivo(true);
		integrante.setCadastro(LocalDate.now());
		integrante.adicionarEndereco(new Endereco());
		Contato telefone = new Contato();
		telefone.setTipo(tipoContatoService.getById(1l)); // Contato do tipo telefone fixo
		Contato celular = new Contato();
		celular.setTipo(tipoContatoService.getById(2l)); // Contato do tipo telefone celular
		Contato email = new Contato();
		email.setTipo(tipoContatoService.getById(3l)); // Contato do tipo e-mail
		integrante.adicionarContato(telefone);
		integrante.adicionarContato(celular);
		integrante.adicionarContato(email);
		return integrante;
	}
	
	private List<String> getUfs() {
		List<String> ufs = new ArrayList<>();
		ufs.add("MG");
		ufs.add("SP");
		ufs.add("AC");
		ufs.add("AL");
		ufs.add("AM");
		ufs.add("AP");
		ufs.add("BA");
		ufs.add("CE");
		ufs.add("DF");
		ufs.add("ES");
		ufs.add("GO");
		ufs.add("MA");
		ufs.add("MS");
		ufs.add("MT");
		ufs.add("PA");
		ufs.add("PB");
		ufs.add("PE");
		ufs.add("PI");
		ufs.add("PR");
		ufs.add("RJ");
		ufs.add("RN");
		ufs.add("RO");
		ufs.add("RR");
		ufs.add("RS");
		ufs.add("SC");
		ufs.add("SE");
		ufs.add("TO");
		return ufs;
	}
	
	@GetMapping("/integrantes/adicionar")
	public ModelAndView adicionar() {
		ModelAndView model = new ModelAndView("integrantes/cadastro");
		model.addObject("integrante", getIntegranteInicial());
		model.addObject("categorias", categoriaService.listarTodas());
		model.addObject("frequencias", frequenciaService.listarTodas());
		model.addObject("formasEntrega", formaEntregaService.listarTodas());
		model.addObject("formasPgto", formaPgtoService.listarTodas());
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/contatos/email/adicionar")
	public ModelAndView adicionarEmail(Integrante integrante) {
		Contato email = new Contato();
		email.setTipo(tipoContatoService.getById(3l)); // Contato do tipo e-mail
		integrante.adicionarContato(email);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/enderecos/adicionar")
	public ModelAndView adicionarEndereco(Integrante integrante) {
		integrante.adicionarEndereco(new Endereco());
		ModelAndView model = new ModelAndView("integrantes/endereco :: enderecoForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/contatos/telefonecelular/adicionar")
	public ModelAndView adicionarTelefoneCelular(Integrante integrante) {
		Contato celular = new Contato();
		celular.setTipo(tipoContatoService.getById(2l)); // Contato do tipo telefone celular
		integrante.adicionarContato(celular);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/contatos/telefonefixo/adicionar")
	public ModelAndView adicionarTelefoneFixo(Integrante integrante) {
		Contato telefone = new Contato();
		telefone.setTipo(tipoContatoService.getById(1l)); // Contato do tipo telefone fixo
		integrante.adicionarContato(telefone);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@GetMapping("/integrantes/ativar_desativar/{id}")
	public ModelAndView ativarOuDesativar(@PathVariable String id) {
		Integrante integrante = service.ativarDesativarIntegrante(new Long(id));
		String viewName = null;
		
		if (integrante.getAtivo()) {
			viewName = String.format("integrantes/resultadoPesquisa :: inativo (id = '%s')", id);
			
		} else {
			viewName = String.format("integrantes/resultadoPesquisa :: ativo (id = '%s')", id);
		}
		
		return new ModelAndView(viewName);
	}
	
	@GetMapping("/integrantes/cancelar/cadastro")
	public ModelAndView cancelarCadastro() {
		ModelAndView model = adicionar();
		model.setViewName("integrantes/index :: index");
		return model;
	}
	
	@PostMapping("/integrantes/endereco/consultar/{idx}")
	public ModelAndView consultarEndereco(Integrante integrante, @PathVariable("idx") int idx) throws Throwable {
		Endereco endereco = integrante.getEnderecos().get(idx);
		
		if (StringUtil.stringNaoNulaENaoVazia(endereco.getCep())) {
			new Localizador().pesquisarEndereco(endereco, endereco.getCep());
			
		} else {
			throw new ValidacaoException("É preciso informar um CEP");
		}
		
		ModelAndView model = new ModelAndView("integrantes/endereco :: enderecoForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@GetMapping("/integrantes/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		ModelAndView model = new ModelAndView("integrantes/cadastro");
		model.addObject("integrante", service.getIntegrante(id));
		model.addObject("categorias", categoriaService.listarTodas());
		model.addObject("frequencias", frequenciaService.listarTodas());
		model.addObject("formasEntrega", formaEntregaService.listarTodas());
		model.addObject("formasPgto", formaPgtoService.listarTodas());
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@GetMapping(value = {"/integrantes", "/integrantes/index"})
	public ModelAndView iniciar() {
		ModelAndView model = new ModelAndView("integrantes/index");
		model.addObject("categorias", categoriaService.listarTodas());
		model.addObject("frequencias", frequenciaService.listarTodas());
		model.addObject("formasEntrega", formaEntregaService.listarTodas());
		model.addObject("formasPgto", formaPgtoService.listarTodas());
		List<Parametro> situacoes = new ArrayList<>(2);
		situacoes.add(new Parametro(1l, "Ativo"));
		situacoes.add(new Parametro(0l, "Inativo"));
		model.addObject("situacoes", situacoes);
		List<Parametro> tipos = new ArrayList<>(2);
		tipos.add(new Parametro(0l, "Ascendente"));
		tipos.add(new Parametro(1l, "Descendente"));
		model.addObject("tipos", tipos);
		return model;
	}
	
	@PostMapping("/integrantes/pesquisa")
	public ModelAndView pesquisar(@RequestParam("nome") String nome, @RequestParam("idCategoria") Long idCategoria) {
		ModelAndView model = new ModelAndView("integrantes/resultadoPesquisa :: resultado");
		List<Integrante> integrantes = service.pesquisar(nome, idCategoria);
		model.addObject("integrantes", integrantes);
		
		if (integrantes.isEmpty()) {
			model.addObject("exibirMensagem", true);
		}
		
		return model;
	}
	
	@PostMapping("/integrantes/pesquisa/avancada")
	public ModelAndView pesquisar(PesquisaAvancada pesquisa) {
		ModelAndView model = new ModelAndView("integrantes/resultadoPesquisa :: resultado");
		
		try {
			List<Integrante> integrantes = service.pesquisar(pesquisa.getNome(), pesquisa.getCpf(), pesquisa.getIdCategoria(), 
					pesquisa.getIdFrequencia(), pesquisa.getIdFormaPgto(), pesquisa.getIdFormaEntrega(), pesquisa.getSituacao(), pesquisa.getOrdenacao());
			model.addObject("integrantes", integrantes);
			
			if (integrantes.isEmpty()) {
				model.addObject("exibirMensagem", true);
			}
			
		} catch (ValidacaoException e) {
			logger.error("Houve um erro ao pesquisar por integrantes", e);
			model.addObject("erro", e.getMessage());
		}
		
		return model;
	}
	
	@PostMapping("/integrantes/contatos/email/remover/{idx}")
	public ModelAndView removerEmail(Integrante integrante, @PathVariable("idx") int idx) {
		integrante.removerEmail(idx);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/enderecos/remover/{idx}")
	public ModelAndView removerEndereco(Integrante integrante, @PathVariable("idx") int idx) {
		integrante.removerEndereco(idx);
		ModelAndView model = new ModelAndView("integrantes/endereco :: enderecoForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/contatos/telefonecelular/remover/{idx}")
	public ModelAndView removerTelefoneCelular(Integrante integrante, @PathVariable("idx") int idx) {
		integrante.removerTelefoneCelular(idx);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/contatos/telefonefixo/remover/{idx}")
	public ModelAndView removerTelefoneFixo(Integrante integrante, @PathVariable("idx") int idx) {
		integrante.removerTelefoneFixo(idx);
		ModelAndView model = new ModelAndView("integrantes/contatos :: contatosForm");
		model.addObject("integrante", integrante);
		model.addObject("ufs", getUfs());
		return model;
	}
	
	@PostMapping("/integrantes/salvar")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ModelAndView salvar(@Valid Integrante integrante, BindingResult bindingResult) throws Throwable {
		ModelAndView model = null;
		
		if (!bindingResult.hasErrors()) {
			service.salvar(integrante);
			model = iniciar();
			model.setViewName("integrantes/index :: incluido");
			model.addObject("incluido", true);
			
		} else {
			FieldError fieldError = bindingResult.getFieldError();
			String field = fieldError.getField();
			
			if (field.equalsIgnoreCase("cadastroStr")) {
				throw new ValidacaoException("A data de cadastro do integrante do Clube do Livro não é válida");
			}
			
			if (field.equalsIgnoreCase("nascimentoStr")) {
				throw new ValidacaoException("A data de nascimento do integrante do Clube do Livro não é válida");
			}
		}
		
		return model;
	}
	
}
