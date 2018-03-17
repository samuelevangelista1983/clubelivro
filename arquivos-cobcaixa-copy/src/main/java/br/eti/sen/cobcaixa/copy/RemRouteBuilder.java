package br.eti.sen.cobcaixa.copy;

public class RemRouteBuilder extends AbstractRouteBuilder {

	public RemRouteBuilder(String src, String dest, int delay) {
		super(src, dest, delay);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute).to(destDirRoute);
	}

}
