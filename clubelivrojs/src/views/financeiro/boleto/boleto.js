import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {Router} from 'aurelia-router';
import {DialogService} from 'aurelia-dialog';
import moment from 'moment';

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
    this.carregando = true;
    console.log(this.boleto.emissao);
    //this.configurarDatas();
    this.boleto.situacao = 2;
    this.http.post('/financeiro/boletos', JSON.stringify(this.boleto))
      .then(data => {
        this.initConfig();
        this.carregando = false;
        this.router.navigateToRoute('boletos');
        this.dialog.open({viewModel:'util/dialog', model:{tipo:'sucesso', msg:'Boleto salvo com sucesso'}});
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }

  configurarDatas() {
    moment.locale('pt-BR');
    let data = this.boleto.emissao;
    let mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
    let dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
    this.boleto.emissao = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
    console.log(this.boleto.emissao);
    data = this.boleto.vcto;
    mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
    dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
    this.boleto.vcto = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');

    if (this.boleto.pgto != undefined && this.boleto.pgto != '') {
      data = this.boleto.pgto;
      mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      this.boleto.pgto = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
      
    } else {
      this.boleto.pgto = null;
    }

    if (this.boleto.efetivacaoCredito != undefined && this.boleto.efetivacaoCredito != '') {
      data = this.boleto.efetivacaoCredito;
      mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      this.boleto.efetivacaoCredito = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
      
    } else {
      this.boleto.efetivacaoCredito = null;
    }
  }
}
