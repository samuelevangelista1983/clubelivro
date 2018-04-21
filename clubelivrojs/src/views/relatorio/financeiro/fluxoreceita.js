import {inject} from 'aurelia-framework';
import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';
import environment from 'environment';
import {DialogService} from 'aurelia-dialog';
import {Chart} from 'chart.js';
import {DateUtil} from 'util/dateutil';

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

@inject(HttpClient, Router, DialogService, DateUtil)
export class FluxoReceita {
  
  meses = [];
  fluxoReceita = null;
  periodo = null;
  pesquisa = {};
  carregando = false;
  mensagem = '';

  constructor(httpClient, router, dialog, dateutil) {
    this.router = router;
    this.dialog = dialog;
    this.dateutil = dateutil;
    this.http = httpClient;
    this.http.configure(x => {
      x.withBaseUrl(environment.endpoint)
      x.withHeader('Content-Type', 'application/json');
    });
  }

  attached() {
    this.meses = this.dateutil.getMeses();
    this.initConfig();
    this.onClickPesquisar();
  }

  definirPeriodo() {
    this.periodo = 'Período analisado - ' + this.dateutil.getNomeMes(this.pesquisa.mesInicio) + ' de ' 
                + this.pesquisa.anoInicio + ' até ' + this.dateutil.getNomeMes(this.pesquisa.mesFim) 
                + ' de ' + this.pesquisa.anoFim;
  }

  initConfig() {
    let hoje = new Date();
    let inicio = this.dateutil.subtrairMeses(hoje, 3);
    let fim = this.dateutil.adicionarMeses(hoje, 3);
    this.pesquisa.mesInicio = this.dateutil.getMes(inicio);
    this.pesquisa.anoInicio = this.dateutil.getAno(inicio);
    this.pesquisa.mesFim = this.dateutil.getMes(fim);
    this.pesquisa.anoFim = this.dateutil.getAno(fim);
  }

  onClickLimpar() {
    this.initConfig();
  }

  onClickPesquisar() {
    this.carregando = true;
    this.http.post('/fluxoreceita/pesquisa', JSON.stringify(this.pesquisa))
      .then(data => {
        this.fluxoReceita = JSON.parse(data.response);

        if (this.fluxoReceita.labels.length > 0) {
          this.mensagem = '';
          this.atualizarGrafico();

        } else {
          this.mensagem = 'Não foram encontradas receitas que satisfaçam a pesquisa';
        }
        
        this.definirPeriodo();
        $('#filtro').modal('hide');
        this.carregando = false;
      })
      .catch(error => {
        $('#filtro').modal('hide');
        this.carregando = false;
        this.dialog.open({viewModel:'util/dialog', model:{tipo: 'erro', msg:error.response}});
      });
  }

  atualizarGrafico() {
    let chart = new Chart(this.grafico, {
      type: 'line',
      data: {
        labels: this.fluxoReceita.labels,
        datasets : [{
            label: 'Valor previsto',
            data: this.fluxoReceita.valoresPrevistos,
            borderWidth: 3,
            borderColor: 'rgba(77, 166, 253, 0.85)',
            backgroundColor: 'transparent'
          },
          {
            label: 'Valor realizado',
            data: this.fluxoReceita.valoresRealizados,
            borderWidth: 3,
            borderColor: 'rgba(6, 204, 6, 0.85)',
            backgroundColor: 'transparent'
          }]
      },
      options: {
        elements: {
          line: {
            tension: 0
          }
        },
        title: {
          display: true,
          fontSize: 20,
          text: 'Receitas previstas x Receitas realizadas'
        },
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero:true
            }
          }]
        }
      }
    });
  }

}
