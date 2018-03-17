import {bindable} from 'aurelia-framework';

export class Inputemail {

  @bindable emails = [];

  onClickAdd() {
    this.emails.push({id: null, mail: null, obs: null});
  }

  onClickDel(index) {
    this.emails.splice(index, 1);
  }

}
