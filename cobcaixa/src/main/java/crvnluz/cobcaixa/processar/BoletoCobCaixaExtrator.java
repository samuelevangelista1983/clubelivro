package crvnluz.cobcaixa.processar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import crvnluz.cobcaixa.entidade.access.BoletoCobCaixa;
import crvnluz.cobcaixa.entidade.hsql.Boleto;
import crvnluz.cobcaixa.entidade.hsql.BoletoPendente;
import crvnluz.cobcaixa.entidade.hsql.Sacado;
import crvnluz.cobcaixa.repositorio.access.BoletoCobCaixaRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.BoletoPendenteRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.BoletoRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.SacadoRepositorio;

@EnableScheduling
@Component
public class BoletoCobCaixaExtrator {

	private final Logger logger = LoggerFactory.getLogger(BoletoCobCaixaExtrator.class);
	
	@Autowired
	private BoletoCobCaixaRepositorio cobcaixaRepositorio;
	@Autowired
	private BoletoRepositorio boletoRepositorio;
	@Autowired
	private BoletoPendenteRepositorio boletoPendenteRepositorio;
	private Pageable limite;
	@Autowired
	private SacadoRepositorio sacadoRepositorio;
	
	public BoletoCobCaixaExtrator() {
		limite = PageRequest.of(0, 300);
	}
	
	private LocalDate calcularDataInicial(Integer situacao) {
		LocalDate dataInicial = boletoRepositorio.getDataUltimoBoleto(situacao);
		
		if (dataInicial == null) {
			dataInicial = LocalDate.of(2015, 1, 1);
		}
		
		return dataInicial;
	}
	
	private void salvar(BoletoCobCaixa boletoCobCaixa) {
		Boleto boleto = Boleto.converter(boletoCobCaixa);
		Sacado sacadoSalvo = sacadoRepositorio.findByNomeAndDocumento(boleto.getSacado().getNome(), boleto.getSacado().getDocumento());
		
		if (sacadoSalvo != null) {
			Sacado sacado = boleto.getSacado();
			sacado.setId(sacadoSalvo.getId());
			boleto.setSacado(sacado);
		}
		
		boleto.setSacado(sacadoRepositorio.save(boleto.getSacado()));
		boletoRepositorio.save(boleto);
		BoletoPendente pendente = new BoletoPendente();
		pendente.setBoleto(boleto);
		pendente.setEnviado(false);
		pendente.setMomentoEnvio(LocalDateTime.now());
		boletoPendenteRepositorio.save(pendente);
	}
	
	// Espera 5 minutos antes da primeira execução e as demais ocorrerão à cada 15 minutos
	@Scheduled(initialDelay = 300000, fixedDelay = 900000)
	//@Scheduled(fixedDelay = 10000)
	public void extrair() {
		logger.info("Processo de extração de boletos do programa CobCaixa iniciado");
		List<BoletoCobCaixa> boletosLiquidados = cobcaixaRepositorio.getBoletosLiquidados(calcularDataInicial(1), limite);
		
		if (!boletosLiquidados.isEmpty()) {
			logger.info(String.format("Foram encontrados %s boletos liquidados", boletosLiquidados.size()));
			boletosLiquidados.stream().forEach(b -> salvar(b));
			logger.info("Os boletos liquidados foram salvos com sucesso");
			
		} else {
			logger.info("Não foram encontrados boletos liquidados");
		}
		
		List<BoletoCobCaixa> boletosEmitidos = cobcaixaRepositorio.getBoletosEmitidos(calcularDataInicial(0), limite);
		
		if (!boletosEmitidos.isEmpty()) {
			logger.info(String.format("Foram encontrados %s boletos emitidos", boletosEmitidos.size()));
			boletosEmitidos.stream().forEach(b -> salvar(b));
			logger.info("Os boletos emitidos foram salvos com sucesso");
			
		} else {
			logger.info("Não foram encontrados boletos emitidos");
		}
		
		List<BoletoCobCaixa> boletosCancelados = cobcaixaRepositorio.getBoletosCancelados(calcularDataInicial(3), limite);
		
		if (!boletosCancelados.isEmpty()) {
			logger.info(String.format("Foram encontrados %s boletos cancelados", boletosCancelados.size()));
			boletosCancelados.stream().forEach(b -> salvar(b));
			logger.info("Os boletos cancelados foram salvos com sucesso");
			
		} else {
			logger.info("Não foram encontrados boletos cancelados");
		}
		
		boletoRepositorio.flush();
		boletoPendenteRepositorio.flush();
		logger.info("Processo de extração de boletos do programa CobCaixa finalizado");
	}
	
}
