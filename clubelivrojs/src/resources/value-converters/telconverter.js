export class TelconverterValueConverter {
  toView(value) {
    if (value != undefined && value.length == 10 && value.search('-') == -1 && value.search('\s') == -1) {
      let numero = value.substr(0, 2);
      numero += ' ';
      numero += value.substr(2, 4);
      numero += '-';
      numero += value.substr(6);
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

