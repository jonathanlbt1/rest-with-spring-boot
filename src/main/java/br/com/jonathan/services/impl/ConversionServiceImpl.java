package br.com.jonathan.services.impl;

import br.com.jonathan.services.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConversionServiceImpl implements ConversionService {

    public Double convertToDouble(String strNumber) {
        if (Objects.isNull(strNumber)) {
            return 0D;
        }
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) {
            return Double.parseDouble(number);
        }
        return null;
    }

    public boolean isNumeric(String strNumber) {
        if (Objects.isNull(strNumber)) {
            return false;
        }
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
