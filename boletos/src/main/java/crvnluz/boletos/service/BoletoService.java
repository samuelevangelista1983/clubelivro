package crvnluz.boletos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crvnluz.boletos.entidade.Boleto;
import crvnluz.boletos.entidade.Sacado;
import crvnluz.boletos.repositorio.BoletoRepositorio;
import crvnluz.boletos.repositorio.SacadoRepositorio;

@Service
public class BoletoService {
	
	private final Logger logger = LoggerFactory.getLogger(BoletoService.class);
	
	@Autowired
	private BoletoRepositorio boletoRepositorio;
	@Autowired
	private SacadoRepositorio sacadoRepositorio;
	
	// MÉTODOS PÚBLICOS
	
	public void processar(Boleto boleto) {
		try {
			Sacado sacado = boleto.getSacado();
			Sacado sacadoEncontrado = sacadoRepositorio.findByDocumento(sacado.getDocumento());
			
			if (sacadoEncontrado != null) {
				sacadoEncontrado.setBairro(sacado.getBairro());
				sacadoEncontrado.setCep(sacado.getCep());
				sacadoEncontrado.setCidade(sacado.getCidade());
				sacadoEncontrado.setComplemento(sacado.getComplemento());
				sacadoEncontrado.setLogradouro(sacado.getLogradouro());
				sacadoEncontrado.setNome(sacado.getNome());
				sacadoEncontrado.setNumero(sacado.getNumero());
				sacadoEncontrado.setUf(sacado.getUf());
				sacado = sacadoEncontrado;
				boleto.setSacado(sacado);
			}
			
			Boleto boletoEncontrado = boletoRepositorio.findByNumeroBanco(boleto.getNumeroBanco());
			
			if (boletoEncontrado != null) {
				boletoEncontrado.setEfetivacaoCredito(boleto.getEfetivacaoCredito());
				boletoEncontrado.setEmissao(boleto.getEmissao());
				boletoEncontrado.setMensagemFichaCompensacao(boleto.getMensagemFichaCompensacao());
				boletoEncontrado.setMensagemReciboSacado(boleto.getMensagemReciboSacado());
				boletoEncontrado.setNumeroBanco(boleto.getNumeroBanco());
				boletoEncontrado.setPgto(boleto.getPgto());
				boletoEncontrado.setSacado(boleto.getSacado());
				boletoEncontrado.setSituacao(boleto.getSituacao());
				boletoEncontrado.setValorCreditado(boleto.getValorCreditado());
				boletoEncontrado.setValorNominal(boleto.getValorNominal());
				boletoEncontrado.setValorPago(boleto.getValorPago());
				boletoEncontrado.setValorTarifa(boleto.getValorTarifa());
				boletoEncontrado.setVcto(boleto.getVcto());
				boleto = boletoEncontrado;
			}
			
			sacado = sacadoRepositorio.save(sacado);
			boletoRepositorio.save(boleto);
			
		} catch (Throwable t) {
			logger.error(String.format("Ocorreu um erro ao processar o boleto número %s", boleto.getNumeroBanco()), t);
		}
	}
	
}
