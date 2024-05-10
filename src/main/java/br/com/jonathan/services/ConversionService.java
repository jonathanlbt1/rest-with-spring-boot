package br.com.jonathan.services;

public interface ConversionService {

    Double convertToDouble(String strNumber);

    boolean isNumeric(String strNumber);
}
