package br.com.jonathan.services;

import br.com.jonathan.data.vo.v1.PersonVO;
import br.com.jonathan.data.vo.v2.PersonVOV2;

import java.util.List;


public interface PersonService {

    List<PersonVO> findAll();

    PersonVO findById(Long id);

    PersonVO create(PersonVO person);
    PersonVOV2 createV2(PersonVOV2 person);

    PersonVO update(PersonVO person);

    void delete(Long id);

    PersonVO mockPerson(int i);
}

