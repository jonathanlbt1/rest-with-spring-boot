package br.com.jonathan.controllers;

import br.com.jonathan.data.vo.v1.BookDTO;
import br.com.jonathan.data.vo.v1.PersonDTO;
import br.com.jonathan.services.BookService;
import br.com.jonathan.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book Endpoint", description = "Endpoints to Manage Books")
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Find all Books recorded in the database",
            description = "Find all Books recorded in the database",
            tags = {"Book Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books found",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON,
                                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))),
                            }),
                    @ApiResponse(responseCode = "404", description = "Books not found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public List<BookDTO> findAll() {
        return service.findAll();
    }


    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds one book by ID",
            description = "Finds one book by ID in the database",
            tags = {"Book Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books found",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Books not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public BookDTO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Creates one book in the database",
            description = "Creates one book in the database",
            tags = {"Book Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book created",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public BookDTO create(@RequestBody BookDTO book) {
        return service.create(book);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(summary = "Updates a book by ID in the database",
            description = "Updates a book by ID in the database",
            tags = {"Book Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public BookDTO update(@RequestBody BookDTO book) {
        return service.update(book);
    }


    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a book by ID in the database",
            description = "Deletes a book by ID in the database",
            tags = {"Book Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
