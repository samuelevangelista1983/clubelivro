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
  incluir = false;
  integrantes = [];

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
    this.carregando = true;

    if (param && param.idBoleto) {
      this.http.get('/financeiro/boletos/' + param.idBoleto)
        .then(data => {
          this.boleto = JSON.parse(data.response);
          this.carregando = false;
        })
        .catch(error => {
          this.carregando = false;
          this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
        });

    } else {
      this.incluir = true;
      this.http.get('/integrantes')
        .then(data => {
          this.integrantes = JSON.parse(data.response);
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
    if (this.boleto.valorPagoStr != undefined) {
      this.boleto.valorCreditadoStr = this.boleto.valorPagoStr;

    } else {
      this.boleto.valorCreditadoStr = null;
    }
  }

  onBlurValorNominal() {
    this.boleto.valorNominalStr = this.numberutil.formatarMoeda(this.boleto.valorNominalStr);
  }

  onBlurValorPgto() {
    this.carregando = true;
    this.boleto.valorPagoStr = this.numberutil.formatarMoeda(this.boleto.valorPagoStr);
    this.calcularValorCreditado();
    this.http.get('/configuracao/formaspgto/1')
      .then(data => {
        let formaPgto = JSON.parse(data.response);
        this.boleto.valorTarifaStr = this.numberutil.formatarMoeda(formaPgto.custo);
        this.carregando = false;
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  onClickCancelar() {
    this.router.navigateToRoute('boletos');
  }

  onClickAtivar(id) {
    this.carregando = true;
    this.http.get('/financeiro/boletos/ativar/' + id)
      .then(data => {
        this.carregando = false;
        this.router.navigateToRoute('boletos');
        this.dialog.open({viewModel:'util/dialog', model:{tipo:'sucesso', msg:'Boleto reativado com sucesso'}});
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }

  onClickDesativar(id) {
    this.carregando = true;
    this.http.get('/financeiro/boletos/cancelar/' + id)
      .then(data => {
        this.carregando = false;
        this.router.navigateToRoute('boletos');
        this.dialog.open({viewModel:'util/dialog', model:{tipo:'sucesso', msg:'Boleto cancelado com sucesso'}});
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }

  onClickSalvar() {
    this.carregando = true;
    this.configurarDatas();

    if (this.boleto.valorPagoStr != undefined && this.boleto.valorPagoStr != '' && is.boleto.valorPagoStr == 'R$ 0,00') {
      this.boleto.situacao = 2;
    }

    if (this.boleto.valorPagoStr == undefined || this.boleto.valorPagoStr == '' || this.boleto.valorPagoStr == 'R$ 0,00') {
      this.boleto.situacao = 0;
    }

    if (this.boleto.sacado == '' || this.boleto.sacado.pessoa == undefined || this.boleto.sacado.pessoa.nome == '') {
      this.boleto.sacado = null;
    }

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
    if (this.boleto.emissao != undefined && this.boleto.emissao != '') {
      this.boleto.emissao = this.dateutil.formatarData(this.boleto.emissao);
    }

    if (this.boleto.vcto != undefined && this.boleto.vcto != '') {
      this.boleto.vcto = this.dateutil.formatarData(this.boleto.vcto);
    }

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
