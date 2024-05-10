package br.com.jonathan.services.impl;

import br.com.jonathan.exceptions.UnsupportedMathOperationException;
import br.com.jonathan.services.ConversionService;
import br.com.jonathan.services.MathOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathOperationServiceImpl implements MathOperationService {

    private final ConversionService conversionService;

    @Autowired
    public MathOperationServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Double sum(String firstNumber, String secondNumber) {
        if (!conversionService.isNumeric(firstNumber) || !conversionService.isNumeric(secondNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return conversionService.convertToDouble(firstNumber) + conversionService.convertToDouble(firstNumber);
    }

    @Override
    public Double subtraction(String firstNumber, String secondNumber) {
        if (!conversionService.isNumeric(firstNumber) || !conversionService.isNumeric(secondNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return conversionService.convertToDouble(firstNumber) - conversionService.convertToDouble(secondNumber);
    }

    @Override
    public Double multiplication(String firstNumber, String secondNumber) {
        if (!conversionService.isNumeric(firstNumber) || !conversionService.isNumeric(secondNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return conversionService.convertToDouble(firstNumber) * conversionService.convertToDouble(secondNumber);
    }

    @Override
    public Double division(String firstNumber, String secondNumber) {
        if (!conversionService.isNumeric(firstNumber) || !conversionService.isNumeric(secondNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return conversionService.convertToDouble(firstNumber) / conversionService.convertToDouble(secondNumber);
    }

    @Override
    public Double average(String firstNumber, String secondNumber) {
        if (!conversionService.isNumeric(firstNumber) || !conversionService.isNumeric(secondNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return (conversionService.convertToDouble(firstNumber) + conversionService.convertToDouble(secondNumber)) / 2;
    }

    @Override
    public Double squareRoot(String number) {
        if (!conversionService.isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return Math.sqrt(conversionService.convertToDouble(number));
    }
}
