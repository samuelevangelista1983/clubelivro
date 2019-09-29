package crvnluz.cobcaixa.processar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import crvnluz.cobcaixa.entidade.access.BoletoCobCaixa;
import crvnluz.cobcaixa.entidade.hsql.Boleto;
import crvnluz.cobcaixa.entidade.hsql.BoletoPendente;
import crvnluz.cobcaixa.entidade.hsql.DiaProcessado;
import crvnluz.cobcaixa.entidade.hsql.Sacado;
import crvnluz.cobcaixa.repositorio.access.BoletoCobCaixaRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.BoletoPendenteRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.BoletoRepositorio;
import crvnluz.cobcaixa.repositorio.hsql.DiaProcessadoRepositorio;
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
	@Autowired
	private DiaProcessadoRepositorio diaProcessadoRepositorio;
	private LocalDate dataBoletosLiquidados;
	private LocalDate dataBoletosEmitidos;
	private LocalDate dataBoletosCancelados;
	@Autowired
	private SacadoRepositorio sacadoRepositorio;
	
	private void atualizarData(LocalDate data, Integer situacao) {
		DiaProcessado diaProcessado = diaProcessadoRepositorio.findByStatus(situacao);
		
		if (diaProcessado != null) {
			diaProcessado.setData(data);
			
		} else {
			diaProcessado = new DiaProcessado();
			diaProcessado.setStatus(situacao);
			diaProcessado.setData(data);
		}
		
		diaProcessadoRepositorio.save(diaProcessado);
	}
	
	private LocalDate calcularDataInicial(Integer situacao) {
		DiaProcessado diaProcessado = diaProcessadoRepositorio.findByStatus(situacao);
		LocalDate data = null;
		
		if (diaProcessado != null && diaProcessado.getData() != null) {
			data = diaProcessado.getData().plusDays(1);
			
			if (data.isAfter(LocalDate.now())) {
				data = LocalDate.now();
			}
			
		} else {
			data = LocalDate.of(2015, 1, 1);
		}
		
		return data;
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
	
	@Scheduled(initialDelayString = "${extrator.initial.delay}", fixedDelayString = "${extrator.fixed.delay}")
	public void extrair() {
		logger.info("Processo de extração de boletos do programa CobCaixa iniciado");
		dataBoletosLiquidados = calcularDataInicial(1);
		
		if (!dataBoletosLiquidados.isAfter(LocalDate.now())) {
			List<BoletoCobCaixa> boletosLiquidados = cobcaixaRepositorio.getBoletosLiquidados(dataBoletosLiquidados);
			
			if (!boletosLiquidados.isEmpty()) {
				logger.info(String.format("Foram encontrados %s boletos liquidados na data %s", boletosLiquidados.size(), dataBoletosLiquidados));
				boletosLiquidados.stream().forEach(b -> salvar(b));
				logger.info("Os boletos liquidados foram salvos com sucesso");
				
			} else {
				logger.info(String.format("Não foram encontrados boletos liquidados na data %s", dataBoletosLiquidados));
			}
			
			atualizarData(dataBoletosEmitidos, 0);
		}
		
		dataBoletosEmitidos = calcularDataInicial(0);
		
		
		if (!dataBoletosEmitidos.isAfter(LocalDate.now())) {
			List<BoletoCobCaixa> boletosEmitidos = cobcaixaRepositorio.getBoletosEmitidos(dataBoletosEmitidos);
			
			if (!boletosEmitidos.isEmpty()) {
				logger.info(String.format("Foram encontrados %s boletos emitidos na data %s", boletosEmitidos.size(), dataBoletosEmitidos));
				boletosEmitidos.stream().forEach(b -> salvar(b));
				logger.info("Os boletos emitidos foram salvos com sucesso");
				
			} else {
				logger.info(String.format("Não foram encontrados boletos emitidos na data %s", dataBoletosEmitidos));
			}
			
			atualizarData(dataBoletosLiquidados, 1);
		}
		
		dataBoletosCancelados = calcularDataInicial(3);
		
		if (!dataBoletosCancelados.isAfter(LocalDate.now())) {
			List<BoletoCobCaixa> boletosCancelados = cobcaixaRepositorio.getBoletosCancelados(dataBoletosCancelados);
			
			if (!boletosCancelados.isEmpty()) {
				logger.info(String.format("Foram encontrados %s boletos cancelados na data %s", boletosCancelados.size(), dataBoletosCancelados));
				boletosCancelados.stream().forEach(b -> salvar(b));
				logger.info("Os boletos cancelados foram salvos com sucesso");
				
			} else {
				logger.info(String.format("Não foram encontrados boletos cancelados na data %s", dataBoletosCancelados));
			}
			
			atualizarData(dataBoletosCancelados, 3);
		}
		
		diaProcessadoRepositorio.flush();
		boletoRepositorio.flush();
		boletoPendenteRepositorio.flush();
		logger.info("Processo de extração de boletos do programa CobCaixa finalizado");
	}
	
}
