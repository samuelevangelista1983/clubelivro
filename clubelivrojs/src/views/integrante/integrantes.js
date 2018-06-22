import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';
import {DialogService} from 'aurelia-dialog';

@inject(HttpClient, Router, DialogService)
export class Integrantes {

  integrantes = [];
  categorias = [];
  formasEntrega = [];
  formasPgto = [];
  frequencias = [];
  situacoes = [];
  tipos = [];
  pesquisa = {};
  pesquisaAvancada = {};
  mensagem = '';
  carregando = false;

  constructor(httpClient, router, dialog) {
    this.router = router;
    this.dialog = dialog;
    this.http = httpClient;
    this.http.configure(x => {
      x.withBaseUrl(environment.endpoint)
      x.withHeader('Content-Type', 'application/json');
    });
  }

  attached(params) {
    this.carregando = true;
    this.situacoes = [
      {id:1, nome:'Ativo'},
      {id:0, nome:'Inativo'}
    ];
    this.tipos = [
      {id:0, nome:'Ascendente'},
      {id:1, nome:'Descendente'},
    ];
    this.http.get('configuracao/classificacoes').then(data => this.categorias = JSON.parse(data.response));
    this.http.get('configuracao/formasentrega').then(data => this.formasEntrega = JSON.parse(data.response));
    this.http.get('configuracao/formaspgto').then(data => this.formasPgto = JSON.parse(data.response));
    this.http.get('configuracao/frequencias').then(data => {
      this.frequencias = JSON.parse(data.response);
      this.carregando = false;
    });
  }

  initConfig() {
    this.pesquisa = {};
    this.pesquisaAvancada = {};
  }

  onClickAtivar(idx, id) {
    this.carregando = true;
    this.http.put('/integrantes/ativar', JSON.stringify(id))
      .then(data => {
        this.onClickPesquisar();
        this.carregando = false;
      })
      .catch(error => {
        alert(error.response);
        this.carregando = false;
      });
  }

  onClickCloseAlert() {
    this.mensagem = '';
  }

  onClickDesativar(idx, id) {
    this.carregando = true;
    this.http.put('/integrantes/desativar', JSON.stringify(id))
      .then(data => {
        this.onClickPesquisar();
        this.carregando = false;
      })
      .catch(error => {
        alert(error.response);
        this.carregando = false;
      });
  }

  onClickEditar(id) {
    this.router.navigateToRoute('integrante', {idIntegrante: id});
  }

  onClickIncluir() {
    this.router.navigateToRoute('integrante', {idIntegrante: null});
  }

  onClickLimpar() {
    this.initConfig();
  }

  onClickPesquisar() {
    this.carregando = true;
    this.http.post('/integrantes/pesquisa', JSON.stringify(this.pesquisa))
      .then(data => {
        this.integrantes = JSON.parse(data.response);

        if (this.integrantes.length > 0) {
          this.mensagem = '';

        } else {
          this.mensagem = 'Não foram encontrados integrantes que satisfaçam a pesquisa';
        }
        this.carregando = false;
      })
      .catch(error => {
        this.integrantes = [];
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }

  onClickPesquisarFiltroAvancado() {
    this.carregando = true;
    this.http.post('integrantes/pesquisa/avancada', JSON.stringify(this.pesquisaAvancada))
      .then(data => {
        this.integrantes = JSON.parse(data.response);

        if (this.integrantes.length > 0) {
          this.mensagem = '';

        } else {
          this.mensagem = 'Não foram encontrados integrantes que satisfaçam a pesquisa';
        }

        $('#filtroAvancado').modal('hide');
        this.initConfig();
        this.carregando = false;
      })
      .catch(error => {
        this.integrantes = [];
        $('#filtroAvancado').modal('hide');
        this.initConfig();
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }
}
