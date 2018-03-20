import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {Router} from 'aurelia-router';
import moment from 'moment';
import {DialogService} from 'aurelia-dialog';

@inject(HttpClient, Router, DialogService)
export class Integrante {
  
  entidade = {};
  classificacoes = [];
  formasEntrega = [];
  idFormaEntrega = null;
  formasPgto = [];
  idFormaPgto = null;
  frequencias = [];
  enderecos = [];
  poderemoverendereco = false;
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
    if (param && param.idIntegrante) {
      this.carregando = true;
      this.http.get('/integrantes/' + param.idIntegrante)
        .then(data => {
          this.entidade = JSON.parse(data.response);
          this.entidade.documento = this.entidade.pessoa.documentos[0];
          this.enderecos = this.entidade.pessoa.enderecos

          for (let endereco of this.enderecos) {
            let cobranca = this.entidade.enderecoCobranca;
            let entrega = this.entidade.enderecoEntrega;

            if (cobranca.cep == endereco.cep && cobranca.logradouro == endereco.logradouro && cobranca.numero == endereco.numero
                && cobranca.bairro == endereco.bairro && cobranca.complemento == endereco.complemento 
                && cobranca.municipio == endereco.municipio && cobranca.uf == endereco.uf) {
                endereco.cobranca = true;
            }

            if (entrega.cep == endereco.cep && entrega.logradouro == endereco.logradouro && entrega.numero == endereco.numero
                && entrega.bairro == endereco.bairro && entrega.complemento == endereco.complemento 
                && entrega.municipio == endereco.municipio && entrega.uf == endereco.uf) {
                endereco.entrega = true;
            }

            this.poderemoverendereco = true;
          }

          let contatos = this.entidade.pessoa.contatos;
          let qtdTel = 0;
          let qtdCel = 0;
          let qtdMail = 0;

          for (let contato of contatos) {
            if (contato.idTipo == 1) {
              this.tels.push({id: contato.id, obs: contato.observacao, numero: contato.valor});
              qtdTel++;
            }

            if (contato.idTipo == 3) {
              this.cels.push({id: contato.id, obs: contato.observacao, numero: contato.valor, whatsapp: false});
              qtdCel++;
            }

            if (contato.idTipo == 5) {
              for (let cel of this.cels) {
                if (cel.numero == contato.valor) {
                  cel.whatsapp = true;
                }
              }
            }

            if (contato.idTipo == 4) {
              this.emails.push({id: contato.id, mail: contato.valor, obs: contato.observacao});
              qtdMail++;
            }
          }

          if (qtdTel > 0) {
            this.tels.splice(0, 1);
          }

          if (qtdCel > 0) {
            this.cels.splice(0, 1);
          }

          if (qtdMail > 0) {
            this.emails.splice(0, 1);
          }

          this.carregando = false;
        })
        .catch(error => {
          this.carregando = false;
          this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
        });
    }
  }

  attached() {
    this.carregando = true;
    this.http.get('configuracao/classificacoes').then(data => this.classificacoes = JSON.parse(data.response));
    this.http.get('configuracao/formasentrega').then(data => this.formasEntrega = JSON.parse(data.response));
    this.http.get('configuracao/formaspgto').then(data => this.formasPgto = JSON.parse(data.response));
    this.http.get('configuracao/frequencias').then(data => {
      this.frequencias = JSON.parse(data.response);
      this.carregando = false;
    });
    this.initConfig();
  }

  initConfig() {
    this.enderecos = [{
      idTipo: null, 
      cep: null, 
      logradouro: null, 
      numero: null, 
      complemento: null, 
      bairro: null, 
      municipio: null, 
      uf: null,
      obs: null,
      entrega: false,
      cobranca: false
    }];
    this.tels = [{id: null, numero: null, obs: null}];
    this.cels = [{id: null, numero: null, obs: null, whatsapp: false}];
    this.emails = [{id: null, mail: null, obs: null}];
    this.entidade = {
      id: null,
      dtCadastro: new Date().toLocaleDateString(),
      dtDesativacao: null,
      ativo: true,
      pessoa: {
        id: null,
        nome: null,
        nascimento: null,
        contatos: [],
        documentos: [],
        enderecos: []
      },
      documento: {id: null, idTipo: 1, valor: null},
      enderecoCobranca: {},
      enderecoEntrega: {},
      frequencia: {id: null, nome: null, freqMensal: null},
      formaEntrega: {id: null},
      formaPgtoPref: {id: null},
      diaPgtoPref: 1,
      classificacao: {id: null}
    };
  }

  onClickCancelar() {
    this.router.navigateToRoute('integrantes');
  }

  onClickSalvar() {
    this.carregando = true;
    this.configurarDatas();
    this.entidade.pessoa.contatos = [];
    this.entidade.pessoa.documentos = [];
    this.entidade.pessoa.enderecos = [];
    this.entidade.pessoa.documentos.push(this.entidade.documento);

    for (let tel of this.tels) {
      if (tel.numero != null && tel.numero != '') {
        this.entidade.pessoa.contatos.push({id: tel.id, idTipo: 1, observacao: tel.obs, valor: tel.numero});
      }
    }

    for (let cel of this.cels) {
      if (cel.numero != null && cel.numero != '') {
        this.entidade.pessoa.contatos.push({id: cel.id,idTipo: 3, observacao: cel.obs, valor: cel.numero});

        if (cel.whatsapp) {
          this.entidade.pessoa.contatos.push({id: cel.id, idTipo: 5, observacao: cel.obs, valor: cel.numero});
        }
      }
    }

    for (let email of this.emails) {
      if (email.mail != null && email.numero != '') {
        this.entidade.pessoa.contatos.push({id: email.id, idTipo: 4, observacao: email.obs, valor: email.mail});
      }
    }

    for (let endereco of this.enderecos) {
      this.entidade.pessoa.enderecos.push({
        id: endereco.id,
        idTipo: endereco.idTipo,
        cep: endereco.cep,
        logradouro: endereco.logradouro,
        numero: endereco.numero,
        complemento: endereco.complemento,
        bairro: endereco.bairro,
        municipio: endereco.municipio,
        uf: endereco.uf,
        observacao: endereco.obs
      });
      
      if (endereco.cobranca) {
        this.entidade.enderecoCobranca = {
          id: endereco.id,
          idTipo: endereco.idTipo,
          cep: endereco.cep,
          logradouro: endereco.logradouro,
          numero: endereco.numero,
          complemento: endereco.complemento,
          bairro: endereco.bairro,
          municipio: endereco.municipio,
          uf: endereco.uf,
          observacao: endereco.obs
        };
      }
      
      if (endereco.entrega) {
        this.entidade.enderecoEntrega = {
          id: endereco.id,
          idTipo: endereco.idTipo,
          cep: endereco.cep,
          logradouro: endereco.logradouro,
          numero: endereco.numero,
          complemento: endereco.complemento,
          bairro: endereco.bairro,
          municipio: endereco.municipio,
          uf: endereco.uf,
          observacao: endereco.obs
        };
      }
    }
    
    this.http.post('/integrantes', JSON.stringify(this.entidade))
      .then(data => {
        this.initConfig();
        this.carregando = false;
        this.router.navigateToRoute('integrantes');
        this.dialog.open({viewModel:'util/dialog', model:{tipo:'sucesso', msg:'Integrante do Clube do Livro incluído com sucesso'}});
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg: error.response}});
      });
  }

  configurarDatas() {
    moment.locale('pt-BR');

    if (this.entidade.dtCadastro.year) {
      let data = this.entidade.dtCadastro;
      let mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      let dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      this.entidade.dtCadastro = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
    }
    
    if (this.entidade.pessoa.nascimento != undefined && this.entidade.pessoa.nascimento.year) {
      let data = this.entidade.pessoa.nascimento;
      let mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      let dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      this.entidade.pessoa.nascimento = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');

    } else if (this.entidade.pessoa.nascimento != undefined && this.entidade.pessoa.nascimento == '') {
      this.entidade.pessoa.nascimento = null;
    }
  }
}
