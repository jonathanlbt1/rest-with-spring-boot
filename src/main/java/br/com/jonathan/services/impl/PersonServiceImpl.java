package br.com.jonathan.services.impl;

import br.com.jonathan.data.vo.v1.PersonVO;
import br.com.jonathan.data.vo.v2.PersonVOV2;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.mapper.PersonMapper;
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

    private final PersonMapper personMapper;
    PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper, PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        return personMapper.toPersonList(personRepository.findAll());
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        return personMapper.toPerson(entity);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");
        var entity = personMapper.toPersonVO(person);
        return personMapper.toPerson(personRepository.save(entity));

    }

    @Override
    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with V2!");
        var entity = personMapper.toPersonVOV2(person);
        return personMapper.toPersonV2(personRepository.save(entity));
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person!");
        var personToUpdate = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setGender(person.getGender());
        return personMapper.toPerson(personRepository.save(personToUpdate));
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var personToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personRepository.delete(personToDelete);
    }

    public PersonVO mockPerson(int i) {
        PersonVO person = new PersonVO();
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brasil " + i);
        person.setGender("Male");
        return person;
    }

}
