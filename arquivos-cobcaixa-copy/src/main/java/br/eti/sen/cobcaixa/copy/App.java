package br.eti.sen.cobcaixa.copy;

import java.util.ResourceBundle;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

public class App {

	public static void main(String[] args) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		String srcDirRem = bundle.getString("src.dir.rem");
		String srcDirRet = bundle.getString("src.dir.ret");
		String destDirRem = bundle.getString("dest.dir.rem");
		String destDirRet = bundle.getString("dest.dir.ret");
		int delayRem = Integer.parseInt(bundle.getString("delay.read.rem.file"));
		int delayRet = Integer.parseInt(bundle.getString("delay.read.ret.file"));
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RemRouteBuilder(srcDirRem, destDirRem, delayRem));
		context.addRoutes(new RetRouteBuilder(srcDirRet, destDirRet, delayRet));
		Main main = new Main();
		main.getCamelContexts().add(context);
		main.run();
	}

}
