import moment from 'moment';

export class DateconverterValueConverter {

  toView(value, format='DD/MM/YYYY') {
    if (value != undefined) {
      moment.locale('pt-BR');

      if (value.year) {
        value = moment(value.year + '-' + value.monthValue + '-' + value.dayOfMonth).format('DD/MM/YYYY');

      } else if (value.length == 8 && value.search('/') == -1) {
        value = moment(value).format('DD/MM/YYYY');
      }
    }

    return value;
  }

  fromView(value) {
    if (value != undefined && value.length == 8 && value.search('/') == -1) {
      let dt = value.substr(0, 2);
      dt += '/';
      dt += value.substr(2, 2);
      dt += '/';
      dt += value.substr(4);
      value = dt;
    }

    return value;
  }
  
}

