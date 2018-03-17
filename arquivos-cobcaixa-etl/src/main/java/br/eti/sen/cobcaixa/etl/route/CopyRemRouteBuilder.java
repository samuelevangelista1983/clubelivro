package br.eti.sen.cobcaixa.etl.route;

public class CopyRemRouteBuilder extends AbstractCopyRouteBuilder {

	public CopyRemRouteBuilder(String src, String dest, int delay) {
		super(src, dest, delay);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute).to(destDirRoute);
	}

}
