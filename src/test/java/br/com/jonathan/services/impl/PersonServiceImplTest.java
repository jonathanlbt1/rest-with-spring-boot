package br.com.jonathan.services.impl;

import br.com.jonathan.data.vo.v1.PersonVO;
import br.com.jonathan.entities.Person;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.mapper.PersonMapper;
import br.com.jonathan.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PersonServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PersonServiceImplTest {

    @MockBean
    private PersonMapper personMapper;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonServiceImpl personServiceImpl;

    @Test
    void testThat_FindAll_ReturnRightProperties() {
        // Arrange
        ArrayList<PersonVO> personVOList = new ArrayList<>();
        when(personMapper.toPersonList(Mockito.any())).thenReturn(personVOList);
        when(personRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<PersonVO> actualFindAllResult = personServiceImpl.findAll();

        // Assert
        verify(personMapper).toPersonList(isA(List.class));
        verify(personRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(personVOList, actualFindAllResult);
    }


    @Test
    void testThat_FindAll_ThrowsException() {
        // Arrange
        when(personRepository.findAll()).thenThrow(new ResourceNotFoundException("People not found!"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.findAll());
        verify(personRepository).findAll();
    }


    @Test
    void testThat_FindById_ReturnsAPerson() {
        // Arrange
        PersonVO personVO = new PersonVO();
        when(personMapper.toPerson(Mockito.any())).thenReturn(personVO);

        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        PersonVO actualFindByIdResult = personServiceImpl.findById(1L);

        // Assert
        verify(personMapper).toPerson(isA(Person.class));
        verify(personRepository).findById(eq(1L));
        assertSame(personVO, actualFindByIdResult);
    }


    @Test
    void testThat_FindById_ThrowsException() {
        // Arrange
        when(personMapper.toPerson(Mockito.any())).thenThrow(new ResourceNotFoundException("Person Not Found!"));

        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.findById(1L));
        verify(personMapper).toPerson(isA(Person.class));
        verify(personRepository).findById(eq(1L));
    }


    @Test
    void testThat_FindById_ReturnsNotFound() {
        // Arrange
        Optional<Person> emptyResult = Optional.empty();
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.findById(1L));
        verify(personRepository).findById(eq(1L));
    }


    @Test
    void testThat_Create_CreatesObject() {
        // Arrange
        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        PersonVO personVO = new PersonVO();
        when(personMapper.toPerson(Mockito.any())).thenReturn(personVO);
        when(personMapper.toPersonVO(Mockito.any())).thenReturn(person);

        Person person2 = new Person();
        person2.setAddress("42 Main St");
        person2.setFirstName("Jane");
        person2.setGender("Gender");
        person2.setId(1L);
        person2.setLastName("Doe");
        when(personRepository.save(Mockito.any())).thenReturn(person2);

        // Act
        PersonVO actualCreateResult = personServiceImpl.create(new PersonVO());

        // Assert
        verify(personMapper).toPerson(isA(Person.class));
        verify(personMapper).toPersonVO(isA(PersonVO.class));
        verify(personRepository).save(isA(Person.class));
        assertSame(personVO, actualCreateResult);
    }


    @Test
    void testThat_Create_ThrowsException() {
        // Arrange
        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        when(personMapper.toPersonVO(Mockito.any())).thenReturn(person);
        when(personRepository.save(Mockito.any())).thenThrow(new ResourceNotFoundException("Person Not Found!"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.create(new PersonVO()));
        verify(personMapper).toPersonVO(isA(PersonVO.class));
        verify(personRepository).save(isA(Person.class));
    }


    @Test
    void testThat_Update_UpdatesObject() {
        // Arrange
        PersonVO personVO = new PersonVO();
        when(personMapper.toPerson(Mockito.any())).thenReturn(personVO);

        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);

        Person person2 = new Person();
        person2.setAddress("42 Main St");
        person2.setFirstName("Jane");
        person2.setGender("Gender");
        person2.setId(1L);
        person2.setLastName("Doe");
        when(personRepository.save(Mockito.any())).thenReturn(person2);
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        PersonVO actualUpdateResult = personServiceImpl.update(new PersonVO());

        // Assert
        verify(personMapper).toPerson(isA(Person.class));
        verify(personRepository).findById(isNull());
        verify(personRepository).save(isA(Person.class));
        assertSame(personVO, actualUpdateResult);
    }


    @Test
    void testThat_Update_ReturnsPersonNotFound() {
        // Arrange
        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.save(Mockito.any())).thenThrow(new ResourceNotFoundException("Person Not Found!"));
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.update(new PersonVO()));
        verify(personRepository).findById(isNull());
        verify(personRepository).save(isA(Person.class));
    }


    @Test
    void testThat_Delete_DeletesAnObject() {
        // Arrange
        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        doNothing().when(personRepository).delete(Mockito.any());
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        personServiceImpl.delete(1L);

        // Assert that nothing has changed
        verify(personRepository).delete(isA(Person.class));
        verify(personRepository).findById(eq(1L));
        assertTrue(personServiceImpl.findAll().isEmpty());
    }


    @Test
    void testThat_Delete_ThrowsException() {
        // Arrange
        Person person = new Person();
        person.setAddress("42 Main St");
        person.setFirstName("Jane");
        person.setGender("Gender");
        person.setId(1L);
        person.setLastName("Doe");
        Optional<Person> ofResult = Optional.of(person);
        doThrow(new ResourceNotFoundException("Person Not Found!")).when(personRepository).delete(Mockito.any());
        when(personRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> personServiceImpl.delete(1L));
        verify(personRepository).delete(isA(Person.class));
        verify(personRepository).findById(eq(1L));
    }

}
