export class CelconverterValueConverter {
  toView(value) {
    if (value != undefined && value.length == 11 && value.search('-') == -1 && value.search('\s') == -1) {
      let numero = value.substr(0, 2);
      numero += ' ';
      numero += value.substr(2, 5);
      numero += '-';
      numero += value.substr(7);
      value = numero;
    }

    return value;
  }

  fromView(value) {
    if (value != undefined) {
      let numero = value.replace('\\-', '');
      numero = numero.replace('\s', '');
      return numero;
    }
  }
}

