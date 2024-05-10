package br.com.jonathan.controllers;


import java.util.concurrent.atomic.AtomicLong;

import br.com.jonathan.services.MathOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class MathController {


	private final MathOperationService mathOperationService;

	@Autowired
    public MathController(MathOperationService mathOperationService) {
        this.mathOperationService = mathOperationService;
    }

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathOperationService.sum(numberOne, numberTwo);
	}

	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathOperationService.subtraction(numberOne, numberTwo);
	}

	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathOperationService.multiplication(numberOne, numberTwo);
	}

	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathOperationService.division(numberOne, numberTwo);
	}

	@RequestMapping(value = "/average/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double average(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathOperationService.average(numberOne, numberTwo);
	}

	@RequestMapping(value = "/square/{numberOne}", method = RequestMethod.GET)
	public Double square(
			@PathVariable(value = "numberOne") String numberOne) throws Exception {
		return mathOperationService.squareRoot(numberOne);
	}


}
