/**
 * @author Caio Silveira - 09/01/2018
 **/

import {inject,ViewCompiler,ViewResources,Container,ViewSlot,createOverrideContext} from 'aurelia-framework';

@inject(ViewCompiler, ViewResources, Container)
export class ViewCreator {
  constructor(viewCompiler, resources, container) {
    this.viewCompiler = viewCompiler;
    this.resources = resources;
    this.container = container;
  }

  insert(containerElement, html, viewModel) {
    let viewCreator = this.viewCompiler.compile(html);
    let view = viewCreator.create(this.container);
    let anchorIsContainer = true;
    let viewSlot = new ViewSlot(containerElement, anchorIsContainer);
    viewSlot.add(view);
    view.bind(viewModel, createOverrideContext(viewModel));
    return () => {
      viewSlot.remove(view);
      view.unbind();
    };
  }
}
