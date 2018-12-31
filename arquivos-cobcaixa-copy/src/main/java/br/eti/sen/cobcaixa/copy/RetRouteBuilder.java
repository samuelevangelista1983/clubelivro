package br.eti.sen.cobcaixa.copy;

public class RetRouteBuilder extends AbstractRouteBuilder {

	public RetRouteBuilder(String src, String dest) {
		super(src, dest);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute)
			.process(new LogCopyProcessor())
			.to(destDirRoute);
	}

}
