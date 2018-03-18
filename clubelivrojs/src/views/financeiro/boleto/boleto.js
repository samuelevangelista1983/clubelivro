import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {Router} from 'aurelia-router';
import {DialogService} from 'aurelia-dialog';

@inject(HttpClient, Router, DialogService)
export class Boleto {
  
  boleto = {};
  carregando = false;

  constructor(httpClient, router, dialog) {
    this.router = router;
    this.dialog = dialog;
    this.http = httpClient;
    this.http.configure((x) => {
      x.withBaseUrl(environment.endpoint)
        x.withHeader('Content-Type', 'application/json');
      }
    );
  }
  
  activate(param) {
    console.log(param);
    if (param && param.idBoleto) {
      this.carregando = true;
      this.http.get('/financeiro/boletos/' + param.idBoleto)
        .then(data => {
          this.boleto = JSON.parse(data.response);
          this.carregando = false;
        })
        .catch(error => {
          this.carregando = false;
          this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
        });
    }
  }

  attached() {
    this.initConfig();
  }

  initConfig() {
    this.boleto = {};
  }

  onClickCancelar() {
    this.router.navigateToRoute('boletos');
  }

  onClickSalvar() {

  }

}
