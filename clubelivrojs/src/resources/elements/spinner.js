import {bindable, bindingMode} from 'aurelia-framework';

export class Spinner {

  @bindable isLoading;
  @bindable({ defaultBindingMode: bindingMode.twoWay }) message;
  @bindable huge;

  constructor() {}

  isLoadingValueChanged(){
    console.log('teste');
  }
}

