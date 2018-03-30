import {customAttribute, inject} from 'aurelia-framework';
import $ from 'jquery';
import 'bootstrap';

@customAttribute('bootstrap-tooltip')
@inject(Element)
export class BootstraptooltipCustomAttribute {

  constructor(element) {
    this.element = element;
  }

  bind() {
    $(this.element).tooltip();
  }

  unbind() {
    $(this.element).tooltip('hide');
  }

  valueChanged(newValue, oldValue) {}
}

