package br.com.jonathan.services.impl;

import br.com.jonathan.controllers.PersonController;
import br.com.jonathan.data.vo.v1.PersonDTO;
import br.com.jonathan.data.vo.v2.PersonVOV2;
import br.com.jonathan.exceptions.RequiredObjectIsNullException;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.mapper.PersonMapper;
import br.com.jonathan.repositories.PersonRepository;
import br.com.jonathan.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServiceImpl implements PersonService {

    private final Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
    private static final String MESSAGE = "Person not found";

    PersonMapper personMapper;
    PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper, PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        var voList = personMapper.toPersonList(personRepository.findAll());
        voList.forEach(
                p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return voList;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        var vo = personMapper.toPerson(entity);
        return vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    }

    public PersonDTO create(PersonDTO person) {
        if (Objects.isNull(person)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one person!");
        var entity = personMapper.toPersonVO(person);
        var vo = personMapper.toPerson(entity);
        return vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

    }

    @Override
    public PersonVOV2 createV2(PersonVOV2 person) {
        if (Objects.isNull(person)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one person with V2!");
        var entity = personMapper.toPersonVOV2(person);
        return personMapper.toPersonV2(personRepository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {
        if (Objects.isNull(person)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating one person!");
        var personToUpdate = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setGender(person.getGender());
        var vo = personMapper.toPerson(personRepository.save(personToUpdate));
        return vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var personToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE));
        personRepository.delete(personToDelete);
    }

}
