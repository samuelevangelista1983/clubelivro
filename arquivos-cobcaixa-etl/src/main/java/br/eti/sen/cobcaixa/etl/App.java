package br.eti.sen.cobcaixa.etl;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.main.Main;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.sen.cobcaixa.etl.route.ArquivoRemRouteBuilder;
import br.eti.sen.cobcaixa.etl.route.CopyRemRouteBuilder;
import br.eti.sen.cobcaixa.etl.route.CopyRetRouteBuilder;

public class App {

	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	private static DataSource getDataSource(ResourceBundle bundle) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(bundle.getString("database.driver.class.name"));
		dataSource.setUsername(bundle.getString("database.username"));
		dataSource.setPassword(bundle.getString("database.password"));
		dataSource.setUrl(bundle.getString("database.connection.url"));
		return dataSource;
	}
	
	public static void main(String[] args) throws Exception {
		try {
			LOGGER.info("Lendo parametros do arquivo de configuracoes...");
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			String srcDirRem = bundle.getString("src.dir.rem");
			String srcDirRet = bundle.getString("src.dir.ret");
			String destDirRem = bundle.getString("dest.dir.rem");
			String destDirRet = bundle.getString("dest.dir.ret");
			int delayRem = Integer.parseInt(bundle.getString("delay.read.rem.file"));
			int delayRet = Integer.parseInt(bundle.getString("delay.read.ret.file"));
			
			LOGGER.info("Configurando objeto DataSource para conexao com banco de dados...");
			DataSource dataSource = getDataSource(bundle);
			LOGGER.info("Configurando o MessageIDRepository...");
			JdbcMessageIdRepository repository = new JdbcMessageIdRepository(dataSource, "messageIdRepository");
			SimpleRegistry registry = new SimpleRegistry();
			LOGGER.info("Registrando os objetos DataSource e MessageIDRepository...");
			registry.put("dataSource", dataSource);
			registry.put("jdbcMessageIdRepository", repository);
			
			LOGGER.info("Instanciando o contexto de integracao...");
			CamelContext context = new DefaultCamelContext(registry);
			context.addRoutes(new CopyRemRouteBuilder(srcDirRem, destDirRem, delayRem));
			context.addRoutes(new CopyRetRouteBuilder(srcDirRet, destDirRet, delayRet));
			context.addRoutes(new ArquivoRemRouteBuilder(destDirRem, 1000));
			
			Main main = new Main();
			main.getCamelContexts().add(context);
			main.run();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Ocorreu um erro ao executar o programa", ex);
		}
	}

}
