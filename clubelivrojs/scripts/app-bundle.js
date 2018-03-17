define('app',['exports', 'aurelia-framework', 'bootstrap'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.App = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var App = exports.App = function () {
    function App(api) {
      _classCallCheck(this, App);

      this.api = api;
    }

    App.prototype.configureRouter = function configureRouter(config, router) {
      this.router = router;
      config.title = 'Clube do Livro';

      config.map([{ route: ['', 'integrantes'], name: 'integrantes', moduleId: 'views/integrante/integrantes', nav: true, title: 'Integrantes' }, { route: 'integrante', name: 'integrante', moduleId: 'views/integrante/integrante', nav: false }, { route: ['boletos'], name: 'boletos', moduleId: 'views/financeiro/boleto/boletos', nav: true, title: 'Boletos' }, { route: 'boleto', name: 'boleto', moduleId: 'views/financeiro/boleto/boleto', nav: false }]);
      this.router = router;
    };

    return App;
  }();
});
define('environment',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    debug: true,
    testing: true,
    endpoint: 'http://localhost:8080/'
  };
});
define('main',['exports', './environment', 'aurelia-pal'], function (exports, _environment, _aureliaPal) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function configure(aurelia) {
    aurelia.use.standardConfiguration().feature('resources').plugin(_aureliaPal.PLATFORM.moduleName('aurelia-dialog'), function (config) {
      config.useDefaults();
      config.settings.lock = true;
      config.settings.keyboard = true;
    }).globalResources('bootstrap/css/bootstrap.css');

    if (_environment2.default.debug) {
      aurelia.use.developmentLogging();
    }

    if (_environment2.default.testing) {
      aurelia.use.plugin('aurelia-testing');
    }

    aurelia.start().then(function () {
      return aurelia.setRoot();
    });
  }
});
define('api/rest-client',['exports', 'aurelia-framework', 'aurelia-http-client', 'environment'], function (exports, _aureliaFramework, _aureliaHttpClient, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.RestClient = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var RestClient = exports.RestClient = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient), _dec(_class = function () {
    function RestClient(httpClient) {
      _classCallCheck(this, RestClient);

      this.http = null;

      this.http = this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    RestClient.prototype.getRequest = function getRequest(url) {
      var _this = this;

      console.log('entrou no request');
      console.log(url);
      return new Promise(function (resolve, reject) {
        _this.http.get(url).then(function (data) {
          data.JSON.parse(data.response);
        });
      });
    };

    RestClient.prototype.postRequest = function postRequest(url, object) {
      var _this2 = this;

      return new Promise(function (resolve, reject) {
        _this2.http.post(url, JSON.stringify(object)).then(function (data) {
          data.JSON.parse(data.response);
        });
      });
    };

    return RestClient;
  }()) || _class);
});
define('resources/index',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;
  function configure(config) {
    config.globalResources(['resources/elements/form/endereco', 'resources/elements/form/inputcel', 'resources/elements/form/inputcpf', 'resources/elements/form/inputdata', 'resources/elements/form/inputemail', 'resources/elements/form/inputtel', 'resources/elements/spinner', 'resources/value-converters/cpfconverter', 'resources/value-converters/cepconverter', 'resources/value-converters/celconverter', 'resources/value-converters/telconverter', 'resources/value-converters/dateconverter']);
  }
});
define('system/ViewCreator',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.ViewCreator = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var ViewCreator = exports.ViewCreator = (_dec = (0, _aureliaFramework.inject)(_aureliaFramework.ViewCompiler, _aureliaFramework.ViewResources, _aureliaFramework.Container), _dec(_class = function () {
    function ViewCreator(viewCompiler, resources, container) {
      _classCallCheck(this, ViewCreator);

      this.viewCompiler = viewCompiler;
      this.resources = resources;
      this.container = container;
    }

    ViewCreator.prototype.insert = function insert(containerElement, html, viewModel) {
      var viewCreator = this.viewCompiler.compile(html);
      var view = viewCreator.create(this.container);
      var anchorIsContainer = true;
      var viewSlot = new _aureliaFramework.ViewSlot(containerElement, anchorIsContainer);
      viewSlot.add(view);
      view.bind(viewModel, (0, _aureliaFramework.createOverrideContext)(viewModel));
      return function () {
        viewSlot.remove(view);
        view.unbind();
      };
    };

    return ViewCreator;
  }()) || _class);
});
define('views/home',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Home = exports.Home = function Home() {
    _classCallCheck(this, Home);
  };
});
define('resources/elements/header',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Header = exports.Header = function Header() {
    _classCallCheck(this, Header);
  };
});
define('resources/elements/menu',[], function () {
  "use strict";
});
define('resources/elements/spinner',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Spinner = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _dec, _desc, _value, _class, _descriptor, _descriptor2, _descriptor3;

  var Spinner = exports.Spinner = (_dec = (0, _aureliaFramework.bindable)({ defaultBindingMode: _aureliaFramework.bindingMode.twoWay }), (_class = function () {
    function Spinner() {
      _classCallCheck(this, Spinner);

      _initDefineProp(this, 'isLoading', _descriptor, this);

      _initDefineProp(this, 'message', _descriptor2, this);

      _initDefineProp(this, 'huge', _descriptor3, this);
    }

    Spinner.prototype.isLoadingValueChanged = function isLoadingValueChanged() {
      console.log('teste');
    };

    return Spinner;
  }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'isLoading', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: null
  }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, 'message', [_dec], {
    enumerable: true,
    initializer: null
  }), _descriptor3 = _applyDecoratedDescriptor(_class.prototype, 'huge', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: null
  })), _class));
});
define('resources/value-converters/celconverter',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var CelconverterValueConverter = exports.CelconverterValueConverter = function () {
    function CelconverterValueConverter() {
      _classCallCheck(this, CelconverterValueConverter);
    }

    CelconverterValueConverter.prototype.toView = function toView(value) {
      if (value != undefined && value.length == 11 && value.search('-') == -1 && value.search('\s') == -1) {
        var numero = value.substr(0, 2);
        numero += ' ';
        numero += value.substr(2, 5);
        numero += '-';
        numero += value.substr(7);
        value = numero;
      }

      return value;
    };

    CelconverterValueConverter.prototype.fromView = function fromView(value) {
      if (value != undefined) {
        var numero = value.replace('\\-', '');
        numero = numero.replace('\s', '');
        return numero;
      }
    };

    return CelconverterValueConverter;
  }();
});
define('resources/value-converters/cepconverter',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var CepconverterValueConverter = exports.CepconverterValueConverter = function () {
    function CepconverterValueConverter() {
      _classCallCheck(this, CepconverterValueConverter);
    }

    CepconverterValueConverter.prototype.toView = function toView(value) {
      if (value != undefined && value.length == 8 && value.search('\\.') == -1 && value.search('-') == -1) {
        var cep = value.substr(0, 2);
        cep += '.';
        cep += value.substr(2, 3);
        cep += '-';
        cep += value.substr(5);
        value = cep;
      }

      return value;
    };

    CepconverterValueConverter.prototype.fromView = function fromView(value) {
      if (value != undefined) {
        var cep = value.replace('\\.', '');
        cep = cep.replace('\\-', '');
        return cep;
      }
    };

    return CepconverterValueConverter;
  }();
});
define('resources/value-converters/cpfconverter',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var CpfconverterValueConverter = exports.CpfconverterValueConverter = function () {
    function CpfconverterValueConverter() {
      _classCallCheck(this, CpfconverterValueConverter);
    }

    CpfconverterValueConverter.prototype.toView = function toView(value) {
      if (value != undefined && value.length == 11 && value.search('-') == -1 && value.search('\\.') == -1) {
        var cpf = value.substr(0, 3);
        cpf += '.';
        cpf += value.substr(3, 3);
        cpf += '.';
        cpf += value.substr(6, 3);
        cpf += '-';
        cpf += value.substr(9);
        value = cpf;
      }

      return value;
    };

    CpfconverterValueConverter.prototype.fromView = function fromView(value) {
      if (value != undefined) {
        var cpf = value.replace('\\.', '');
        cpf = cpf.replace('\\-', '');
        return cpf;
      }
    };

    return CpfconverterValueConverter;
  }();
});
define('resources/value-converters/currencyconverter',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var CurrencyconverterValueConverter = exports.CurrencyconverterValueConverter = function () {
    function CurrencyconverterValueConverter() {
      _classCallCheck(this, CurrencyconverterValueConverter);
    }

    CurrencyconverterValueConverter.prototype.toView = function toView(value) {};

    CurrencyconverterValueConverter.prototype.fromView = function fromView(value) {};

    return CurrencyconverterValueConverter;
  }();
});
define('resources/value-converters/dateconverter',['exports', 'moment'], function (exports, _moment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.DateconverterValueConverter = undefined;

  var _moment2 = _interopRequireDefault(_moment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var DateconverterValueConverter = exports.DateconverterValueConverter = function () {
    function DateconverterValueConverter() {
      _classCallCheck(this, DateconverterValueConverter);
    }

    DateconverterValueConverter.prototype.toView = function toView(value) {
      var format = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 'DD/MM/YYYY';

      if (value != undefined) {
        _moment2.default.locale('pt-BR');

        if (value.year) {
          value = (0, _moment2.default)(value.year + '-' + value.monthValue + '-' + value.dayOfMonth).format('DD/MM/YYYY');
        } else if (value.length == 8 && value.search('/') == -1) {
          value = (0, _moment2.default)(value).format('DD/MM/YYYY');
        }
      }

      return value;
    };

    DateconverterValueConverter.prototype.fromView = function fromView(value) {
      if (value != undefined && value.length == 8 && value.search('/') == -1) {
        var dt = value.substr(0, 2);
        dt += '/';
        dt += value.substr(2, 2);
        dt += '/';
        dt += value.substr(4);
        value = dt;
      }

      return value;
    };

    return DateconverterValueConverter;
  }();
});
define('resources/value-converters/telconverter',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var TelconverterValueConverter = exports.TelconverterValueConverter = function () {
    function TelconverterValueConverter() {
      _classCallCheck(this, TelconverterValueConverter);
    }

    TelconverterValueConverter.prototype.toView = function toView(value) {
      if (value != undefined && value.length == 10 && value.search('-') == -1 && value.search('\s') == -1) {
        var numero = value.substr(0, 2);
        numero += ' ';
        numero += value.substr(2, 4);
        numero += '-';
        numero += value.substr(6);
        value = numero;
      }

      return value;
    };

    TelconverterValueConverter.prototype.fromView = function fromView(value) {
      if (value != undefined) {
        var numero = value.replace('\\-', '');
        numero = numero.replace('\s', '');
        return numero;
      }
    };

    return TelconverterValueConverter;
  }();
});
define('util/dialog/alerta',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Alerta = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Alerta = exports.Alerta = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Alerta(controller) {
      _classCallCheck(this, Alerta);

      this.controller = controller;
    }

    Alerta.prototype.activate = function activate(msg) {
      this.mensagem = msg;
    };

    return Alerta;
  }()) || _class);
});
define('util/dialog/erro',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Erro = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Erro = exports.Erro = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Erro(controller) {
      _classCallCheck(this, Erro);

      this.controller = controller;
    }

    Erro.prototype.activate = function activate(msg) {
      this.mensagem = msg;
    };

    return Erro;
  }()) || _class);
});
define('util/dialog/info',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Errordialog = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Errordialog = exports.Errordialog = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Errordialog(controller) {
      _classCallCheck(this, Errordialog);

      this.controller = controller;
    }

    Errordialog.prototype.activate = function activate(msg) {
      this.mensagem = msg;
    };

    return Errordialog;
  }()) || _class);
});
define('util/dialog/sucesso',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Sucesso = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Sucesso = exports.Sucesso = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Sucesso(controller) {
      _classCallCheck(this, Sucesso);

      this.controller = controller;
    }

    Sucesso.prototype.activate = function activate(msg) {
      this.mensagem = msg;
    };

    return Sucesso;
  }()) || _class);
});
define('views/configuracao/classificacoes',['exports', 'aurelia-framework', 'aurelia-http-client'], function (exports, _aureliaFramework, _aureliaHttpClient) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Classificacoes = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Classificacoes = exports.Classificacoes = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient), _dec(_class = function () {
    function Classificacoes(httpClient) {
      _classCallCheck(this, Classificacoes);

      this.classificacoes = [];

      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl('http://localhost:8080/');
      });
    }

    Classificacoes.prototype.attached = function attached() {
      var _this = this;

      this.http.get('configuracao/classificacoes').then(function (data) {
        _this.classificacoes = JSON.parse(data.response);
      });
    };

    return Classificacoes;
  }()) || _class);
});
define('views/integrante/integrante',['exports', 'aurelia-framework', 'aurelia-http-client', 'environment', 'aurelia-router', 'moment', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaHttpClient, _environment, _aureliaRouter, _moment, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Integrante = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  var _moment2 = _interopRequireDefault(_moment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Integrante = exports.Integrante = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient, _aureliaRouter.Router, _aureliaDialog.DialogService), _dec(_class = function () {
    function Integrante(httpClient, router, dialog) {
      _classCallCheck(this, Integrante);

      this.entidade = {};
      this.classificacoes = [];
      this.formasEntrega = [];
      this.idFormaEntrega = null;
      this.formasPgto = [];
      this.idFormaPgto = null;
      this.frequencias = [];
      this.enderecos = [];
      this.poderemoverendereco = false;
      this.mensagem = '';
      this.carregando = false;

      this.router = router;
      this.dialog = dialog;
      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    Integrante.prototype.activate = function activate(param) {
      var _this = this;

      if (param && param.idIntegrante) {
        this.carregando = true;
        this.http.get('/integrantes/' + param.idIntegrante).then(function (data) {
          _this.entidade = JSON.parse(data.response);
          _this.entidade.documento = _this.entidade.pessoa.documentos[0];
          _this.enderecos = _this.entidade.pessoa.enderecos;

          for (var _iterator = _this.enderecos, _isArray = Array.isArray(_iterator), _i = 0, _iterator = _isArray ? _iterator : _iterator[Symbol.iterator]();;) {
            var _ref;

            if (_isArray) {
              if (_i >= _iterator.length) break;
              _ref = _iterator[_i++];
            } else {
              _i = _iterator.next();
              if (_i.done) break;
              _ref = _i.value;
            }

            var endereco = _ref;

            var cobranca = _this.entidade.enderecoCobranca;
            var entrega = _this.entidade.enderecoEntrega;

            if (cobranca.cep == endereco.cep && cobranca.logradouro == endereco.logradouro && cobranca.numero == endereco.numero && cobranca.bairro == endereco.bairro && cobranca.complemento == endereco.complemento && cobranca.municipio == endereco.municipio && cobranca.uf == endereco.uf) {
              endereco.cobranca = true;
            }

            if (entrega.cep == endereco.cep && entrega.logradouro == endereco.logradouro && entrega.numero == endereco.numero && entrega.bairro == endereco.bairro && entrega.complemento == endereco.complemento && entrega.municipio == endereco.municipio && entrega.uf == endereco.uf) {
              endereco.entrega = true;
            }

            _this.poderemoverendereco = true;
          }

          var contatos = _this.entidade.pessoa.contatos;
          var qtdTel = 0;
          var qtdCel = 0;
          var qtdMail = 0;

          for (var _iterator2 = contatos, _isArray2 = Array.isArray(_iterator2), _i2 = 0, _iterator2 = _isArray2 ? _iterator2 : _iterator2[Symbol.iterator]();;) {
            var _ref2;

            if (_isArray2) {
              if (_i2 >= _iterator2.length) break;
              _ref2 = _iterator2[_i2++];
            } else {
              _i2 = _iterator2.next();
              if (_i2.done) break;
              _ref2 = _i2.value;
            }

            var contato = _ref2;

            if (contato.idTipo == 1) {
              _this.tels.push({ id: contato.id, obs: contato.observacao, numero: contato.valor });
              qtdTel++;
            }

            if (contato.idTipo == 3) {
              _this.cels.push({ id: contato.id, obs: contato.observacao, numero: contato.valor, whatsapp: false });
              qtdCel++;
            }

            if (contato.idTipo == 5) {
              for (var _iterator3 = _this.cels, _isArray3 = Array.isArray(_iterator3), _i3 = 0, _iterator3 = _isArray3 ? _iterator3 : _iterator3[Symbol.iterator]();;) {
                var _ref3;

                if (_isArray3) {
                  if (_i3 >= _iterator3.length) break;
                  _ref3 = _iterator3[_i3++];
                } else {
                  _i3 = _iterator3.next();
                  if (_i3.done) break;
                  _ref3 = _i3.value;
                }

                var cel = _ref3;

                if (cel.numero == contato.valor) {
                  cel.whatsapp = true;
                }
              }
            }

            if (contato.idTipo == 4) {
              _this.emails.push({ id: contato.id, mail: contato.valor, obs: contato.observacao });
              qtdMail++;
            }
          }

          if (qtdTel > 0) {
            _this.tels.splice(0, 1);
          }

          if (qtdCel > 0) {
            _this.cels.splice(0, 1);
          }

          if (qtdMail > 0) {
            _this.emails.splice(0, 1);
          }

          _this.carregando = false;
        }).catch(function (error) {
          _this.carregando = false;
          _this.dialog.open({ viewModel: 'util/dialog', model: { tipo: 'erro', msg: error.response } });
        });
      }
    };

    Integrante.prototype.attached = function attached() {
      var _this2 = this;

      this.carregando = true;
      this.http.get('configuracao/classificacoes').then(function (data) {
        return _this2.classificacoes = JSON.parse(data.response);
      });
      this.http.get('configuracao/formasentrega').then(function (data) {
        return _this2.formasEntrega = JSON.parse(data.response);
      });
      this.http.get('configuracao/formaspgto').then(function (data) {
        return _this2.formasPgto = JSON.parse(data.response);
      });
      this.http.get('configuracao/frequencias').then(function (data) {
        _this2.frequencias = JSON.parse(data.response);
        _this2.carregando = false;
      });
      this.initConfig();
    };

    Integrante.prototype.initConfig = function initConfig() {
      this.enderecos = [{
        idTipo: null,
        cep: null,
        logradouro: null,
        numero: null,
        complemento: null,
        bairro: null,
        municipio: null,
        uf: null,
        obs: null,
        entrega: false,
        cobranca: false
      }];
      this.tels = [{ id: null, numero: null, obs: null }];
      this.cels = [{ id: null, numero: null, obs: null, whatsapp: false }];
      this.emails = [{ id: null, mail: null, obs: null }];
      this.entidade = {
        id: null,
        dtCadastro: new Date().toLocaleDateString(),
        dtDesativacao: null,
        ativo: true,
        pessoa: {
          id: null,
          nome: null,
          nascimento: null,
          contatos: [],
          documentos: [],
          enderecos: []
        },
        documento: { id: null, idTipo: 1, valor: null },
        enderecoCobranca: {},
        enderecoEntrega: {},
        frequencia: { id: null, nome: null, freqMensal: null },
        formaEntrega: { id: null },
        formaPgtoPref: { id: null },
        diaPgtoPref: 1,
        classificacao: { id: null }
      };
    };

    Integrante.prototype.onClickCancelar = function onClickCancelar() {
      this.router.navigateToRoute('integrantes');
    };

    Integrante.prototype.onClickSalvar = function onClickSalvar() {
      var _this3 = this;

      this.carregando = true;
      this.configurarDatas();
      this.entidade.pessoa.contatos = [];
      this.entidade.pessoa.documentos = [];
      this.entidade.pessoa.enderecos = [];
      this.entidade.pessoa.documentos.push(this.entidade.documento);

      for (var _iterator4 = this.tels, _isArray4 = Array.isArray(_iterator4), _i4 = 0, _iterator4 = _isArray4 ? _iterator4 : _iterator4[Symbol.iterator]();;) {
        var _ref4;

        if (_isArray4) {
          if (_i4 >= _iterator4.length) break;
          _ref4 = _iterator4[_i4++];
        } else {
          _i4 = _iterator4.next();
          if (_i4.done) break;
          _ref4 = _i4.value;
        }

        var tel = _ref4;

        if (tel.numero != null && tel.numero != '') {
          this.entidade.pessoa.contatos.push({ id: tel.id, idTipo: 1, observacao: tel.obs, valor: tel.numero });
        }
      }

      for (var _iterator5 = this.cels, _isArray5 = Array.isArray(_iterator5), _i5 = 0, _iterator5 = _isArray5 ? _iterator5 : _iterator5[Symbol.iterator]();;) {
        var _ref5;

        if (_isArray5) {
          if (_i5 >= _iterator5.length) break;
          _ref5 = _iterator5[_i5++];
        } else {
          _i5 = _iterator5.next();
          if (_i5.done) break;
          _ref5 = _i5.value;
        }

        var cel = _ref5;

        if (cel.numero != null && cel.numero != '') {
          this.entidade.pessoa.contatos.push({ id: cel.id, idTipo: 3, observacao: cel.obs, valor: cel.numero });

          if (cel.whatsapp) {
            this.entidade.pessoa.contatos.push({ id: cel.id, idTipo: 5, observacao: cel.obs, valor: cel.numero });
          }
        }
      }

      for (var _iterator6 = this.emails, _isArray6 = Array.isArray(_iterator6), _i6 = 0, _iterator6 = _isArray6 ? _iterator6 : _iterator6[Symbol.iterator]();;) {
        var _ref6;

        if (_isArray6) {
          if (_i6 >= _iterator6.length) break;
          _ref6 = _iterator6[_i6++];
        } else {
          _i6 = _iterator6.next();
          if (_i6.done) break;
          _ref6 = _i6.value;
        }

        var email = _ref6;

        if (email.mail != null && email.numero != '') {
          this.entidade.pessoa.contatos.push({ id: email.id, idTipo: 4, observacao: email.obs, valor: email.mail });
        }
      }

      for (var _iterator7 = this.enderecos, _isArray7 = Array.isArray(_iterator7), _i7 = 0, _iterator7 = _isArray7 ? _iterator7 : _iterator7[Symbol.iterator]();;) {
        var _ref7;

        if (_isArray7) {
          if (_i7 >= _iterator7.length) break;
          _ref7 = _iterator7[_i7++];
        } else {
          _i7 = _iterator7.next();
          if (_i7.done) break;
          _ref7 = _i7.value;
        }

        var endereco = _ref7;

        this.entidade.pessoa.enderecos.push({
          id: endereco.id,
          idTipo: endereco.idTipo,
          cep: endereco.cep,
          logradouro: endereco.logradouro,
          numero: endereco.numero,
          complemento: endereco.complemento,
          bairro: endereco.bairro,
          municipio: endereco.municipio,
          uf: endereco.uf,
          observacao: endereco.obs
        });

        if (endereco.cobranca) {
          this.entidade.enderecoCobranca = {
            id: endereco.id,
            idTipo: endereco.idTipo,
            cep: endereco.cep,
            logradouro: endereco.logradouro,
            numero: endereco.numero,
            complemento: endereco.complemento,
            bairro: endereco.bairro,
            municipio: endereco.municipio,
            uf: endereco.uf,
            observacao: endereco.obs
          };
        }

        if (endereco.entrega) {
          this.entidade.enderecoEntrega = {
            id: endereco.id,
            idTipo: endereco.idTipo,
            cep: endereco.cep,
            logradouro: endereco.logradouro,
            numero: endereco.numero,
            complemento: endereco.complemento,
            bairro: endereco.bairro,
            municipio: endereco.municipio,
            uf: endereco.uf,
            observacao: endereco.obs
          };
        }
      }

      this.http.post('/integrantes', JSON.stringify(this.entidade)).then(function (data) {
        _this3.initConfig();
        _this3.mensagem = '';
        _this3.carregando = false;
        _this3.router.navigateToRoute('integrantes', { incluido: true });
        _this3.dialog.open({ viewModel: 'util/dialog', model: { tipo: 'sucesso', msg: 'Integrante do Clube do Livro incluído com sucesso' } });
      }).catch(function (error) {
        _this3.carregando = false;
        _this3.dialog.open({ viewModel: 'util/dialog', model: { tipo: 'erro', msg: error.response } });
      });
    };

    Integrante.prototype.configurarDatas = function configurarDatas() {
      _moment2.default.locale('pt-BR');

      if (this.entidade.dtCadastro.year) {
        var data = this.entidade.dtCadastro;
        this.entidade.dtCadastro = (0, _moment2.default)(data.year + '-' + data.monthValue + '-' + data.dayOfMonth).format('DD/MM/YYYY');
      }

      if (this.entidade.pessoa.nascimento != undefined && this.entidade.pessoa.nascimento.year) {
        var _data = this.entidade.pessoa.nascimento;
        this.entidade.pessoa.nascimento = (0, _moment2.default)(_data.year + '-' + _data.monthValue + '-' + _data.dayOfMonth).format('DD/MM/YYYY');
      } else if (this.entidade.pessoa.nascimento != undefined && this.entidade.pessoa.nascimento == '') {
        this.entidade.pessoa.nascimento = null;
      }
    };

    return Integrante;
  }()) || _class);
});
define('views/integrante/integrantes',['exports', 'aurelia-framework', 'aurelia-http-client', 'aurelia-router', 'environment'], function (exports, _aureliaFramework, _aureliaHttpClient, _aureliaRouter, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Integrantes = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Integrantes = exports.Integrantes = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient, _aureliaRouter.Router), _dec(_class = function () {
    function Integrantes(httpClient, router) {
      _classCallCheck(this, Integrantes);

      this.integrantes = [];
      this.categorias = [];
      this.pesquisa = {};
      this.mensagem = '';
      this.incluido = false;
      this.carregando = false;

      this.router = router;
      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    Integrantes.prototype.attached = function attached(params) {
      var _this = this;

      this.carregando = true;
      this.http.get('configuracao/classificacoes').then(function (data) {
        _this.categorias = JSON.parse(data.response);
        _this.carregando = false;
      });

      if (params && params.incluido) {
        this.incluido = true;
      }
    };

    Integrantes.prototype.initConfig = function initConfig() {
      this.pesquisa = {};
    };

    Integrantes.prototype.onClickAtivar = function onClickAtivar(idx, id) {
      var _this2 = this;

      this.carregando = true;
      this.http.put('/integrantes/ativar', JSON.stringify(id)).then(function (data) {
        _this2.onClickPesquisar();
        _this2.carregando = false;
      }).catch(function (error) {
        alert(error.response);
        _this2.carregando = false;
      });
    };

    Integrantes.prototype.onClickCloseAlert = function onClickCloseAlert() {
      this.mensagem = '';
    };

    Integrantes.prototype.onClickDesativar = function onClickDesativar(idx, id) {
      var _this3 = this;

      this.carregando = true;
      this.http.put('/integrantes/desativar', JSON.stringify(id)).then(function (data) {
        _this3.onClickPesquisar();
        _this3.carregando = false;
      }).catch(function (error) {
        alert(error.response);
        _this3.carregando = false;
      });
    };

    Integrantes.prototype.onClickEditar = function onClickEditar(id) {
      this.router.navigateToRoute('integrante', { idIntegrante: id });
    };

    Integrantes.prototype.onClickIncluir = function onClickIncluir() {
      this.router.navigateToRoute('integrante', { idIntegrante: null });
    };

    Integrantes.prototype.onClickLimpar = function onClickLimpar() {
      this.initConfig();
    };

    Integrantes.prototype.onClickPesquisar = function onClickPesquisar() {
      var _this4 = this;

      this.carregando = true;
      this.http.post('/integrantes/pesquisa', JSON.stringify(this.pesquisa)).then(function (data) {
        _this4.integrantes = JSON.parse(data.response);

        if (_this4.integrantes.length > 0) {
          _this4.mensagem = '';
        } else {
          _this4.mensagem = 'Não foram encontrados integrantes que satisfaçam a pesquisa';
        }
        _this4.carregando = false;
      }).catch(function (error) {
        alert(error.response);
        _this4.carregando = false;
      });
    };

    return Integrantes;
  }()) || _class);
});
define('resources/elements/form/dynamic-form',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.DynamicFormCustomElement = undefined;

    function _initDefineProp(target, property, descriptor, context) {
        if (!descriptor) return;
        Object.defineProperty(target, property, {
            enumerable: descriptor.enumerable,
            configurable: descriptor.configurable,
            writable: descriptor.writable,
            value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
        });
    }

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
        var desc = {};
        Object['ke' + 'ys'](descriptor).forEach(function (key) {
            desc[key] = descriptor[key];
        });
        desc.enumerable = !!desc.enumerable;
        desc.configurable = !!desc.configurable;

        if ('value' in desc || desc.initializer) {
            desc.writable = true;
        }

        desc = decorators.slice().reverse().reduce(function (desc, decorator) {
            return decorator(target, property, desc) || desc;
        }, desc);

        if (context && desc.initializer !== void 0) {
            desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
            desc.initializer = undefined;
        }

        if (desc.initializer === void 0) {
            Object['define' + 'Property'](target, property, desc);
            desc = null;
        }

        return desc;
    }

    function _initializerWarningHelper(descriptor, context) {
        throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
    }

    var _desc, _value, _class, _descriptor, _descriptor2, _descriptor3;

    var DynamicFormCustomElement = exports.DynamicFormCustomElement = (_class = function () {
        function DynamicFormCustomElement() {
            _classCallCheck(this, DynamicFormCustomElement);

            _initDefineProp(this, 'class', _descriptor, this);

            _initDefineProp(this, 'message', _descriptor2, this);

            _initDefineProp(this, 'model', _descriptor3, this);
        }

        DynamicFormCustomElement.prototype.attached = function attached() {};

        DynamicFormCustomElement.prototype.activate = function activate(params) {
            this.class = params.class;
            this.message = params.message;
            this.model = params.model;
        };

        DynamicFormCustomElement.prototype.modelChanged = function modelChanged(newValue, oldValue) {
            console.log(newValue);
        };

        return DynamicFormCustomElement;
    }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'class', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: function initializer() {
            return 'classe';
        }
    }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, 'message', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: function initializer() {
            return 'novo';
        }
    }), _descriptor3 = _applyDecoratedDescriptor(_class.prototype, 'model', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: function initializer() {
            return '';
        }
    })), _class);
});
define('resources/elements/form/endereco',['exports', 'aurelia-framework', 'aurelia-http-client', 'environment', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaHttpClient, _environment, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Endereco = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _dec, _class, _desc, _value, _class2, _descriptor;

  var Endereco = exports.Endereco = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient, _aureliaDialog.DialogService), _dec(_class = (_class2 = function () {
    function Endereco(httpClient, dialog) {
      _classCallCheck(this, Endereco);

      _initDefineProp(this, 'enderecos', _descriptor, this);

      this.tipos = [];
      this.ufs = [];
      this.carregando = false;

      this.dialog = dialog;
      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    Endereco.prototype.attached = function attached() {
      var _this = this;

      this.http.get('configuracao/tiposendereco').then(function (data) {
        _this.tipos = JSON.parse(data.response);
      });
      this.ufs = ['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'];
    };

    Endereco.prototype.onClickAdd = function onClickAdd() {
      this.enderecos.push({
        id: null,
        idTipo: null,
        cep: null,
        logradouro: null,
        numero: null,
        complemento: null,
        bairro: null,
        municipio: null,
        uf: null,
        obs: null,
        entrega: false,
        cobranca: false
      });
    };

    Endereco.prototype.onClickDel = function onClickDel(index) {
      this.enderecos.splice(index, 1);
    };

    Endereco.prototype.onClickPesquisarEndereco = function onClickPesquisarEndereco(idx) {
      var _this2 = this;

      if (this.enderecos[idx].cep) {
        this.carregando = true;
        var cep = this.enderecos[idx].cep;
        cep = cep.replace(/[\.-]/g, "");

        this.http.get('endereco/consulta/' + cep).then(function (data) {
          var endereco = JSON.parse(data.response);
          _this2.enderecos[idx].logradouro = endereco.logradouro;
          _this2.enderecos[idx].bairro = endereco.bairro;
          _this2.enderecos[idx].municipio = endereco.municipio;
          _this2.enderecos[idx].uf = endereco.uf;
          _this2.carregando = false;
        }).catch(function (error) {
          _this2.carregando = false;
          _this2.dialog.open({ viewModel: 'util/dialog', model: { tipo: 'info', msg: 'Não foi encontrado um endereço relacionado ao CEP informado' } });
        });
      } else {
        this.dialog.open({ viewModel: 'util/dialog', model: { tipo: 'alerta', msg: 'É preciso informar um CEP' } });
      }
    };

    return Endereco;
  }(), (_descriptor = _applyDecoratedDescriptor(_class2.prototype, 'enderecos', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return [];
    }
  })), _class2)) || _class);
});
define('resources/elements/form/inputcel',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Inputcel = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor;

  var Inputcel = exports.Inputcel = (_class = function () {
    function Inputcel() {
      _classCallCheck(this, Inputcel);

      _initDefineProp(this, 'cels', _descriptor, this);
    }

    Inputcel.prototype.onClickAdd = function onClickAdd() {
      this.cels.push({ id: null, numero: null, obs: null, whatsapp: false });
    };

    Inputcel.prototype.onClickDel = function onClickDel(index) {
      this.cels.splice(index, 1);
    };

    return Inputcel;
  }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'cels', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return [];
    }
  })), _class);
});
define('resources/elements/form/inputcpf',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Inputcpf = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor, _descriptor2;

  var Inputcpf = exports.Inputcpf = (_class = function Inputcpf() {
    _classCallCheck(this, Inputcpf);

    _initDefineProp(this, 'obrigatorio', _descriptor, this);

    _initDefineProp(this, 'numero', _descriptor2, this);
  }, (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'obrigatorio', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return false;
    }
  }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, 'numero', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return '';
    }
  })), _class);
});
define('resources/elements/form/inputdata',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Inputdata = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor, _descriptor2;

  var Inputdata = exports.Inputdata = (_class = function Inputdata() {
    _classCallCheck(this, Inputdata);

    _initDefineProp(this, 'obrigatorio', _descriptor, this);

    _initDefineProp(this, 'valor', _descriptor2, this);
  }, (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'obrigatorio', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return false;
    }
  }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, 'valor', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: null
  })), _class);
});
define('resources/elements/form/inputemail',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Inputemail = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor;

  var Inputemail = exports.Inputemail = (_class = function () {
    function Inputemail() {
      _classCallCheck(this, Inputemail);

      _initDefineProp(this, 'emails', _descriptor, this);
    }

    Inputemail.prototype.onClickAdd = function onClickAdd() {
      this.emails.push({ id: null, mail: null, obs: null });
    };

    Inputemail.prototype.onClickDel = function onClickDel(index) {
      this.emails.splice(index, 1);
    };

    return Inputemail;
  }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'emails', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return [];
    }
  })), _class);
});
define('resources/elements/form/inputtel',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Inputtel = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor;

  var Inputtel = exports.Inputtel = (_class = function () {
    function Inputtel() {
      _classCallCheck(this, Inputtel);

      _initDefineProp(this, 'tels', _descriptor, this);
    }

    Inputtel.prototype.onClickAdd = function onClickAdd() {
      this.tels.push({ id: null, numero: null, obs: null });
    };

    Inputtel.prototype.onClickDel = function onClickDel(index) {
      this.tels.splice(index, 1);
    };

    return Inputtel;
  }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'tels', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: function initializer() {
      return [];
    }
  })), _class);
});
define('views/financeiro/boleto/boleto',['exports', 'aurelia-framework', 'aurelia-http-client', 'environment', 'aurelia-router'], function (exports, _aureliaFramework, _aureliaHttpClient, _environment, _aureliaRouter) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Boleto = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Boleto = exports.Boleto = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient, _aureliaRouter.Router), _dec(_class = function () {
    function Boleto(httpClient, router) {
      _classCallCheck(this, Boleto);

      this.boleto = {};
      this.mensagem = '';
      this.carregando = false;

      this.router = router;
      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    Boleto.prototype.active = function active() {};

    Boleto.prototype.attached = function attached() {};

    Boleto.prototype.initConfig = function initConfig() {};

    Boleto.prototype.onClickCancelar = function onClickCancelar() {
      this.router.navigateToRoute('boletos');
    };

    Boleto.prototype.onClickSalvar = function onClickSalvar() {};

    return Boleto;
  }()) || _class);
});
define('views/financeiro/boleto/boletos',['exports', 'aurelia-framework', 'aurelia-http-client', 'aurelia-router', 'environment'], function (exports, _aureliaFramework, _aureliaHttpClient, _aureliaRouter, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Boletos = undefined;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Boletos = exports.Boletos = (_dec = (0, _aureliaFramework.inject)(_aureliaHttpClient.HttpClient, _aureliaRouter.Router), _dec(_class = function () {
    function Boletos(httpClient, router) {
      _classCallCheck(this, Boletos);

      this.boletos = [];
      this.categorias = [];
      this.situacoes = [];
      this.pesquisa = {};
      this.mensagem = '';
      this.carregando = false;

      this.router = router;
      this.http = httpClient;
      this.http.configure(function (x) {
        x.withBaseUrl(_environment2.default.endpoint);
        x.withHeader('Content-Type', 'application/json');
      });
    }

    Boletos.prototype.attached = function attached() {
      var _this = this;

      this.carregando = true;
      this.http.get('configuracao/classificacoes').then(function (data) {
        _this.categorias = JSON.parse(data.response);
        _this.carregando = false;
      });
      this.situacoes = [{ id: 0, nome: 'Aberto' }, { id: 1, nome: 'Baixado' }, { id: 2, nome: 'Baixado manualmente' }];
    };

    Boletos.prototype.initConfig = function initConfig() {
      this.pesquisa = {};
    };

    Boletos.prototype.onClickCloseAlert = function onClickCloseAlert() {
      this.mensagem = '';
    };

    Boletos.prototype.onClickEditar = function onClickEditar(id) {
      this.router.navigateToRoute('boleto', { idBoleto: id });
    };

    Boletos.prototype.onClickLimpar = function onClickLimpar() {
      this.initConfig();
    };

    Boletos.prototype.onClickPesquisar = function onClickPesquisar() {
      var _this2 = this;

      this.carregando = true;
      this.http.post('/financeiro/boletos/pesquisa', JSON.stringify(this.pesquisa)).then(function (data) {
        _this2.boletos = JSON.parse(data.response);

        if (_this2.boletos.length > 0) {
          _this2.mensagem = '';
        } else {
          _this2.mensagem = 'Não foram encontrados boletos que satisfaçam a pesquisa';
        }

        _this2.carregando = false;
      }).catch(function (error) {
        alert(error.response);
        _this2.carregando = false;
      });
    };

    return Boletos;
  }()) || _class);
});
define('util/alerta',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Alerta = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Alerta = exports.Alerta = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Alerta(controller) {
      _classCallCheck(this, Alerta);

      this.controller = controller;
    }

    Alerta.prototype.activate = function activate(msg) {
      this.mensagem = msg;
    };

    return Alerta;
  }()) || _class);
});
define('util/dialog',['exports', 'aurelia-framework', 'aurelia-dialog'], function (exports, _aureliaFramework, _aureliaDialog) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Alerta = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _dec, _class;

  var Alerta = exports.Alerta = (_dec = (0, _aureliaFramework.inject)(_aureliaDialog.DialogController), _dec(_class = function () {
    function Alerta(controller) {
      _classCallCheck(this, Alerta);

      this.controller = controller;
    }

    Alerta.prototype.activate = function activate(params) {
      this.tipo = params.tipo;
      this.mensagem = params.msg;
    };

    return Alerta;
  }()) || _class);
});
define('text!app.html', ['module'], function(module) { module.exports = "<template><div class=\"container-fluid\"><div class=\"menubar\"><img class=\"logo\" src=\"assets/img/logo.png\"></div><nav class=\"navbar navbar-expand-md navbar-dark bg-dark\"><div class=\"collapse navbar-collapse\"><ul class=\"navbar-nav mr-auto\"><li class=\"nav-item\" repeat.for=\"row of router.navigation\"><a class=\"nav-link\" href=\"${row.href}\">${row.title}</a></li></ul><ul class=\"nav\tnavbar-nav\tnavbar-right\"><li class=\"loader\" if.bind=\"router.isNavigating\"><i class=\"fa\tfa-spinner\tfa-spin\tfa-2x\"></i></li></ul></div></nav></div><div class=\"container-fluid\"><div class=\"corpo\"><router-view></router-view></div></div></template>"; });
define('text!views/home.html', ['module'], function(module) { module.exports = "<template><h3 class=\"texto\">Visão geral</h3></template>"; });
define('text!resources/elements/header.html', ['module'], function(module) { module.exports = "<template><nav class=\"navabar navbar-expand-lg\"><a class=\"navabar-brand\"><img src=\"assets/img/logo.png\"></a><ul class=\"navbar-nav\"><li class=\"nav-item\">Integrantes</li><li class=\"nav-item dropdown\"><a class=\"nav-link dropdown-toggle\" href=\"#\" data-toggle=\"dropdown\">Financeiro</a><div class=\"dropdown-menu\">Boletos</div></li></ul></nav></template>"; });
define('text!resources/elements/menu.html', ['module'], function(module) { module.exports = ""; });
define('text!resources/elements/spinner.html', ['module'], function(module) { module.exports = "<template><div class=\"${huge ? 'page-loader' : 'container-loader'}\" if.bind=\"isLoading\"><div class=\"spinner\"></div></div></template>"; });
define('text!util/dialog/alerta.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-exclamation-triangle\">&nbsp;</span> <span>Atenção</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!util/dialog/erro.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-times-circle\">&nbsp;</span> <span>Erro</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!util/dialog/info.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-exclamation-circle\">&nbsp;</span> <span>Informação</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!util/dialog/sucesso.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"alert alert-sucess alert-dismissable\"><span class=\"icon-check-circle\">&nbsp;</span> <span>Sucesso</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-primary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!views/configuracao/classificacoes.html', ['module'], function(module) { module.exports = "<template><table border=\"1\"><thead><tr><td>ID</td><td>Nome</td></tr></thead><tbody><tr repeat.for=\"classificacao of classificacoes\"><td>${classificacao.id}</td><td>${classificacao.nome}</td></tr></tbody></table></template>"; });
define('text!views/integrante/integrante.html', ['module'], function(module) { module.exports = "<template><spinner is-loading.bind=\"carregando\" huge.bind=\"false\"></spinner><div class=\"row\"><h3 class=\"col\">Integrantes do Clube do Livro</h3></div><nav><div class=\"nav nav-tabs\" id=\"nav-tab\" role=\"tablist\"><a class=\"nav-item nav-link active\" id=\"nav-home-tab\" data-toggle=\"tab\" href=\"#nav-home\" role=\"tab\" aria-controls=\"nav-home\" aria-selected=\"true\">Básico</a> <a class=\"nav-item nav-link\" id=\"nav-profile-tab\" data-toggle=\"tab\" href=\"#nav-profile\" role=\"tab\" aria-controls=\"nav-profile\" aria-selected=\"false\">Endereços</a> <a class=\"nav-item nav-link\" id=\"nav-contact-tab\" data-toggle=\"tab\" href=\"#nav-contact\" role=\"tab\" aria-controls=\"nav-contact\" aria-selected=\"false\">Contatos</a> <a class=\"nav-item nav-link\" id=\"nav-complemento-tab\" data-toggle=\"tab\" href=\"#nav-complemento\" role=\"tab\" aria-controls=\"nav-complemento\" aria-selected=\"false\">Complemento</a></div></nav><div class=\"tab-content\" id=\"nav-tabContent\"><div class=\"tab-pane fade show active\" id=\"nav-home\" role=\"tabpanel\" aria-labelledby=\"nav-home-tab\"><br><form><div class=\"form-group row\"><div class=\"col-sm-4\"><label>Data de cadastro</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"10\" minlength=\"10\" type=\"text\" required value.bind=\"entidade.dtCadastro | dateconverter\" pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"></div><div class=\"col-sm-4\"><label>Data de nascimento</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"10\" minlength=\"10\" type=\"text\" value.bind=\"entidade.pessoa.nascimento | dateconverter\" pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"></div><div class=\"col-sm-4\"><label>CPF</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"14\" minlength=\"11\" type=\"text\" value.bind=\"entidade.documento.valor | cpfconverter\" pattern=\"[0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2}\"></div></div><div class=\"form-group row\"><div class=\"col-sm\"><label for=\"nome\">Nome</label><input autocomplete=\"off\" class=\"form-control\" id=\"nome\" maxlength=\"255\" required type=\"text\" value.bind=\"entidade.pessoa.nome\"></div></div></form></div><div class=\"tab-pane fade\" id=\"nav-profile\" role=\"tabpanel\" aria-labelledby=\"nav-profile-tab\"><br><form><endereco enderecos.bind=\"enderecos\"></endereco></form></div><div class=\"tab-pane fade\" id=\"nav-contact\" role=\"tabpanel\" aria-labelledby=\"nav-contact-tab\"><br><form><inputtel tels.bind=\"tels\"></inputtel><inputcel cels.bind=\"cels\"></inputcel><inputemail emails.bind=\"emails\"></inputemail></form></div><div class=\"tab-pane fade\" id=\"nav-complemento\" role=\"tabpanel\" aria-labelledby=\"nav-complemento-tab\"><br><form><fieldset class=\"borda\"><legend>Categorias</legend><div class=\"row\"><label class=\"radio-inline col-sm-3\" repeat.for=\"classificacao of classificacoes\"><input type=\"radio\" model.bind=\"classificacao.id\" checked.bind=\"entidade.classificacao.id\">&nbsp;${classificacao.nome} </label></div></fieldset><br><fieldset class=\"borda\"><legend>Freqüência</legend><div class=\"row\"><label class=\"radio-inline col-sm-3\" repeat.for=\"frequencia of frequencias\"><input type=\"radio\" model.bind=\"frequencia.id\" checked.bind=\"entidade.frequencia.id\">&nbsp;${frequencia.nome} </label></div></fieldset><br><fieldset class=\"borda\"><legend>Entrega</legend><div class=\"row\"><label class=\"radio-inline col-sm-3\" repeat.for=\"formaEntrega of formasEntrega\"><input type=\"radio\" model.bind=\"formaEntrega.id\" checked.bind=\"entidade.formaEntrega.id\">&nbsp;${formaEntrega.nome} </label></div></fieldset><br><fieldset class=\"borda\"><legend>Pagamento</legend><div class=\"row\"><label class=\"radio-inline col-sm-2\" repeat.for=\"formaPgto of formasPgto\"><input type=\"radio\" model.bind=\"formaPgto.id\" checked.bind=\"entidade.formaPgtoPref.id\">&nbsp;${formaPgto.nome} </label></div><div class=\"col-sm\"><label>Dia de pagamento</label><input class=\"form-control col-sm-2\" max=\"31\" min=\"1\" type=\"number\" value.bind=\"entidade.diaPgtoPref\"></div></fieldset></form></div><br><div class=\"form-group row\"><div class=\"col-sm-1\"><button type=\"button\" class=\"btn btn-primary\" click.delegate=\"onClickSalvar()\"><span class=\"icon-floppy-o\"></span> <span>Salvar</span></button></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-secondary\" click.delegate=\"onClickCancelar()\"><span class=\"icon-reply\"></span> <span>Cancelar</span></button></div></div></div></template>"; });
define('text!views/integrante/integrantes.html', ['module'], function(module) { module.exports = "<template><spinner is-loading.bind=\"carregando\" huge.bind=\"false\"></spinner><div class=\"row\"><h3 class=\"col\">Integrantes do Clube do Livro</h3></div><form><div class=\"form-group row\"><div class=\"col-sm-5\"><input autocomplete=\"off\" type=\"text\" id=\"nome\" class=\"form-control\" value.bind=\"pesquisa.nome\" placeholder=\"Digite um nome para pesquisa\"></div><label for=\"categoria\" class=\"col-sm-1 col-form-label\">Categoria</label><div><select id=\"categoria\" class=\"form-control\" value.bind=\"pesquisa.categoria\"><option></option><option repeat.for=\"categoria of categorias\" model.bind=\"categoria.id\">${categoria.nome}</option></select></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-secondary\" click.delegate=\"onClickLimpar()\"><span class=\"icon-eraser\"></span> <span>Limpar</span></button> <button type=\"button\" class=\"btn btn-primary\" click.delegate=\"onClickPesquisar()\"><span class=\"icon-filter\"></span> <span>Filtrar</span></button> <button type=\"button\" class=\"btn btn-primary\" click.delegate=\"onClickIncluir()\"><span class=\"icon-plus\"></span> <span>Incluir</span></button></div></div></form><div class=\"table-responsive\" show.bind=\"integrantes.length > 0\"><table class=\"table table-hover\"><thead><th>Nome</th><th class=\"col-sm-3\">Categoria</th><th class=\"col-sm-2\">Ativo</th></thead><tbody><tr repeat.for=\"item of integrantes\"><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.pessoa.nome}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.classificacao.nome}</td><td><a class=\"item-grid\" show.bind=\"item.ativo\" click.delegate=\"onClickDesativar($index, item.id)\"><span class=\"icon-toggle-on\"></span> </a><a class=\"item-grid\" show.bind=\"!item.ativo\" click.delegate=\"onClickAtivar($index, item.id)\"><span class=\"icon-toggle-off\"></span></a></td></tr></tbody></table></div><div class=\"alert alert-info alert-dismissable fade show\" show.bind=\"mensagem.length > 0\"> ${mensagem} </div></template>"; });
define('text!resources/elements/form/dynamic-form.html', ['module'], function(module) { module.exports = "<template><label>Class: ${class}</label>- Message:<label>${message}</label><input type=\"text\" name=\"form\" value.bind=\"model\"></template>"; });
define('text!resources/elements/form/endereco.html', ['module'], function(module) { module.exports = "<template><spinner is-loading.bind=\"carregando\" huge.bind=\"false\"></spinner><div repeat.for=\"endereco of enderecos\"><div class=\"form-group row\"><div class=\"col-sm-3\"><label>Tipo</label><select class=\"form-control\" value.bind=\"endereco.idTipo\"><option></option><option repeat.for=\"tipo of tipos\" model.bind=\"tipo.id\">${tipo.nome}</option></select></div><div class=\"col-sm-2\"><label>CEP</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"10\" minlength=\"8\" pattern=\"[0-9]{2}[\\.]?[0-9]{3}[-]?[0-9]{3}\" type=\"text\" value.bind=\"endereco.cep | cepconverter\"></div><div class=\"col-sm-2\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-primary form-control\" click.delegate=\"onClickPesquisarEndereco($index)\"><span class=\"icon-search\"></span> <span>Pesquisar</span></button></div><div class=\"col-sm-4\"></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-link btn-lg form-control float-right\" click.delegate=\"onClickDel($index)\" show.bind=\"enderecos.length > 1\" title=\"Remover endereço\"><span class=\"icon-trash-o\"></span></button></div></div><div class=\"form-group row\"><div class=\"col-sm\"><label>Logradouro</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"endereco.logradouro\"></div><div class=\"col-sm-2\"><label>Número</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"15\" type=\"text\" value.bind=\"endereco.numero\"></div></div><div class=\"form-group row\"><div class=\"col-sm-3\"><label>Complemento</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"45\" type=\"text\" value.bind=\"endereco.complemento\"></div><div class=\"col-sm\"><label>Bairro</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"endereco.bairro\"></div></div><div class=\"form-group row\"><div class=\"col-sm\"><label>Município</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"endereco.municipio\"></div><div class=\"col-sm-2\"><label>Estado</label><select class=\"form-control\" value.bind=\"endereco.uf\"><option></option><option repeat.for=\"uf of ufs\" model.bind=\"uf\">${uf}</option></select></div></div><div class=\"form-group row\"><div class=\"col-sm\"><label>Observação</label><textarea autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" rows=\"2\" value.bind=\"endereco.obs\"></textarea></div></div><div class=\"form-group row\"><div class=\"form-check form-check-inline col-sm-1\"><label class=\"form-check-label\"><input checked.bind=\"endereco.entrega\" class=\"form-check-input\" type=\"checkbox\">Entrega</label></div><div class=\"form-check form-check-inline col-sm-9\"><label class=\"form-check-label\"><input checked.bind=\"endereco.cobranca\" class=\"form-check-input\" type=\"checkbox\">Cobrança</label></div><div class=\"col-sm\"></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-link btn-lg form-control float-right\" click.delegate=\"onClickAdd()\" show.bind=\"$last\" title=\"Adicionar endereço\"><span class=\"icon-plus\"></span></button></div></div></div></template>"; });
define('text!resources/elements/form/inputcel.html', ['module'], function(module) { module.exports = "<template><div class=\"form-group row\" repeat.for=\"cel of cels\"><div class=\"col-sm-3\"><label>Telefone celular</label><div class=\"form-check form-check-inline col-sm-1\"><label class=\"form-check-label\"><input checked.bind=\"cel.whatsapp\" class=\"form-check-input\" type=\"checkbox\">WhatsApp</label></div><input autocomplete=\"off\" class=\"form-control\" value.bind=\"cel.numero | celconverter\" maxlength=\"13\" minlength=\"11\" type=\"tel\" pattern=\"[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}\"></div><div class=\"col-sm-7\"><label>Observação</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"cel.obs\"></div><div class=\"col-sm-1\" show.bind=\"cels.length == 1 && $last\"><label>&nbsp;<button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar telefone celular\"><span class=\"icon-plus\"></span></button></label></div><div class=\"col-sm-1\" show.bind=\"cels.length == 1 && $last\"></div><div class=\"col-sm-1\" show.bind=\"cels.length > 1 && !$last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover telefone celular\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"cels.length > 1 && !$last\"></div><div class=\"col-sm-1\" show.bind=\"cels.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover telefone celular\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"cels.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar telefone celular\"><span class=\"icon-plus\"></span></button></div></div></template>"; });
define('text!resources/elements/form/inputcpf.html', ['module'], function(module) { module.exports = "<template><input class=\"form-control\" value.bind=\"numero\" show.bind=\"!obrigatorio\" maxlength=\"14\" minlength=\"11\" type=\"text\" pattern=\"[0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2}\"> <input class=\"form-control\" value.bind=\"numero\" show.bind=\"obrigatorio\" maxlength=\"14\" minlength=\"11\" required type=\"text\" pattern=\"[0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2}\"></template>"; });
define('text!resources/elements/form/inputdata.html', ['module'], function(module) { module.exports = "<template><input class=\"form-control\" value.bind=\"valor\" show.bind=\"!obrigatorio\" maxlength=\"10\" minlength=\"10\" type=\"date\" pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"> <input class=\"form-control\" value.bind=\"valor\" show.bind=\"obrigatorio\" maxlength=\"10\" minlength=\"10\" type=\"date\" required pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"></template>"; });
define('text!resources/elements/form/inputemail.html', ['module'], function(module) { module.exports = "<template><div class=\"form-group row\" repeat.for=\"email of emails\"><div class=\"col-sm\"><label>e-mail</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"email\" value.bind=\"email.mail\"></div><div class=\"col-sm\"><label>Observação</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"email.obs\"></div><div class=\"col-sm-1\" show.bind=\"emails.length == 1 && $last\"><label>&nbsp;<button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar e-mail\"><span class=\"icon-plus\"></span></button></label></div><div class=\"col-sm-1\" show.bind=\"emails.length == 1 && $last\"></div><div class=\"col-sm-1\" show.bind=\"emails.length > 1 && !$last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover e-mail\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"emails.length > 1 && !$last\"></div><div class=\"col-sm-1\" show.bind=\"emails.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover e-mail\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"emails.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar e-mail\"><span class=\"icon-plus\"></span></button></div></div></template>"; });
define('text!resources/elements/form/inputtel.html', ['module'], function(module) { module.exports = "<template><div class=\"form-group row\" repeat.for=\"tel of tels\"><div class=\"col-sm-3\"><label>Telefone fixo</label><input autocomplete=\"off\" class=\"form-control\" value.bind=\"tel.numero | telconverter\" maxlength=\"12\" minlength=\"10\" type=\"text\" pattern=\"[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}\"></div><div class=\"col-sm-7\"><label>Observação</label><input autocomplete=\"off\" class=\"form-control\" maxlength=\"255\" type=\"text\" value.bind=\"tel.obs\"></div><div class=\"col-sm-1\" show.bind=\"tels.length == 1 && $last\"><label>&nbsp;<button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar telefone fixo\"><span class=\"icon-plus\"></span></button></label></div><div class=\"col-sm-1\" show.bind=\"tels.length == 1 && $last\"></div><div class=\"col-sm-1\" show.bind=\"tels.length > 1 && !$last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover telefone fixo\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"tels.length > 1 && !$last\"></div><div class=\"col-sm-1\" show.bind=\"tels.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickDel($index)\" title=\"Remover telefone fixo\"><span class=\"icon-trash-o\"></span></button></div><div class=\"col-sm-1\" show.bind=\"tels.length > 1 && $last\"><label>&nbsp;</label><button type=\"button\" class=\"btn btn-link btn-lg form-control\" click.delegate=\"onClickAdd()\" title=\"Adicionar telefone fixo\"><span class=\"icon-plus\"></span></button></div></div></template>"; });
define('text!views/financeiro/boleto/boleto.html', ['module'], function(module) { module.exports = "<template><spinner is-loading.bind=\"carregando\" huge.bind=\"false\"></spinner><div class=\"row\"><h3 class=\"col\">Boletos do Clube do Livro</h3></div><div class=\"alert alert-warning alert-dismissable fade show\" show.bind=\"mensagem.length > 0\"> ${mensagem} </div><form><div class=\"form-group row\"><div class=\"col-sm-8\"><label>Sacado</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.sacado.pessoa.nome\"></div><div class=\"col-sm-4\"><label>Data de emissão</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.emissaoStr\"></div></div><div class=\"form-group row\"><div class=\"col-sm-4\"><label>Número documento</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div><div class=\"col-sm-4\"><label>Nosso número</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div><div class=\"col-sm-4\"><label>Valor nominal</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div></div><div class=\"form-group row\"><div class=\"col-sm-4\"><label>Data de vencimento</label><input class=\"form-control\" disabled=\"disabled\" type=\"text\" value.bind=\"boleto.vctoStr\"></div><div class=\"col-sm-4\"><label>Data de pagamento</label><input class=\"form-control\" maxlength=\"10\" minlength=\"10\" type=\"text\" input.delegate=\"onInputNascimento()\" value.bind=\"boleto.vctoStr\" pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"></div><div class=\"col-sm-4\"><label>Valor pago</label><input autocomplete=\"off\" class=\"form-control\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div></div><div class=\"form-group row\"><div class=\"col-sm-4\"><label>Valor tarifa</label><input autocomplete=\"off\" class=\"form-control\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div><div class=\"col-sm-4\"><label>Valor creditado</label><input autocomplete=\"off\" class=\"form-control\" type=\"text\" value.bind=\"boleto.valorNominalStr\"></div><div class=\"col-sm-4\"><label>Data de efetivação de crédito</label><input class=\"form-control\" maxlength=\"10\" minlength=\"10\" type=\"text\" input.delegate=\"onInputNascimento()\" value.bind=\"boleto.vctoStr\" pattern=\"^(?:(?:31(\\/)(?:0[13578]|1[02]))\\1|(?:(?:30)(\\/)(?:0[1,3-9]|1[0-2])\\2))\\d{4}$|^(?:[0-2]\\d)(\\/)(?:(?:0[1-9])|(?:1[0-2]))\\3\\d{4}$\"></div></div><div class=\"form-group row\"><div class=\"col-sm-1\"><button type=\"button\" class=\"btn btn-primary\" click.delegate=\"onClickSalvar()\"><span class=\"icon-floppy-o\"></span> <span>Salvar</span></button></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-secondary\" click.delegate=\"onClickCancelar()\"><span class=\"icon-reply\"></span> <span>Cancelar</span></button></div></div></form></template>"; });
define('text!views/financeiro/boleto/boletos.html', ['module'], function(module) { module.exports = "<template><spinner is-loading.bind=\"carregando\" huge.bind=\"false\"></spinner><div class=\"row\"><h3 class=\"col\">Boletos do Clube do Livro</h3></div><form><div class=\"form-group row\"><div class=\"col-sm-3\"><input autocomplete=\"off\" type=\"text\" id=\"nome\" class=\"form-control\" value.bind=\"pesquisa.nome\" placeholder=\"Digite um nome para pesquisa\"></div><label for=\"categoria\" class=\"col-sm-1 col-form-label\">Categoria</label><div><select id=\"categoria\" class=\"form-control\" value.bind=\"pesquisa.categoria\"><option></option><option repeat.for=\"categoria of categorias\" model.bind=\"categoria.id\">${categoria.nome}</option></select></div><label for=\"situacao\" class=\"col-sm-1 col-form-label\">Situação</label><div><select id=\"situacao\" class=\"form-control\" value.bind=\"pesquisa.situacao\"><option></option><option repeat.for=\"situacao of situacoes\" model.bind=\"situacao.id\">${situacao.nome}</option></select></div><div class=\"col-sm\"><button type=\"button\" class=\"btn btn-secondary\" click.delegate=\"onClickLimpar()\"><span class=\"icon-eraser\"></span> <span>Limpar</span></button> <button type=\"button\" class=\"btn btn-primary\" click.delegate=\"onClickPesquisar()\"><span class=\"icon-filter\"></span> <span>Filtrar</span></button></div></div></form><div class=\"table-responsive\" show.bind=\"boletos.length > 0\"><table class=\"table table-hover\"><thead><th>Nome</th><th>Categoria</th><th>N&ordm; boleto</th><th>N&ordm; banco</th><th>Valor</th><th>Valor pago</th><th>Vcto</th><th>Situação</th></thead><tbody><tr repeat.for=\"item of boletos\"><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.sacado.pessoa.nome}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.sacado.classificacao.nome}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.numeroBeneficiario}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.numeroBanco}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.valorNominalStr}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.valorPagoStr}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\">${item.vctoStr}</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\" show.bind=\"item.situacao == 0\">Aberto</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\" show.bind=\"item.situacao == 1\">Baixado</td><td class=\"item-grid\" click.delegate=\"onClickEditar(item.id)\" show.bind=\"item.situacao == 2\">Baixado manualmente</td></tr></tbody></table></div><div class=\"alert alert-info alert-dismissable fade show\" show.bind=\"mensagem.length > 0\"> ${mensagem} </div></template>"; });
define('text!util/dialog/alerta.1.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-exclamation-triangle\">&nbsp;</span> <span>Atenção</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!util/alerta.1.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-exclamation-triangle\">&nbsp;</span> <span>Atenção</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
define('text!util/dialog.html', ['module'], function(module) { module.exports = "<template><ux-dialog><ux-dialog-header class=\"dialog\"><span class=\"icon-exclamation-triangle\" if.bind=\"tipo == 'alerta'\">&nbsp;</span> <span if.bind=\"tipo == 'alerta'\">Atenção</span> <span class=\"icon-times-circle\" if.bind=\"tipo == 'erro'\">&nbsp;</span> <span if.bind=\"tipo == 'erro'\">Erro</span> <span class=\"icon-exclamation-circle\" if.bind=\"tipo == 'info'\">&nbsp;</span> <span if.bind=\"tipo == 'info'\">Informação</span> <span class=\"icon-check-circle\" if.bind=\"tipo == 'sucesso'\">&nbsp;</span> <span if.bind=\"tipo == 'sucesso'\">Sucesso</span></ux-dialog-header><ux-dialog-body>${mensagem}</ux-dialog-body><ux-dialog-footer><button class=\"btn btn-secondary\" click.delegate=\"controller.close()\">Fechar</button></ux-dialog-footer></ux-dialog></template>"; });
//# sourceMappingURL=app-bundle.js.map