import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';
import {DialogService} from 'aurelia-dialog';

@inject(HttpClient, Router, DialogService)
export class Boletos {

  boletos = [];
  campos = [];
  categorias = [];
  situacoes = [];
  tipos = [];
  pesquisa = {};
  pesquisaAvancada = {}
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
  
  attached() {
    this.carregando = true;
    this.http.get('configuracao/classificacoes')
      .then(data => {
        this.categorias = JSON.parse(data.response);
        this.carregando = false;
      });
    this.campos = [
      {id:0, nome:'Sacado'},
      {id:1, nome:'Número do boleto'},
      {id:2, nome:'Data de emissão'},
      {id:3, nome:'Data de vencimento'}
    ];
    this.situacoes = [
      {id:0, nome:'Aberto'},
      {id:1, nome:'Baixado'},
      {id:2, nome:'Baixado manualmente'},
      {id:3, nome:'Cancelado'},
      {id:4, nome:'Cancelado manualmente'},
      {id:5, nome:'Erro processamento'},
    ];
    this.tipos = [
      {id:0, nome:'Ascendente'},
      {id:1, nome:'Descendente'},
    ];
  }

  initConfig() {
    this.pesquisa = {};
    this.pesquisaAvancada = {};
  }

  onClickCloseAlert() {
    this.mensagem = '';
  }

  onClickEditar(id) {
    this.router.navigateToRoute('boleto', {idBoleto: id});
  }

  onClickIncluir() {
    this.router.navigateToRoute('boleto');
  }

  onClickLimpar() {
    this.initConfig();
  }

  onClickPesquisar() {
    this.carregando = true;
    this.http.post('/financeiro/boletos/pesquisa', JSON.stringify(this.pesquisa))
      .then(data => {
        this.boletos = JSON.parse(data.response);
        
        if (this.boletos.length > 0) {
          this.mensagem = '';
          
        } else {
          this.mensagem = 'Não foram encontrados boletos que satisfaçam a pesquisa';
        }

        this.carregando = false;
      })
      .catch(error => {
        this.boletos = [];
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  onClickPesquisarFiltroAvancado() {
    this.carregando = true;
    this.http.post('financeiro/boletos/pesquisa/avancada', JSON.stringify(this.pesquisaAvancada))
      .then(data => {
          this.boletos = JSON.parse(data.response);

          if (this.boletos.length > 0) {
            this.mensagem = '';

          } else {
            this.mensagem = 'Não foram encontrados boletos que satisfaçam a pesquisa';
          }

          $('#buscaAvancada').modal('hide');
          this.initConfig();
          this.carregando = false;
      })
      .catch(error => {
        this.boletos = [];
        $('#buscaAvancada').modal('hide');
        this.initConfig();
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }
}
