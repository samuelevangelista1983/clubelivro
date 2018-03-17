package br.org.crvnluz.editora.clubelivro.infra.persistencia.converter;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BigDecimalConverter implements AttributeConverter<BigDecimal, Double> {
	
	@Override
	public Double convertToDatabaseColumn(BigDecimal bigDecimal) {
		return Optional.ofNullable(bigDecimal).map(BigDecimal::doubleValue).orElse(null);
	}
	
	@Override
	public BigDecimal convertToEntityAttribute(Double valor) {
		return Optional.ofNullable(valor).map(v -> new BigDecimal(v)).orElse(null);
	}
	
}
