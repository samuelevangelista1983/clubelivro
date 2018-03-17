import {inject} from 'aurelia-framework';
import {DialogController} from 'aurelia-dialog';

@inject(DialogController)
export class Alerta {
  
  mensagem;
  tipo;

  constructor(controller) {
    this.controller = controller;
  }

  activate (params) {
    this.tipo = params.tipo;
    this.mensagem = params.msg;
  }
}
