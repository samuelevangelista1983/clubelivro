import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';

@inject(HttpClient, Router)
export class Integrantes {

  inadimplentes = [];
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

  onClickLimpar() {
    this.initConfig();
  }

  onClickPesquisar() {
    this.carregando = true;
    this.http.post('/inadimplentes/pesquisa', JSON.stringify(this.pesquisa))
      .then(data => {
        this.inadimplentes = JSON.parse(data.response);

        if (this.inadimplentes.length > 0) {
          this.mensagem = '';

        } else {
          this.mensagem = 'Não foram encontrados integrantes que satisfaçam a pesquisa';
        }
        this.carregando = false;
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }
}
