package br.eti.sen.cobcaixa.copy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	private final Logger logger = LoggerFactory.getLogger(App.class);
	
	private String srcDirRem;
	private String srcDirRet;
	private String destDirRem;
	private String destDirRet;
	
	private void configurar(File arquivo) throws IOException {
		try {
			List<String> linhas = Files.readAllLines(arquivo.toPath());
			
			for (String linha : linhas) {
				if (!linha.contains("=")) {
					ConfiguracaoException configuracaoException = new ConfiguracaoException("Para definir o valor de uma configuração deve ser utilizado o separado \"=\"");
					logger.error("Erro de sintaxe na definição das configurações", configuracaoException);
					throw configuracaoException;
				}
				
				String[] configuracao = linha.split("=");
				
				switch (configuracao[0]) {
					case "diretorio.origem.arquivo.remessa":
						srcDirRem = configuracao[1];
						break;
					case "diretorio.destino.arquivo.remessa":
						destDirRem = configuracao[1];
						break;
					case "diretorio.origem.arquivo.retorno":
						srcDirRet = configuracao[1];
						break;
					case "diretorio.destino.arquivo.retorno":
						destDirRet = configuracao[1];
						break;
					default:
						logger.info(String.format("Chave de configuração %s inválida", configuracao[0]));
				}
			}
			
			validarConfiguracao();
			
		} catch (IOException ioException) {
			logger.error("Houve um erro ao ler o conteúdo do arquivo de configuração informado", ioException);
			throw ioException;
		}
	}
	
	private void executar() throws Throwable {
		try {
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new RemRouteBuilder(srcDirRem, destDirRem));
			context.addRoutes(new RetRouteBuilder(srcDirRet, destDirRet));
			Main main = new Main();
			main.getCamelContexts().add(context);
			main.run();
			
		} catch (Throwable throwable) {
			logger.error("Falha ao copiar os arquivos de remessa e/ou de retorno", throwable);
			throw throwable;
		}
	}
	
	private void validarConfiguracao() {
		validarDiretorioOrigem(srcDirRem);
		validarDiretorioDestino(destDirRem);
		validarDiretorioOrigem(srcDirRet);
		validarDiretorioDestino(destDirRet);
	}
	
	private void validarDiretorioDestino(String dir) {
		if (dir == null || "".equals(dir.trim())) {
			ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("A chave de configuração %s não foi informada", dir));
			logger.error("Erro na definição das configurações", configuracaoException);
			throw configuracaoException;
		}
		
		File diretorio = new File(dir);
		
		if (diretorio.exists()) {
			if (!diretorio.canRead()) {
				ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("No momento não é permitida a leitura do diretório %s", dir));
				logger.error("Erro na definição das configurações", configuracaoException);
				throw configuracaoException;
			}
			
			if (diretorio.isFile()) {
				ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("%s não é o caminho de um diretório", dir));
				logger.error("Erro na definição das configurações", configuracaoException);
				throw configuracaoException;
			}
		}
	}
	
	private void validarDiretorioOrigem(String dir) {
		if (dir == null || "".equals(dir.trim())) {
			ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("A chave de configuração %s não foi informada", dir));
			logger.error("Erro na definição das configurações", configuracaoException);
			throw configuracaoException;
		}
		
		File diretorio = new File(dir);
		
		if (!diretorio.exists()) {
			ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("O diretório %s não existe", dir));
			logger.error("Erro na definição das configurações", configuracaoException);
			throw configuracaoException;
		}
		
		if (!diretorio.canRead()) {
			ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("No momento não é permitida a leitura do diretório %s", dir));
			logger.error("Erro na definição das configurações", configuracaoException);
			throw configuracaoException;
		}
		
		if (diretorio.isFile()) {
			ConfiguracaoException configuracaoException = new ConfiguracaoException(String.format("%s não é o caminho de um diretório", dir));
			logger.error("Erro na definição das configurações", configuracaoException);
			throw configuracaoException;
		}
	}
	
	public static void main(String[] args) throws Throwable {
		if (args.length != 1) {
			System.out.println("Para utilizar este programa é necessário informar o caminho de um arquivo de configuração com as seguintes configurações:");
			System.out.println();
			System.out.println("diretorio.origem.arquivo.remessa=CAMINHO DIRETORIO DE ORIGEM ARQUIVOS REMESSA");
			System.out.println("diretorio.destino.arquivo.remessa=CAMINHO DIRETORIO DE DESTINO ARQUIVOS REMESSA");
			System.out.println("diretorio.origem.arquivo.retorno=CAMINHO DIRETORIO DE ORIGEM ARQUIVOS RETORNO");
			System.out.println("diretorio.destino.arquivo.retorno=CAMINHO DIRETORIO DE DESTINO ARQUIVOS RETORNO");
			System.out.println();
			System.out.println("Exemplo:");
			System.out.println("java -jar arquivos-cobycaixa.jar d:/caixa/arquivoConfiguracao.txt");
			System.exit(0);
		}
		
		File arquivo = new File(args[0]);
		
		if (!arquivo.exists()) {
			throw new IllegalArgumentException("Arquivo de configuração informado não existe");
		}
		
		if (arquivo.isDirectory()) {
			throw new IllegalArgumentException("Foi informado o caminho para um diretório e não para um arquivo de configuração");
		}
		
		if (!arquivo.canRead()) {
			throw new IllegalArgumentException("No momento não é permitida a leitura do arquivo de configuração informado");
		}
		
		App app = new App();
		app.configurar(arquivo);
		app.executar();
	}
	
}
