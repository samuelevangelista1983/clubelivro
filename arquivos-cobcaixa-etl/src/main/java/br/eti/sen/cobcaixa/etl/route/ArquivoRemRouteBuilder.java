package br.eti.sen.cobcaixa.etl.route;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.eti.sen.cobcaixa.etl.entidade.Boleto;
import br.eti.sen.cobcaixa.etl.processor.ArquivoRemProcessor;

public class ArquivoRemRouteBuilder extends AbstractRouteBuilder {
	
	public ArquivoRemRouteBuilder(String dir, int delay) {
		super(dir, delay);
	}
	
	// MÉTODOS PÚBLICOS
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {
		from(route)
			.process(new ArquivoRemProcessor())
			.split(body())
				.enrich("direct:consultarSacado", new AggregationStrategy() {
					
					@Override
					public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
						List<Map<String, Object>> maps = newExchange.getIn().getBody(List.class);
						Message in = oldExchange.getIn();
						Boleto boleto = in.getBody(Boleto.class);
						
						if (!maps.isEmpty()) {
							Map<String, Object> map = maps.get(0);
							Long idSacado = map.get("id") != null ? Long.parseLong(map.get("id").toString()) : null;
							boleto.setStatus(0);
							boleto.setIdSacado(idSacado);
							
						} else {
							boleto.setStatus(1);
						}
						
						in.setBody(boleto);
						return oldExchange;
					}
				})
				.choice()
					.when(bodyAs(Boleto.class).method("getStatus").isEqualTo(0))
						.process()
						.exchange(ex -> {
							Message in = ex.getIn();
							in.setBody(Boleto.getComandoInserirBoleto(in.getBody(Boleto.class)));
						})
						.to("jdbc:dataSource")
					.otherwise()
						.marshal().json()
						.to(failRoute);
		
		from("direct:consultarSacado")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				in.setBody(Boleto.getConsultaIdSacado(in.getBody(Boleto.class)));
			})
			.to("jdbc:dataSource");
	}

}
