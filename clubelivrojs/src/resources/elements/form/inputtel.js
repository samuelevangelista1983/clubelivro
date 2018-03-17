import {bindable} from 'aurelia-framework';

export class Inputtel {

  @bindable tels = [];

  onClickAdd() {
    this.tels.push({id: null, numero: null, obs: null});
  }

  onClickDel(index) {
    this.tels.splice(index, 1);
  }
  
}
