package br.com.jonathan.services.impl;

import br.com.jonathan.controllers.BookController;
import br.com.jonathan.data.vo.v1.BookDTO;
import br.com.jonathan.exceptions.RequiredObjectIsNullException;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.mapper.BookMapper;
import br.com.jonathan.repositories.BookRepository;
import br.com.jonathan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServiceImpl implements BookService {

    private final Logger logger = Logger.getLogger(BookServiceImpl.class.getName());
    public static final String MESSAGE_BOOK = "Book not found";

    BookMapper bookMapper;
    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> findAll() {
        logger.info("Finding all books!");
        var bookDTOList = bookMapper.toBookDTOList(bookRepository.findAll());
        bookDTOList.forEach(
                p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return bookDTOList;
    }

    @Override
    public BookDTO findById(Long id) {
        logger.info("Finding one book!");
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE_BOOK));
        var bookDTO = bookMapper.toBookDTO(book);
        return bookDTO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    }

    @Override
    public BookDTO create(BookDTO book) {
        if (Objects.isNull(book)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one book!");
        var entity = bookMapper.toBook(book);
        var vo = bookMapper.toBookDTO(entity);

        return vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
    }

    @Override
    public BookDTO update(BookDTO book) {
        if (Objects.isNull(book)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating one book!");
        var bookEntity = bookRepository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE_BOOK));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setEditor(book.getEditor());
        bookEntity.setPages(book.getPages());

        var vo = bookMapper.toBookDTO(bookRepository.save(bookEntity));
        return vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting one book!");
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE_BOOK));
        bookRepository.delete(book);
    }
}
