export class CepconverterValueConverter {

  toView(value) {
    if (value != undefined && value.length == 8 && value.search('\\.') == -1 && value.search('-') == -1) {
      let cep = value.substr(0, 2);
      cep += '.';
      cep += value.substr(2, 3);
      cep += '-';
      cep += value.substr(5);
      value = cep;
    }

    return value;
  }

  fromView(value) {
    if (value != undefined) {
      let cep = value.replace('\\.', '');
      cep = cep.replace('\\-', '');
      return cep;
    }
  }
}

