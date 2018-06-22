import moment from 'moment';

export class DateUtil {

  constructor() {}

  adicionarMeses(data, qtd) {
    moment.locale('pt-br');
    return moment(data).add(qtd, 'months');
  }
  
  formatarData(data) {
    let dataFormatada = data;
    moment.locale('pt-br');

    if (data && data.year) {
      let mes = data.monthValue < 10 ? '0' + data.monthValue : data.monthValue;
      let dia = data.dayOfMonth < 10 ? '0' + data.dayOfMonth : data.dayOfMonth;
      dataFormatada = moment(data.year + '-' + mes + '-' + dia).format('DD/MM/YYYY');
    }

    return dataFormatada;
  }

  getAno(data) {
    moment.locale('pt-br');
    return moment(data).format('YYYY');
  }

  getMes(data) {
    moment.locale('pt-br');
    return moment(data).month() + 1;
  }

  getMesAbreviado(data) {
    moment.locale('pt-br');
    return moment(data).format('MMM');
  }

  getMeses() {
    return [
      {num: 1, nome: 'Janeiro'},
      {num: 2, nome: 'Fevereiro'},
      {num: 3, nome: 'Março'},
      {num: 4, nome: 'Abril'},
      {num: 5, nome: 'Maio'},
      {num: 6, nome: 'Junho'},
      {num: 7, nome: 'Julho'},
      {num: 8, nome: 'Agosto'},
      {num: 9, nome: 'Setembro'},
      {num: 10, nome: 'Outubro'},
      {num: 11, nome: 'Novembro'},
      {num: 12, nome: 'Dezembro'}
    ]
  }

  getMesPorExtenso(data) {
    moment.locale('pt-br');
    let num = moment(data).month() + 1;
    let meses = this.getMeses();
    let nome;

    for (let mes of meses) {
      if (mes.num == num) {
        nome = mes.nome;
        break;
      }
    }

    return nome;
  }

  getNomeMes(numMes) {
    let nome;
    let meses = this.getMeses();

    for (let mes of meses) {
      if (mes.num == numMes) {
        nome = mes.nome;
        break;
      }
    }

    return nome;
  }

  subtrairMeses(data, qtd) {
    moment.locale('pt-br');
    return moment(data).subtract(qtd, 'months');
  }

}
