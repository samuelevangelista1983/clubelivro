package br.org.crvnluz.editora.clubelivro.controlador.financeiro.boleto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.org.crvnluz.editora.clubelivro.controlador.Parametro;
import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Boleto;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.CategoriaService;
import br.org.crvnluz.editora.clubelivro.servico.financeiro.BoletoService;

@Controller
public class BoletoController {
	
	private final Logger logger = LoggerFactory.getLogger(BoletoController.class);
	
	@Autowired
	private BoletoService service;
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/relatorios/boletos/detalhar/{id}")
	public ModelAndView getBoleto(@PathVariable Long id) {
		ModelAndView model = new ModelAndView("relatorios/boletos/boleto");
		model.addObject("boleto", service.getBoleto(id));
		return model;
	}
	
	@GetMapping(value = {"/relatorios/boletos", "/relatorios/boletos/index"})
	public ModelAndView iniciar() {
		ModelAndView model = new ModelAndView("relatorios/boletos/index");
		model.addObject("categorias", categoriaService.listarTodas());
		List<Parametro> situacoes = new ArrayList<>(2);
		situacoes.add(new Parametro(0l, "Aberto"));
		situacoes.add(new Parametro(1l, "Baixado"));
		situacoes.add(new Parametro(2l, "Baixado manualmente"));
		situacoes.add(new Parametro(3l, "Cancelado"));
		situacoes.add(new Parametro(4l, "Cancelado manualmente"));
		situacoes.add(new Parametro(5l, "Erro processamento"));
		model.addObject("situacoes", situacoes);
		List<Parametro> tipos = new ArrayList<>(2);
		tipos.add(new Parametro(0l, "Ascendente"));
		tipos.add(new Parametro(1l, "Descendente"));
		model.addObject("tipos", tipos);
		List<Parametro> campos = new ArrayList<>(2);
		campos.add(new Parametro(0l, "Sacado"));
		campos.add(new Parametro(1l, "Número do boleto"));
		campos.add(new Parametro(2l, "Data de emissão"));
		campos.add(new Parametro(3l, "Data de vencimento"));
		model.addObject("campos", campos);
		return model;
	}
	
	@PostMapping("/relatorios/boletos/pesquisa")
	public ModelAndView pesquisar(@RequestParam("nome") String nome, @RequestParam("numeroBoleto") String numeroBoleto) {
		ModelAndView model = new ModelAndView("relatorios/boletos/resultadoPesquisa :: resultado");
		List<Boleto> boletos = service.pesquisar(nome, numeroBoleto);
		model.addObject("boletos", boletos);
		
		if (boletos.isEmpty()) {
			model.addObject("exibirMensagem", true);
		}
		
		return model;
	}
	
	@PostMapping("/relatorios/boletos/pesquisa/avancada")
	public ModelAndView pesquisar(PesquisaAvancada pesquisa) {
		ModelAndView model = new ModelAndView("relatorios/boletos/resultadoPesquisa :: resultado");
		
		try {
			List<Boleto> boletos = service.pesquisar(pesquisa.getSacado(), pesquisa.getIdCategoria(), pesquisa.getSituacao(), 
					pesquisa.getDtEmissaoInicial(), pesquisa.getDtEmissaoFinal(), pesquisa.getDtVctoInicial(), pesquisa.getDtVctoFinal(), 
					pesquisa.getOrdenacao(), pesquisa.getCampoOrdenacao());
			model.addObject("boletos", boletos);
			
			if (boletos.isEmpty()) {
				model.addObject("exibirMensagem", true);
			}
			
		} catch (ValidacaoException e) {
			logger.error("Houve um erro ao pesquisar por integrantes", e);
			model.addObject("erro", e.getMessage());
		}
		
		return model;
	}
	
}
