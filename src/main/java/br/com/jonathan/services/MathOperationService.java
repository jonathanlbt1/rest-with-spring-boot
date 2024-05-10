package br.com.jonathan.services;

public interface MathOperationService {

    Double sum(String firstNumber, String secondNumber);

    Double subtraction(String firstNumber, String secondNumber);

    Double multiplication(String firstNumber, String secondNumber);

    Double division(String firstNumber, String secondNumber);

    Double average(String firstNumber, String secondNumber);

    Double squareRoot(String number);
}
