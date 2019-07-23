package br.org.crvnluz.editora.clubelivro.integracao.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.integracao.entidade.IntegranteProcessado;
import br.org.crvnluz.editora.clubelivro.integracao.repositorio.IntegranteProcesadoRepositorio;

@EnableScheduling
@Component
public class CadastroPessoaJob {
	
	private final Logger logger = LoggerFactory.getLogger(CadastroPessoaJob.class);
	
	@Autowired
	private IntegranteProcesadoRepositorio processadoRepositorio;
	private Pageable limite;
	@Autowired
	private CadastroPessoaIntegrador cadastroPessoa;
	
	public CadastroPessoaJob() {
		limite = PageRequest.of(0, 1000);
	}
	
	// Espera 30 minutos antes da primeira execução e as demais ocorrerão à cada 1 hora
	@Scheduled(initialDelay = 1800000, fixedDelay = 3600000)
	//@Scheduled(fixedDelay = 10000)
	@Transactional
	public void executar() {
		List<IntegranteProcessado> processados = processadoRepositorio.getIntegranteProcessados(200, LocalDateTime.now(), limite);
		List<IntegranteProcessado> enviados = new ArrayList<>(processados.size());
		
		for (IntegranteProcessado processado: processados) {
			try {
				Integrante integrante = processado.getIntegrante();
				cadastroPessoa.atualizarCadastroPessoa(integrante);
				enviados.add(processado);
				
			} catch (Throwable throwable) {
				logger.error("Falha ao enviar os dados do integrante ao cadastro de pessoas", throwable);
				int tentativas = processado.getTentativas() + 1;
				
				if (tentativas < 200) {
					processado.setTentativas(tentativas);
					processado.setProximaTentativa(LocalDateTime.now().plusHours(1));
					processado.setMsgErro(throwable.getMessage());
					processadoRepositorio.save(processado);
					
				} else {
					processadoRepositorio.delete(processado);
				}
			}
		}
		
		if (!enviados.isEmpty()) {
			processadoRepositorio.deleteInBatch(enviados);
		}
	}
	
}
