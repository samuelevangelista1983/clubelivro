function loadStart() {
	$('#spinner').show();
}

function loadStop() {
	$('#spinner').hide();
}

function ajaxRequest(method, url, data, idTag) {
	loadStart();
	$.ajax({
		type: method,
		url: url,
		cache: false,
		data: data,
		success: function (response) {
			$('#' + idTag).replaceWith(response);
			loadStop();
		},
		error: function(e) {
			$('#mensagemErro').html(e.responseJSON.message);
			$('#modalErro').modal('show');
			loadStop();
		}
	});
}

function getAjax(url, data, idTag) {
	ajaxRequest('get', url, data, idTag);
}

function postAjax(url, data, idTag) {
	ajaxRequest('post', url, data, idTag);
}
