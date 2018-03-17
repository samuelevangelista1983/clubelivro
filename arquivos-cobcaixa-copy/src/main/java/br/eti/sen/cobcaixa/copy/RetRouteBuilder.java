package br.eti.sen.cobcaixa.copy;

public class RetRouteBuilder extends AbstractRouteBuilder {

	public RetRouteBuilder(String src, String dest, int delay) {
		super(src, dest, delay);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute).to(destDirRoute);
	}

}
