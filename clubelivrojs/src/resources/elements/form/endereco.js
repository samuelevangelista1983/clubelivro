import {bindable, inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';
import {DialogService} from 'aurelia-dialog';

@inject(HttpClient, DialogService)
export class Endereco {

  @bindable enderecos = [];
  tipos = [];
  ufs = [];
  carregando = false;
  
  constructor(httpClient, dialog) {
    this.dialog = dialog;
    this.http = httpClient;
    this.http.configure((x) => {
        x.withBaseUrl(environment.endpoint)
        x.withHeader('Content-Type', 'application/json');
      }
    );
  }

  attached() {
    this.http.get('configuracao/tiposendereco')
      .then(data => {
        this.tipos = JSON.parse(data.response);
      }
    );
    this.ufs = ['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 
              'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 
              'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'];
  }

  onClickAdd() {
    this.enderecos.push({
      id: null,
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
    });
  }

  onClickDel(index) {
    this.enderecos.splice(index, 1);
  }

  onClickPesquisarEndereco(idx) {
    if (this.enderecos[idx].cep) {
      this.carregando = true;
      let cep = this.enderecos[idx].cep;
      cep = cep.replace(/[\.-]/g,"");

      this.http.get('endereco/consulta/' + cep)
        .then(data => {
          let endereco = JSON.parse(data.response);
          this.enderecos[idx].logradouro = endereco.logradouro;
          this.enderecos[idx].bairro = endereco.bairro;
          this.enderecos[idx].municipio = endereco.municipio;
          this.enderecos[idx].uf = endereco.uf;
          this.carregando = false;
        })
        .catch(error => {
          this.carregando = false;
          this.dialog.open({viewModel:'util/dialog', model:{tipo:'info', msg:'Não foi encontrado um endereço relacionado ao CEP informado'}});
        });

    } else {
      this.dialog.open({viewModel:'util/dialog', model:{tipo:'alerta', msg:'É preciso informar um CEP'}});
    }
  }

}
