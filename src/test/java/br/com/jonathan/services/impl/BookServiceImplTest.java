package br.com.jonathan.services.impl;

import br.com.jonathan.data.vo.v1.BookDTO;
import br.com.jonathan.entities.Book;
import br.com.jonathan.exceptions.RequiredObjectIsNullException;
import br.com.jonathan.exceptions.ResourceNotFoundException;
import br.com.jonathan.mapper.BookMapper;
import br.com.jonathan.repositories.BookRepository;
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

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookServiceImplTest {

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Test
    void testThat_FindAll_ReturnsAList() {
        // Arrange
        ArrayList<BookDTO> bookDTOList = new ArrayList<>();
        when(bookMapper.toBookDTOList(Mockito.any())).thenReturn(bookDTOList);
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<BookDTO> actualFindAllResult = bookServiceImpl.findAll();

        // Assert
        verify(bookMapper).toBookDTOList(isA(List.class));
        verify(bookRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(bookDTOList, actualFindAllResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#findAll()}
     */
    @Test
    void testThat_FindAll_ThrowsException() {
        // Arrange
        when(bookRepository.findAll()).thenThrow(new RequiredObjectIsNullException("Throw Null Exception!"));

        // Act and Assert
        assertThrows(RequiredObjectIsNullException.class, () -> bookServiceImpl.findAll());
        verify(bookRepository).findAll();
    }

    /**
     * Method under test: {@link BookServiceImpl#findAll()}
     */
    @Test
    void testThat_FindAll_ReturnsConvertedObject() {
        // Arrange
        ArrayList<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(new BookDTO());
        when(bookMapper.toBookDTOList(Mockito.any())).thenReturn(bookDTOList);
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<BookDTO> actualFindAllResult = bookServiceImpl.findAll();

        // Assert
        verify(bookMapper).toBookDTOList(isA(List.class));
        verify(bookRepository).findAll();
        assertEquals(1, actualFindAllResult.size());
        assertSame(bookDTOList, actualFindAllResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#findAll()}
     */
    @Test
    void testThat_FindAll_ReturnsCompletedList() {
        // Arrange
        ArrayList<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(new BookDTO());
        bookDTOList.add(new BookDTO());
        when(bookMapper.toBookDTOList(Mockito.any())).thenReturn(bookDTOList);
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<BookDTO> actualFindAllResult = bookServiceImpl.findAll();

        // Assert
        verify(bookMapper).toBookDTOList(isA(List.class));
        verify(bookRepository).findAll();
        assertEquals(2, actualFindAllResult.size());
        assertSame(bookDTOList, actualFindAllResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#findById(Long)}
     */
    @Test
    void testThat_FindById_ReturnsTheRightObject() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        when(bookMapper.toBookDTO(Mockito.any())).thenReturn(bookDTO);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        BookDTO actualFindByIdResult = bookServiceImpl.findById(1L);

        // Assert
        verify(bookMapper).toBookDTO(isA(Book.class));
        verify(bookRepository).findById(eq(1L));
        assertSame(bookDTO, actualFindByIdResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#findById(Long)}
     */
    @Test
    void testThat_FindById_ThrowsException() {
        // Arrange
        when(bookMapper.toBookDTO(Mockito.any())).thenThrow(new RequiredObjectIsNullException("{UUU}"));

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RequiredObjectIsNullException.class, () -> bookServiceImpl.findById(1L));
        verify(bookMapper).toBookDTO(isA(Book.class));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#findById(Long)}
     */
    @Test
    void testThat_FindById_ReturnsMessageNotFound() {
        // Arrange
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.findById(1L));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#create(BookDTO)}
     */
    @Test
    void testThat_Create_CreatesAnObject() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        BookDTO bookDTO = new BookDTO();
        when(bookMapper.toBookDTO(Mockito.any())).thenReturn(bookDTO);
        when(bookMapper.toBook(Mockito.any())).thenReturn(book);
        BookDTO book2 = new BookDTO();

        // Act
        BookDTO actualCreateResult = bookServiceImpl.create(book2);

        // Assert
        verify(bookMapper).toBook(isA(BookDTO.class));
        verify(bookMapper).toBookDTO(isA(Book.class));
        assertTrue(book2.getLinks().toList().isEmpty());
        assertSame(bookDTO, actualCreateResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#create(BookDTO)}
     */
    @Test
    void testThat_Create_CreatesTheRightObject() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setKey(1L);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        when(bookMapper.toBookDTO(Mockito.any())).thenReturn(bookDTO);
        when(bookMapper.toBook(Mockito.any())).thenReturn(book);
        BookDTO book2 = new BookDTO();

        // Act
        BookDTO actualCreateResult = bookServiceImpl.create(book2);

        // Assert
        verify(bookMapper).toBook(isA(BookDTO.class));
        verify(bookMapper).toBookDTO(isA(Book.class));
        assertTrue(book2.getLinks().toList().isEmpty());
        assertSame(bookDTO, actualCreateResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#create(BookDTO)}
     */
    @Test
    void testThat_Create3_ThrowsException() {
        // Arrange, Act and Assert
        assertThrows(RequiredObjectIsNullException.class, () -> bookServiceImpl.create(null));
    }

    /**
     * Method under test: {@link BookServiceImpl#create(BookDTO)}
     */
    @Test
    void testThat_Create_ThrowsNotFoundException() {
        // Arrange
        when(bookMapper.toBook(Mockito.any())).thenThrow(new ResourceNotFoundException("Creating one book!"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.create(new BookDTO()));
        verify(bookMapper).toBook(isA(BookDTO.class));
    }

    /**
     * Method under test: {@link BookServiceImpl#update(BookDTO)}
     */
    @Test
    void testThat_Update_UpdatesAnObject() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        when(bookMapper.toBookDTO(Mockito.any())).thenReturn(bookDTO);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setEditor("Editor");
        book2.setId(1L);
        book2.setPages(1);
        book2.setTitle("Dr");
        when(bookRepository.save(Mockito.any())).thenReturn(book2);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        BookDTO book3 = new BookDTO();

        // Act
        BookDTO actualUpdateResult = bookServiceImpl.update(book3);

        // Assert
        verify(bookMapper).toBookDTO(isA(Book.class));
        verify(bookRepository).findById(isNull());
        verify(bookRepository).save(isA(Book.class));
        assertTrue(book3.getLinks().toList().isEmpty());
        assertSame(bookDTO, actualUpdateResult);
    }

    /**
     * Method under test: {@link BookServiceImpl#update(BookDTO)}
     */
    @Test
    void testThat_Update_ThrowsException() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.save(Mockito.any())).thenThrow(new RequiredObjectIsNullException("{UUU}"));
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RequiredObjectIsNullException.class, () -> bookServiceImpl.update(new BookDTO()));
        verify(bookRepository).findById(isNull());
        verify(bookRepository).save(isA(Book.class));
    }

    /**
     * Method under test: {@link BookServiceImpl#update(BookDTO)}
     */
    @Test
    void testThat_Update_ThrowsNotFoundMessage() {
        // Arrange
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.update(new BookDTO()));
        verify(bookRepository).findById(isNull());
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(Long)}
     */
    @Test
    void testThat_Delete_DeletesAnObject() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        doNothing().when(bookRepository).delete(Mockito.any());
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        bookServiceImpl.delete(1L);

        // Assert that nothing has changed
        verify(bookRepository).delete(isA(Book.class));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(Long)}
     */
    @Test
    void testThat_Delete_ThrowsException() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setEditor("Editor");
        book.setId(1L);
        book.setPages(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        doThrow(new RequiredObjectIsNullException("Deleting one book!")).when(bookRepository).delete(Mockito.any());
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RequiredObjectIsNullException.class, () -> bookServiceImpl.delete(1L));
        verify(bookRepository).delete(isA(Book.class));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(Long)}
     */
    @Test
    void testThat_Delete_ReturnsNotFoundException() {
        // Arrange
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.delete(1L));
        verify(bookRepository).findById(eq(1L));
    }
}
