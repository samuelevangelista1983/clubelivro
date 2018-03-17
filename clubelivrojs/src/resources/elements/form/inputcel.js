import {bindable} from 'aurelia-framework';

export class Inputcel {

  @bindable cels = [];

  onClickAdd() {
    this.cels.push({id:null, numero: null, obs: null, whatsapp: false});
  }

  onClickDel(index) {
    this.cels.splice(index, 1);
  }

}
