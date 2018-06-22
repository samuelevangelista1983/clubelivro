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
			.choice()
				.when(header("errorMsg").isNull())
					.to("direct:splitBody")
				.otherwise()
					.multicast()
						.parallelProcessing()
							.to("direct:tratamentoExcecao", "direct:splitBody");
		
		from("direct:tratamentoExcecao")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				String errorMsg = in.getHeader("errorMsg", String.class);
				in.setBody(errorMsg);
			})
			.to(failRoute);
		
		from("direct:splitBody")
			.split(body())
				.to("direct:processarBoleto");
		
		from("direct:processarBoleto")
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
					.to("direct:adicionarBoleto")
				.otherwise()
					.process()
					.exchange(ex -> {
						Message in = ex.getIn();
						in.setHeader("boleto", in.getBody(Boleto.class));
					})
					.to("direct:adicionarSacado");
		
		from("direct:consultarSacado")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				in.setBody(Boleto.getConsultaIdSacado(in.getBody(Boleto.class)));
			})
			.to("jdbc:dataSource");
		
		from("direct:adicionarBoleto")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				in.setBody(Boleto.getComandoInserirBoleto(in.getBody(Boleto.class)));
			})
			.to("jdbc:dataSource");
		
		from("direct:adicionarSacado")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				in.setBody(Boleto.getComandoInserirPessoa(boleto));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				in.setBody(Boleto.getConsultaIdPessoa(boleto));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				List<Map<String, Object>> map = in.getBody(List.class);
				Long idPessoa = (Long) map.get(0).get("id");
				in.setHeader("idPessoa", idPessoa);
			})
			.to("direct:adicionarDocumento");
		
		from("direct:adicionarDocumento")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				Long idPessoa = in.getHeader("idPessoa", Long.class);
				in.setBody(Boleto.getComandoInserirDocumento(boleto, idPessoa));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Long idPessoa = in.getHeader("idPessoa", Long.class);
				in.setBody(Boleto.getConsultaIdDocumento(idPessoa));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				List<Map<String, Object>> map = in.getBody(List.class);
				Long idDocumento = (Long) map.get(0).get("id");
				in.setHeader("idDocumento", idDocumento);
			})
			.to("direct:adicionarEndereco");
		
		from("direct:adicionarEndereco")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				Long idPessoa = in.getHeader("idPessoa", Long.class);
				in.setBody(Boleto.getComandoInserirEndereco(boleto, idPessoa));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Long idPessoa = in.getHeader("idPessoa", Long.class);
				in.setBody(Boleto.getConsultaIdEndereco(idPessoa));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				List<Map<String, Object>> map = in.getBody(List.class);
				Long idEndereco = (Long) map.get(0).get("id");
				in.setHeader("idEndereco", idEndereco);
				in.setBody(in.getHeader("boleto", Boleto.class));
			})
			.multicast()
				.to("direct:adicionarEmail", "direct:adicionarTelefoneCelular", "direct:adicionarTelefoneFixo", "direct:adicionarIntegrante");
		
		from("direct:adicionarEmail")
			.choice()
				.when(bodyAs(Boleto.class).method("getEmailSacado").isInstanceOf(String.class))
					.process()
					.exchange(ex -> {
						Message in = ex.getIn();
						Boleto boleto = in.getHeader("boleto", Boleto.class);
						Long idPessoa = in.getHeader("idPessoa", Long.class);
						in.setBody(Boleto.getComandoInserirEmail(boleto, idPessoa));
					})
					.to("jdbc:dataSource");
		
		from("direct:adicionarTelefoneCelular")
			.choice()
				.when(bodyAs(Boleto.class).method("getCelularSacado").isNotNull())
					.process()
					.exchange(ex -> {
						Message in = ex.getIn();
						Boleto boleto = in.getHeader("boleto", Boleto.class);
						Long idPessoa = in.getHeader("idPessoa", Long.class);
						in.setBody(Boleto.getComandoInserirTelefoneCelular(boleto, idPessoa));
					})
					.to("jdbc:dataSource");

		from("direct:adicionarTelefoneFixo")
			.choice()
				.when(bodyAs(Boleto.class).method("getTelefoneSacado").isNotNull())
					.process()
					.exchange(ex -> {
						Message in = ex.getIn();
						Boleto boleto = in.getHeader("boleto", Boleto.class);
						Long idPessoa = in.getHeader("idPessoa", Long.class);
						in.setBody(Boleto.getComandoInserirTelefoneFixo(boleto, idPessoa));
					})
					.to("jdbc:dataSource");
		
		from("direct:adicionarIntegrante")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				Long idPessoa = in.getHeader("idPessoa", Long.class);
				Long idDocumento = in.getHeader("idDocumento", Long.class);
				Long idEndereco = in.getHeader("idEndereco", Long.class);
				in.setBody(Boleto.getComandoInserirIntegrante(boleto, idPessoa, idDocumento, idEndereco));
			})
			.to("jdbc:dataSource")
			.process()
			.exchange(ex -> {
				Message in = ex.getIn();
				Boleto boleto = in.getHeader("boleto", Boleto.class);
				in.setBody(boleto);
			})
			.to("direct:processarBoleto");
	}

}
