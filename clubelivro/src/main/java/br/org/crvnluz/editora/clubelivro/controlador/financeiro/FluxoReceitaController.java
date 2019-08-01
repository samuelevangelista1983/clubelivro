package br.org.crvnluz.editora.clubelivro.controlador.financeiro;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.crvnluz.editora.clubelivro.entidade.financeiro.FluxoReceita;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.FluxoReceitaDAO;

//@RestController
//@RequestMapping("/fluxoreceita")
public class FluxoReceitaController extends BaseController {
	/*
	@Autowired
	private FluxoReceitaDAO dao;
	
	// MÉTODOS PÚBLICOS
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/pesquisa")
	public ResponseEntity getFluxoReceita(@RequestBody String request) {
		ResponseEntity response;
		
		try {
			Map<String, Object> map = new ObjectMapper().readValue(request, HashMap.class);
			int mesInicio = map.get("mesInicio") != null ? Integer.parseInt(map.get("mesInicio").toString()) : 0;
			
			if (mesInicio == 0) {
				throw new ValidacaoException("O mês inicial deve ser informado");
			}
			
			int anoInicio = map.get("anoInicio") != null ? Integer.parseInt(map.get("anoInicio").toString()) : 0;
			
			if (anoInicio == 0) {
				throw new ValidacaoException("O ano inicial deve ser informado");
			}
			
			int mesFim = map.get("mesFim") != null ? Integer.parseInt(map.get("mesFim").toString()) : 0;
			
			if (mesFim == 0) {
				throw new ValidacaoException("O mês final deve ser informado");
			}
			
			int anoFim = map.get("anoFim") != null ? Integer.parseInt(map.get("anoFim").toString()) : 0;
			
			if (anoFim == 0) {
				throw new ValidacaoException("O ano final deve ser informado");
			}
			
			
			LocalDate dtInicial = LocalDate.of(anoInicio, mesInicio, 1);
			LocalDate dtFinal = null;
			
			try {
				dtFinal = LocalDate.of(anoFim, mesFim, 31);
				
			} catch (DateTimeException ex0) {
				try {
					dtFinal = LocalDate.of(anoFim, mesFim, 30);
					
				} catch (DateTimeException ex1) {
					try {
						dtFinal = LocalDate.of(anoFim, mesFim, 29);
						
					} catch (DateTimeException ex2) {
						dtFinal = LocalDate.of(anoFim, mesFim, 28);
					}
				}
			}
			
			if (dtFinal.isBefore(dtInicial)) {
				throw new ValidacaoException("O período final não pode ser anterior ao período inicial");
			}
			
			List<Object[]> dados = dao.getFluxoReceita(dtInicial, dtFinal);
			FluxoReceita fluxoReceita = new FluxoReceita();
			
			if (!dados.isEmpty()) {
				fluxoReceita.preencherValores(dados);
			}
			
			response = new ResponseEntity(fluxoReceita, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	*/
}
