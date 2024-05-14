package br.com.jonathan.mapper;


import br.com.jonathan.data.vo.v1.PersonVO;
import br.com.jonathan.data.vo.v2.PersonVOV2;
import br.com.jonathan.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "id", target = "key")
    PersonVO toPerson(Person person);

    @Mapping(source = "key", target = "id")
    Person toPersonVO(PersonVO personVO);

    List<Person> toPersonVOList(List<PersonVO> people);
    List<PersonVO> toPersonList(List<Person> people);

    PersonVOV2 toPersonV2(Person person);
    Person toPersonVOV2(PersonVOV2 person);

}
