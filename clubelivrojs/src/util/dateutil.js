import moment from 'moment';

export class DateUtil {

  constructor() {}

  formatarData(data) {
    let dataFormatada = data;
    moment.locale('pt-BR');

    if (data.year) {
      let mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      let dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      dataFormatada = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
    }

    return dataFormatada;
  }

}
