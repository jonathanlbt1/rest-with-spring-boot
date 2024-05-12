package br.com.jonathan.services;

import br.com.jonathan.data.vo.v1.PersonVO;

import java.util.List;


public interface PersonService {

    List<PersonVO> findAll();

    PersonVO findById(Long id);

    PersonVO create(PersonVO person);

    PersonVO update(PersonVO person);

    void delete(Long id);

    PersonVO mockPerson(int i);
}

