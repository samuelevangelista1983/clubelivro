import {inject} from 'aurelia-framework';
import 'bootstrap';

export class App {

  constructor(api) {
    this.api = api;
  }

  // O framework executa este método inicialmente, informando um RouterConfiguration e um Router
  configureRouter(config, router) {
    this.router = router;
    config.title = 'Clube do Livro';
    /* As rotas da aplicação são definidas pelo objeto RouterConfiguration utilizando um map para armazenar os padrões de rota.
       Um padrão de rota deve ter pelo menos os atributos router e moduleId.
     */
    config.map([
      /*{route:['', 'home'], name:'home', moduleId:'views/home', nav:true, title:'Painel de controle'},*/
      {route:['', 'integrantes'], name:'integrantes', moduleId:'views/integrante/integrantes', nav:true, title:'Integrantes'},
      {route:'integrante', name:'integrante', moduleId:'views/integrante/integrante', nav:false},
      {route:['boletos'], name:'boletos', moduleId:'views/financeiro/boleto/boletos', nav:true, title:'Boletos'},
      {route:'boleto', name:'boleto', moduleId:'views/financeiro/boleto/boleto', nav:false},/*,
      {route:'classificacoes', name:'classificacoes', moduleId:'views/configuracao/classificacoes'}*/
    ]);
    this.router = router;
  }
}
