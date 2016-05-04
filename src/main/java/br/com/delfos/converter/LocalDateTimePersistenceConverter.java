package br.com.delfos.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;

public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime arg0) {
		if (arg0 != null) { 
			return Timestamp.valueOf(arg0);
		}
		return null;
	}


	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
