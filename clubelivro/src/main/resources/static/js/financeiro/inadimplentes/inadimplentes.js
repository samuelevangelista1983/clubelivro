function pesquisar() {
	var data = '&idCategoria=' + $('#idCategoria').val();
	postAjax('/relatorios/inadimplentes/pesquisa', data, 'resultadoPesquisa');
}