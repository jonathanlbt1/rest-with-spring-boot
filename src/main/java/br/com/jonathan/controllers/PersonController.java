package br.com.jonathan.controllers;

import br.com.jonathan.data.vo.v1.PersonVO;
import br.com.jonathan.data.vo.v2.PersonVOV2;
import br.com.jonathan.services.PersonService;
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
@RequestMapping("/api/person/v1")
@Tag(name = "Person Endpoint", description = "Endpoints to Manage Persons")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find all Persons recorded in the database",
            description = "Find all Persons recorded in the database",
            tags = {"Person Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Persons found",
                            content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
                            }),
                    @ApiResponse(responseCode = "404", description = "Persons not found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public List<PersonVO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds one person by ID",
            description = "Finds one person by ID in the database",
            tags = {"Person Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Persons found",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Persons not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Creates one person in the database",
            description = "Creates one person in the database",
            tags = {"Person Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person created",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Persons not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public PersonVO create(@RequestBody PersonVO person) {
        return service.create(person);
    }

    @PostMapping(value = "/v2",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
        return service.createV2(person);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a person by ID in the database",
            description = "Updates a person by ID in the database",
            tags = {"Person Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person updated",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Persons not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    public PersonVO update(@RequestBody PersonVO person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a person by ID from the database",
            description = "Deletes a person by ID from the database",
            tags = {"Person Endpoint"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Persons not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
