import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';

@inject(HttpClient, Router)
export class Integrantes {

  integrantes = [];
  categorias = [];
  pesquisa = {};
  mensagem = '';
  carregando = false;

  constructor(httpClient, router) {
    this.router = router;
    this.http = httpClient;
    this.http.configure(x => {
      x.withBaseUrl(environment.endpoint)
      x.withHeader('Content-Type', 'application/json');
    });
  }

  attached(params) {
    this.carregando = true;
    this.http.get('configuracao/classificacoes')
      .then(data => {
        this.categorias = JSON.parse(data.response);
        this.carregando = false;
      });
  }

  initConfig() {
    this.pesquisa = {};
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
        alert(error.response);
        this.carregando = false;
      });
  }
}
