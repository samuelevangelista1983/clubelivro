export function configure(config) {
  config.globalResources([
    'resources/elements/form/endereco',
    'resources/elements/form/inputcel',
    'resources/elements/form/inputcpf',
    'resources/elements/form/inputdata',
    'resources/elements/form/inputemail',
    'resources/elements/form/inputtel',
    'resources/elements/spinner',
    'resources/value-converters/cpfconverter',
    'resources/value-converters/cepconverter',
    'resources/value-converters/celconverter',
    'resources/value-converters/telconverter',
    'resources/value-converters/dateconverter'
  ]);
}
