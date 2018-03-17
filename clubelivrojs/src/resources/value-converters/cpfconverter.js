export class CpfconverterValueConverter {
  toView(value) {
    if (value != undefined && value.length == 11 && value.search('-') == -1 && value.search('\\.') == -1) {
      let cpf = value.substr(0, 3);
      cpf += '.';
      cpf += value.substr(3, 3);
      cpf += '.';
      cpf += value.substr(6, 3);
      cpf += '-';
      cpf += value.substr(9);
      value = cpf;
    }

    return value;
  }

  fromView(value) {
    if (value != undefined) {
      let cpf = value.replace('\\.', '');
      cpf = cpf.replace('\\-', '');
      return cpf;
    }
  }
}

