/**
 * @author Caio Silveira - 12/01/2018
 **/

import {inject, bindable} from 'aurelia-framework';

export class DynamicFormCustomElement {
    @bindable class = 'classe';
    @bindable message = 'novo';
    @bindable model = '';

    constructor(){    

    }

    attached() {
        
    }

    activate(params){
        this.class = params.class;
        this.message = params.message;
        this.model = params.model;
    }

    modelChanged(newValue, oldValue) {
      console.log(newValue);
    }
}
