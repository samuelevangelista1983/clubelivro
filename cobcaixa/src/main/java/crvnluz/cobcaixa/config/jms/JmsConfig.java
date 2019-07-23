package crvnluz.cobcaixa.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JmsConfig {

	@Value("${activemq.broker.url}")
	private String brokerUrl;
	@Value("${activemq.broker.username}")
	private String usuario = "admin";
	@Value("${activemq.broker.password}")
	private String senha = "admin";
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setPassword(usuario);
		connectionFactory.setUserName(senha);
		//connectionFactory.setWatchTopicAdvisories(false);
		connectionFactory.setClientID(clientIdPrefix);
		/*RedeliveryPolicy policy = new RedeliveryPolicy();
		policy.setMaximumRedeliveries(6); // tenta enviar até conseguir
		policy.setInitialRedeliveryDelay(120000); // espera 2 minutos inicialmente para tentar reenviar
		policy.setMaximumRedeliveryDelay(3600000); // espera no máximo 1 hora
		policy.setBackOffMultiplier(2.0); // multiplica por cinco o tempo de espera a cada nova tentativa de reenvio
		policy.setUseExponentialBackOff(true);
		connectionFactory.setRedeliveryPolicy(policy);*/
		return connectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		return new CachingConnectionFactory(connectionFactory());
	}
	
	@Bean
	public JmsTemplate getJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
	
}