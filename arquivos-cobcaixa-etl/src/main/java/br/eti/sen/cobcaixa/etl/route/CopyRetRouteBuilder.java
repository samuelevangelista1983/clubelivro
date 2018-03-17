package br.eti.sen.cobcaixa.etl.route;

public class CopyRetRouteBuilder extends AbstractCopyRouteBuilder {

	public CopyRetRouteBuilder(String src, String dest, int delay) {
		super(src, dest, delay);
	}

	@Override
	public void configure() throws Exception {
		from(srcDirRoute).to(destDirRoute);
	}

}
