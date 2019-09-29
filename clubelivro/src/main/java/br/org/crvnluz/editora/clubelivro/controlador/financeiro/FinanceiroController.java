package br.org.crvnluz.editora.clubelivro.controlador.financeiro;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.org.crvnluz.editora.clubelivro.controlador.Parametro;
import br.org.crvnluz.editora.clubelivro.controlador.financeiro.fluxoreceita.FluxoReceita;
import br.org.crvnluz.editora.clubelivro.controlador.financeiro.inadimplencia.Inadimplente;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.CategoriaService;
import br.org.crvnluz.editora.clubelivro.servico.financeiro.FinanceiroService;

@Controller
public class FinanceiroController {
	
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private FinanceiroService service;
	
	private List<Object[]> preencherValores(List<Object[]> dados) {
		List<Object[]> valores = new ArrayList<>(dados.size());
		
		if (!dados.isEmpty()) {
			int ano = 0;
			
			for (Object[] fluxo: dados) {
				int anoFluxo = Integer.parseInt(fluxo[0].toString());
				String label = LocalDate.of(2018, Integer.parseInt(fluxo[1].toString()), 1).format(DateTimeFormatter.ofPattern("MMM", new Locale("pt", "BR")));
				label = new StringBuilder().append(Character.toUpperCase(label.charAt(0))).append(label.substring(1)).toString();
				
				if (ano != anoFluxo) {
					ano = anoFluxo;
					label = new StringBuilder(label).append(" - ").append(ano).toString();
				}
				
				Object[] valor = new Object[3];
				valor[0] = label;
				valor[1] = Double.parseDouble(fluxo[2].toString());
				valor[2] = Double.parseDouble(fluxo[3].toString());
				valores.add(valor);
			}
		}
		
		return valores;
	}
	
	private List<Parametro> getMeses() {
		List<Parametro> meses = new ArrayList<>(12);
		meses.add(new Parametro(1l, "Janeiro"));
		meses.add(new Parametro(2l, "Fevereiro"));
		meses.add(new Parametro(3l, "Março"));
		meses.add(new Parametro(4l, "Abril"));
		meses.add(new Parametro(5l, "Maio"));
		meses.add(new Parametro(6l, "Junho"));
		meses.add(new Parametro(7l, "Julho"));
		meses.add(new Parametro(8l, "Agosto"));
		meses.add(new Parametro(9l, "Setembro"));
		meses.add(new Parametro(10l, "Outubro"));
		meses.add(new Parametro(11l, "Novembro"));
		meses.add(new Parametro(12l, "Dezembro"));
		return meses;
	}
	
	@GetMapping(value = {"/relatorios/fluxoreceita", "/relatorios/fluxoreceita/index"})
	public ModelAndView getFluxoReceita() {
		ModelAndView model = new ModelAndView("relatorios/fluxoreceita/index");
		model.addObject("meses", getMeses());
		model.addObject("anoMinimo", service.getMenorAno());
		model.addObject("anoMaximo", service.getMaiorAno());
		return model;
	}
	
	@PostMapping("/relatorios/fluxoreceita/pesquisa")
	public ModelAndView getFluxoReceita(@RequestParam("anoInicial") Integer anoInicial, @RequestParam("mesInicial") Integer mesInicial,
			@RequestParam("anoFinal") Integer anoFinal, @RequestParam("mesFinal") Integer mesFinal) {
		
		List<Object[]> valores = new ArrayList<>();
		
		if (anoInicial != null && mesInicial != null && anoFinal != null && mesFinal != null) {
			LocalDate inicio = LocalDate.of(anoInicial, mesInicial, 1);
			LocalDate fim = null;
			
			try {
				fim = LocalDate.of(anoFinal, mesFinal, 31);
				
			} catch (DateTimeException ex0) {
				try {
					fim = LocalDate.of(anoFinal, mesFinal, 30);
					
				} catch (DateTimeException ex1) {
					try {
						fim = LocalDate.of(anoFinal, mesFinal, 29);
						
					} catch (DateTimeException ex2) {
						fim = LocalDate.of(anoFinal, mesFinal, 28);
					}
				}
			}
			
			try {
				valores = service.getFluxoReceita(inicio, fim);
				
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ModelAndView model = new ModelAndView("relatorios/fluxoreceita/resultadoPesquisa :: resultado");
		
		if (!valores.isEmpty()) {
			List<Parametro> meses = getMeses();
			List<FluxoReceita> fluxos = new ArrayList<>(valores.size());
			valores.stream().forEach(valor -> {
				FluxoReceita fluxo = new FluxoReceita();
				fluxo.setAno(Integer.parseInt(valor[0].toString()));
				fluxo.setPrevisto(Double.parseDouble(valor[2].toString()));
				fluxo.setRealizado(Double.parseDouble(valor[3].toString()));
				String mes = meses.stream().filter(m -> m.getId().equals(Long.parseLong(valor[1].toString()))).findFirst().get().getNome();
				fluxo.setMes(mes);
				fluxos.add(fluxo);
			});
			
			model.addObject("valores", preencherValores(valores));
			model.addObject("fluxoReceita", fluxos);
			
		} else {
			model.addObject("exibirMensagem", true);
		}
		
		return model;
	}
	
	@GetMapping(value = {"/relatorios/inadimplentes", "/relatorios/inadimplentes/index"})
	public ModelAndView iniciarInadimplentes() {
		ModelAndView model = new ModelAndView("relatorios/inadimplentes/index");
		model.addObject("categorias", categoriaService.listarTodas());
		return model;
	}
	
	@PostMapping("/relatorios/inadimplentes/pesquisa")
	public ModelAndView pesquisarInadimplentes(@RequestParam("idCategoria") Long idCategoria) {
		List<Inadimplente> inadimplentes = new ArrayList<>();
		
		try {
			inadimplentes = service.pesquisarInadimplentes(idCategoria);
			
		} catch (Throwable t) {
			// TODDO Auto-generated catch block
			t.printStackTrace();
		}
		
		ModelAndView model = new ModelAndView("relatorios/inadimplentes/resultadoPesquisa :: resultado");
		model.addObject("categorias", categoriaService.listarTodas());
		model.addObject("inadimplentes", inadimplentes);
		
		if (inadimplentes.isEmpty()) {
			model.addObject("exibirMensagem", true);
		}
		
		return model;
	}
}
