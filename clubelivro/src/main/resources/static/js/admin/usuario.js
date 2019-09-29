function salvarUsuario() {
	loadStart();
	$.ajax({
		type: 'post',
		url: '/usuario/salvar',
		cache: false,
		data: $('#formUsuario').serialize(),
		success: function (response) {
			loadStop();
			$(location).attr('href', '/index?usuario_alterado=true')
		},
		error: function(e) {
			$('#mensagemErro').html(e.responseJSON.message);
			$('#modalErro').modal('show');
			loadStop();
		}
	});
}