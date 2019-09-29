function adicionarEmail() {
	var data = $('#formContatos').serialize();
	postAjax('/integrantes/contatos/email/adicionar', data, 'formContatos');
}

function adicionarEndereco() {
	var data = $('#formEnderecos').serialize();
	postAjax('/integrantes/enderecos/adicionar', data, 'formEnderecos');
}

function adicionarTelefoneCelular() {
	var data = $('#formContatos').serialize();
	postAjax('/integrantes/contatos/telefonecelular/adicionar', data, 'formContatos');
}

function adicionarTelefoneFixo() {
	var data = $('#formContatos').serialize();
	postAjax('/integrantes/contatos/telefonefixo/adicionar', data, 'formContatos');
}

function ativarOuDesativar(id, idTag) {
	var url = '/integrantes/ativar_desativar/' + id;
	getAjax(url, null, idTag);
}

function editar(id) {
	var url = '/integrantes/editar/' + id;
	getAjax(url, null, 'main');
}

function filtrar() {
	var data = 'nome=' + $('#nome').val() + '&idCategoria=' + $('#idCategoria').val();
	postAjax('/integrantes/pesquisa', data, 'resultadoPesquisa');
}

function incluir() {
	getAjax('/integrantes/adicionar', null, 'main');
}

function limparPesquisaAvancada() {
	$('#pesquisaAvancada')[0].reset();
}

function pesquisar() {
	var data = $('form[name=pesquisaAvancada]').serialize();
	postAjax('/integrantes/pesquisa/avancada', data, 'resultadoPesquisa');
	$('#filtroAvancado').modal('hide');
	limparPesquisaAvancada();
}

function pesquisarEndereco(idx) {
	var data = $('#formEnderecos').serialize();
	var url = '/integrantes/endereco/consultar/' + idx;
	postAjax(url, data, 'enderecos');
}

function removerEmail(idx) {
	var url = '/integrantes/contatos/email/remover/' + idx;
	var data = $('#formContatos').serialize();
	postAjax(url, data, 'formContatos');
}

function removerEndereco(idx) {
	var url = '/integrantes/enderecos/remover/' + idx;
	var data = $('#formEnderecos').serialize();
	postAjax(url, data, 'formEnderecos');
}

function removerTelefoneCelular(idx) {
	var url = '/integrantes/contatos/telefonecelular/remover/' + idx;
	var data = $('#formContatos').serialize();
	postAjax(url, data, 'formContatos');
}

function removerTelefoneFixo(idx) {
	var url = '/integrantes/contatos/telefonefixo/remover/' + idx;
	var data = $('#formContatos').serialize();
	postAjax(url, data, 'formContatos');
}

function salvarIntegrante() {
	var data = $('#formBasico, #formEnderecos, #formContatos, #formComplemento').serialize();
	postAjax('/integrantes/salvar', data, 'main');
}