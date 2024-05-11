package br.com.jonathan.services;

import br.com.jonathan.entities.Person;

import java.util.List;


public interface PersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person create(Person person);

    Person update(Person person);

    void delete(Long id);

    Person mockPerson(int i);
}

