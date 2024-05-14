package br.com.jonathan.mapper;


import br.com.jonathan.data.vo.v1.PersonDTO;
import br.com.jonathan.data.vo.v2.PersonVOV2;
import br.com.jonathan.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "id", target = "key")
    PersonDTO toPerson(Person person);

    @Mapping(source = "key", target = "id")
    Person toPersonVO(PersonDTO personDTO);

    List<Person> toPersonVOList(List<PersonDTO> people);
    List<PersonDTO> toPersonList(List<Person> people);

    PersonVOV2 toPersonV2(Person person);
    Person toPersonVOV2(PersonVOV2 person);

}
