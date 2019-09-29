function detalhar(id) {
	var url = '/relatorios/boletos/detalhar/' + id;
	getAjax(url, null, 'main');
}

function filtrar() {
	var data = 'nome=' + $('#nome').val() + '&numeroBoleto=' + $('#numeroBoleto').val();
	postAjax('/relatorios/boletos/pesquisa', data, 'resultadoPesquisa');
}

function limparPesquisaAvancada() {
	$('#pesquisaAvancada')[0].reset();
}

function pesquisar() {
	var data = $('form[name=pesquisaAvancada]').serialize();
	postAjax('/relatorios/boletos/pesquisa/avancada', data, 'resultadoPesquisa');
	$('#buscaAvancada').modal('hide');
	limparPesquisaAvancada();
}
