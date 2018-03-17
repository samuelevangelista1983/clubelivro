import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';

@inject(HttpClient, Router)
export class Boletos {

  boletos = [];
  categorias = [];
  situacoes = [];
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
  
  attached() {
    this.carregando = true;
    this.http.get('configuracao/classificacoes')
      .then(data => {
        this.categorias = JSON.parse(data.response);
        this.carregando = false;
      });
    this.situacoes = [
      {id:0, nome:'Aberto'},
      {id:1, nome:'Baixado'},
      {id:2, nome:'Baixado manualmente'}
    ];
  }

  initConfig() {
    this.pesquisa = {};
  }

  onClickCloseAlert() {
    this.mensagem = '';
  }

  onClickEditar(id) {
    this.router.navigateToRoute('boleto', {idBoleto: id});
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
        alert(error.response);
        this.carregando = false;
      });
  }
}
