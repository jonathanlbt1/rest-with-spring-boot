package br.com.jonathan.services;

import br.com.jonathan.data.vo.v1.PersonDTO;
import br.com.jonathan.data.vo.v2.PersonVOV2;

import java.util.List;


public interface PersonService {

    List<PersonDTO> findAll();

    PersonDTO findById(Long id);

    PersonDTO create(PersonDTO person);
    PersonVOV2 createV2(PersonVOV2 person);

    PersonDTO update(PersonDTO person);

    void delete(Long id);

}

