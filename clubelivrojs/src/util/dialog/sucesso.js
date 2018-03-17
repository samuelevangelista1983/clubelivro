import {inject} from 'aurelia-framework';
import {DialogController} from 'aurelia-dialog';

@inject(DialogController)
export class Sucesso {
  
  mensagem;

  constructor(controller) {
    this.controller = controller;
  }

  activate (msg) {
    this.mensagem = msg;
  }
}
