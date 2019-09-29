function pesquisar() {
	var data = 'anoInicial=' + $('#anoInicial').val() + '&mesInicial=' + $('#mesInicial').val() + 
				'&anoFinal=' + $('#anoFinal').val() + '&mesFinal=' + $('#mesFinal').val();
	postAjax('/relatorios/fluxoreceita/pesquisa', data, 'resultadoPesquisa');
}
