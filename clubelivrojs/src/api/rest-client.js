import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import environment from 'environment';

@inject(HttpClient)
export class RestClient {
  
  http = null;

  constructor(httpClient) {
    this.http = this.http = httpClient;
    this.http.configure((x) => {
        x.withBaseUrl(environment.endpoint)
        x.withHeader('Content-Type', 'application/json');
      }
    );
  }

  getRequest(url) {
    console.log('entrou no request');
    console.log(url);
    return new Promise((resolve, reject) => {
      this.http.get(url)
        .then(data => {data.JSON.parse(data.response)})
    });
  }

  postRequest(url, object) {
    return new Promise((resolve, reject) => {
      this.http.post(url, JSON.stringify(object))
        .then(data => {data.JSON.parse(data.response)})
    });
  }

}
