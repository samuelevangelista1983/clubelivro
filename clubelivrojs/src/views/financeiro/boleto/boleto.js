import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {Router} from 'aurelia-router';
import {DialogService} from 'aurelia-dialog';
import {DateUtil} from 'util/dateutil';
import {NumberUtil} from 'util/numberutil';

@inject(HttpClient, Router, DialogService, DateUtil, NumberUtil)
export class Boleto {
  
  boleto = {};
  carregando = false;

  constructor(httpClient, router, dialog, dateutil, numberutil) {
    this.router = router;
    this.dialog = dialog;
    this.dateutil = dateutil;
    this.numberutil = numberutil;
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

  calcularValorCreditado() {
    let valorPago = 0;

    if (this.boleto.valorPagoStr != undefined) {
      valorPago = this.numberutil.parseNumero(this.boleto.valorPagoStr);
      let valorTarifa = 0;

      if (this.boleto.valorTarifaStr != undefined) {
        valorTarifa = this.numberutil.parseNumero(this.boleto.valorTarifaStr);
      }

      let valorCredito = valorPago - valorTarifa;
      this.boleto.valorCreditadoStr = this.numberutil.formatarMoeda(valorCredito);

    } else {
      this.boleto.valorCreditadoStr = null;
    }
  }

  onBlurValorPgto() {
    this.boleto.valorPagoStr = this.numberutil.formatarMoeda(this.boleto.valorPagoStr);
    this.calcularValorCreditado();
  }

  onBlurValorTarifa() {
    this.boleto.valorTarifaStr = this.numberutil.formatarMoeda(this.boleto.valorTarifaStr);
    this.calcularValorCreditado();
  }

  onClickCancelar() {
    this.router.navigateToRoute('boletos');
  }

  onClickSalvar() {
    this.carregando = true;
    this.configurarDatas();
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
    this.boleto.emissao = this.dateutil.formatarData(this.boleto.emissao);
    this.boleto.vcto = this.dateutil.formatarData(this.boleto.vcto);
    
    if (this.boleto.pgto != undefined && this.boleto.pgto != '') {
      this.boleto.pgto = this.dateutil.formatarData(this.boleto.pgto);
      
    } else {
      this.boleto.pgto = null;
    }

    if (this.boleto.efetivacaoCredito != undefined && this.boleto.efetivacaoCredito != '') {
      this.boleto.efetivacaoCredito = this.dateutil.formatarData(this.boleto.efetivacaoCredito);
      
    } else {
      this.boleto.efetivacaoCredito = null;
    }
  }
}
