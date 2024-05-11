package br.com.jonathan.services.impl;

import br.com.jonathan.entities.Person;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.repositories.PersonRepository;
import br.com.jonathan.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServiceImpl implements PersonService {

    private final Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
    private static final String MESSAGE = "Person not found";

    PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        logger.info("Finding all people!");
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person!");
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
    }

    public Person create(Person person) {
        logger.info("Creating one person!");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person!");
        var personToUpdate = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setGender(person.getGender());
        return personRepository.save(personToUpdate);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var personToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personRepository.delete(personToDelete);
    }

    public Person mockPerson(int i) {
        Person person = new Person();
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brasil " + i);
        person.setGender("Male");
        return person;
    }
}
