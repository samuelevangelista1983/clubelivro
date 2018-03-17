import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {Router} from 'aurelia-router';

@inject(HttpClient, Router)
export class Boleto {
  
  boleto = {};
  mensagem = '';
  carregando = false;

  constructor(httpClient, router) {
    this.router = router;
    this.http = httpClient;
    this.http.configure((x) => {
      x.withBaseUrl(environment.endpoint)
        x.withHeader('Content-Type', 'application/json');
      }
    );
  }
  
  active() {

  }

  attached() {

  }

  initConfig() {

  }

  onClickCancelar() {
    this.router.navigateToRoute('boletos');
  }

  onClickSalvar() {

  }

}
