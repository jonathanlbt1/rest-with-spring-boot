package br.com.jonathan.mapper;

import br.com.jonathan.data.vo.v1.BookDTO;
import br.com.jonathan.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "key")
    BookDTO toBookDTO(Book book);

    @Mapping(source = "key", target = "id")
    Book toBook(BookDTO bookDTO);

    List<BookDTO> toBookDTOList(List<Book> books);
    List<Book> toBookList(List<BookDTO> books);
}
