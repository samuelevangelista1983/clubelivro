package crvnluz.cobcaixa.processar;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import crvnluz.cobcaixa.entidade.hsql.BoletoPendente;
import crvnluz.cobcaixa.repositorio.hsql.BoletoPendenteRepositorio;

@EnableScheduling
@Component
public class BoletoRemetente {
	
	private final Logger logger = LoggerFactory.getLogger(BoletoRemetente.class);
	
	@Autowired
	private BoletoPendenteRepositorio repositorio;
	private Pageable limite;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("${activemq.broker.topic.boletos}")
	private String topicBoletos;
	@Autowired
	private Gson gson;
	
	public BoletoRemetente() {
		limite = PageRequest.of(0, 300);
	}
	// Espera 10 minutos antes da primeira execução e as demais ocorrerão à cada 15 minutos
	@Scheduled(initialDelay = 600000, fixedDelay = 900000)
	//@Scheduled(fixedDelay = 10000)
	public void remeter() {
		logger.info("Processo de envio de boletos à fila de processamento iniciado");
		List<BoletoPendente> pendentes = repositorio.getBoletosPendentes(LocalDateTime.now(), limite);
		
		if (!pendentes.isEmpty()) {
			logger.info(String.format("Foram encontrados %s boletos pendentes de envio à fila de processamento", pendentes.size()));
			pendentes.stream().forEach(p -> {
				try {
					String json = gson.toJson(p.getBoleto());
					//System.out.println(json);
					jmsTemplate.convertAndSend(topicBoletos, json);
					p.setEnviado(true);
					
				} catch (Throwable t) {
					LocalDateTime momentoEnvio = p.getMomentoEnvio();
					p.setMomentoEnvio(momentoEnvio.plusHours(1));
					p.setErro(t.getCause().getMessage());
					p.setEnviado(false);
				}
			});
			repositorio.saveAll(pendentes);
			List<BoletoPendente> boletosEnviados = repositorio.getBoletosEnviados();
			repositorio.deleteInBatch(boletosEnviados);
			repositorio.flush();
			long enviados = pendentes.stream().filter(p -> p.getEnviado() == true).count();
			logger.info(String.format("Foram enviados %s boletos à fila de processamento", enviados));
			long naoEnviados = pendentes.size() - enviados;
			
			if (naoEnviados > 0) {
				logger.info(String.format("Não foram enviados %s boletos à fila de processamento", naoEnviados));
			}
			
		} else {
			logger.info("Não foram encontrados boletos pendentes de envio à fila de processamento");
		}
		
		logger.info("Processo de envio de boletos à fila de processamento finalizado");
	}
}
