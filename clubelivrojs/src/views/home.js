import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';
import {DialogService} from 'aurelia-dialog';
import {Chart} from 'chart.js';

Chart.defaults.global.tooltips.callbacks.label = function(tooltipItem, data) {
  let dataLabel = data.datasets[tooltipItem.datasetIndex].label + ': ';
  let valor = 'R$ ';

  let idx = tooltipItem.yLabel.toString().search('\\.');
  let centavos = idx > -1 ? tooltipItem.yLabel.toString().substr(idx + 1, 2) : 0;
  let inteiro = idx > -1 ? tooltipItem.yLabel.toString().substr(0, idx) : tooltipItem.yLabel.toString();
  
  if (parseInt(inteiro) > 999) {
    let label = inteiro.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    valor += label;

  } else {
    valor += inteiro;
  }

  valor += ',';

  if (centavos > 0) {
    if (centavos < 10) {
      valor += '0';
    }

    valor += centavos;

  } else {
    valor += '00';
  }

  return dataLabel + valor;
};

Chart.scaleService.updateScaleDefaults('linear', {
  ticks: {
      callback: function (value, index, values) {
        let valor = 'R$ ';

        if (parseInt(value) > 999) {
          valor += value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");

        } else {
          valor += value;
        }

        valor += ',00';
        return valor;
      }
  }
});

@inject(HttpClient, Router, DialogService)
export class Home {

  painelIntegrante = null;
  painelInadimplencia = null;
  painelReceita = null;
  carregando = false;

  constructor(httpClient, router, dialog) {
    this.router = router;
    this.dialog = dialog;
    this.http = httpClient;
    this.http.configure(x => {
      x.withBaseUrl(environment.endpoint)
      x.withHeader('Content-Type', 'application/json');
    });
  }

  attached() {
    this.carregando = true;
    this.http.post('/painelIntegrante/pesquisa')
      .then(data => {
        this.painelIntegrante = JSON.parse(data.response);
        this.atualizarGraficosIntegrantes();
        this.carregando = false;
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  onClickReceitas() {
    this.http.post('/receita/pesquisa')
      .then(data => {
        this.painelReceita = JSON.parse(data.response);
        this.atualizarGraficosReceita();
        this.carregando = false;
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  onClickInadimplencia() {
    this.http.get('/inadimplencia/pesquisa')
      .then(data => {
        this.painelInadimplencia = JSON.parse(data.response);
        this.atualizarGraficosInadimplencia();
        this.carregando = false;
      })
      .catch(error => {
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  atualizarGraficosIntegrantes() {
    let chartAtivos = new Chart(this.graficoAtivos, {
      type: 'doughnut',
      data: {
        labels: ['Ativos', 'Inativos'],
        datasets : [{
            label: 'Valor previsto',
            data: [this.painelIntegrante.ativos, this.painelIntegrante.inativos],
            backgroundColor: ['rgba(77, 166, 253, 0.85)','rgba(215, 40, 40, 0.9)']
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Integrantes ativos x Integrantes inativos'
        }
      }
    });
    let chartCategoria = new Chart(this.graficoCategorias, {
      type: 'doughnut',
      data: {
        labels: ['Estudo', 'Romance', 'Estudo e romance', 'Estudo e romance alternado'],
        datasets : [{
            data: [
              this.painelIntegrante.estudo, 
              this.painelIntegrante.romance, 
              this.painelIntegrante.estudoRomance, 
              this.painelIntegrante.estudoRomanceAlternado],
            backgroundColor: [
              'rgba(77, 166, 253, 0.85)',
              'rgba(215, 40, 40, 0.9)',
              'rgba(215, 191, 44, 0.9)',
              'rgba(154, 116, 237, 0.9)'
            ]
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Integrantes x Categorias'
        }
      }
    });
    /*let chartFrequencia = new Chart(this.graficoFrequencias, {
      type: 'doughnut',
      data: {
        labels: ['Mensal', 'Bimestral'],
        datasets : [{
            data: [
              this.painelIntegrante.mensal, 
              this.painelIntegrante.bimestral],
            backgroundColor: [
              'rgba(77, 166, 253, 0.85)',
              'rgba(215, 40, 40, 0.9)'
            ]
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Integrantes x Freqüências'
        }
      }
    });
    let chartEntrega = new Chart(this.graficoEntregas, {
      type: 'doughnut',
      data: {
        labels: ['Correios', 'Presencial'],
        datasets : [{
            data: [
              this.painelIntegrante.correios, 
              this.painelIntegrante.presencial],
            backgroundColor: [
              'rgba(77, 166, 253, 0.85)',
              'rgba(215, 40, 40, 0.9)'
            ]
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Integrantes x Formas de entrega'
        }
      }
    });
    let chartPagamento = new Chart(this.graficoPagamento, {
      type: 'doughnut',
      data: {
        labels: ['Boleto', 'Cartão débito', 'Cartão crédito', 'Dinheiro', 'Cheque'],
        datasets : [{
            data: [
              this.painelIntegrante.boleto, 
              this.painelIntegrante.debito, 
              this.painelIntegrante.credito, 
              this.painelIntegrante.dinheiro,
              this.painelIntegrante.cheque],
            backgroundColor: [
              'rgba(77, 166, 253, 0.85)',
              'rgba(215, 40, 40, 0.9)',
              'rgba(215, 191, 44, 0.9)',
              'rgba(154, 116, 237, 0.9)',
              'rgba(255, 0, 190, 0.9)',
            ]
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Integrantes x Formas de pagamento'
        }
      }
    });*/
  }

  atualizarGraficosInadimplencia() {
    let chartInadimplenciaMensal = new Chart(this.graficoInadimplenciaMensal, {
      type: 'line',
      data: {
        labels: this.painelInadimplencia.inadimplenciaMensal.labels,
        datasets : [{
            label: 'Valor devido',
            data: this.painelInadimplencia.inadimplenciaMensal.valores,
            backgroundColor: 'rgba(215, 40, 40, 0.9)'
          }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Inadimplência mensal'
        }
      }
    });
    
    let chartInadimplenciaCategoria = new Chart(this.graficoInadimplenciaCategoria, {
      type: 'horizontalBar',
      data: {
        labels: this.painelInadimplencia.inadimplenciaCategoria.categorias,
        datasets: [{
          label: 'Valor devido',
          data: this.painelInadimplencia.inadimplenciaCategoria.valores,
          backgroundColor: 'rgba(215, 40, 40, 0.9)'
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Inadimplência x Categorias'
        }
      }
    });
  }

  atualizarGraficosReceita() {
    let chartReceitaCategoria = new Chart(this.graficoReceitaCategoria, {
      type: 'bar',
      data: {
        labels: this.painelReceita.categorias,
        datasets: [{
          label: 'Valor recebido',
          data: this.painelReceita.valores,
          backgroundColor: 'rgba(6, 204, 6, 0.85)'
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 20,
          text: 'Receita x Categorias'
        }
      }
    });
  }
}
