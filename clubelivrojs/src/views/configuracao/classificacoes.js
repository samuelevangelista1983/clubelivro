import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';

@inject(HttpClient)
export class Classificacoes {
  
  classificacoes = [];

  constructor(httpClient) {
    this.http = httpClient;
    this.http.configure(x => {
      x.withBaseUrl('http://localhost:8080/');
    });
  }

  attached() {
    this.http.get('configuracao/classificacoes')
      .then(data => {
        this.classificacoes = JSON.parse(data.response);
      });
  }
}
