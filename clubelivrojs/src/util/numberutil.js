import numeral from 'numeral';

numeral.register('locale', 'pt-br', {
  delimiters: {
      thousands: '.',
      decimal: ','
  },
  abbreviations: {
      thousand: 'mil',
      million: 'milhões',
      billion: 'b',
      trillion: 't'
  },
  ordinal: function (number) {
      return 'º';
  },
  currency: {
      symbol: 'R$ '
  }
});

numeral.locale('pt-br');

export default numeral;

export class NumberUtil {

  formatarMoeda(numero) {
    return numeral(numero).format('$0,0.00');
  }

  parseNumero(moeda) {
    console.log(moeda);
    let valor = moeda.splice(3);
    return valor.replace(',', '\\.');
  }

}
