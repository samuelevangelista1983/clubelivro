package br.eti.sen.cobcaixa.copy;

public class RemRouteBuilder extends AbstractRouteBuilder {

	public RemRouteBuilder(String src, String dest) {
		super(src, dest);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute)
			.process(new LogCopyProcessor())
			.to(destDirRoute);
	}

}
