package com.mic3.personservice.rest;

import com.mic3.personservice.dto.PersonDTO;
import com.mic3.personservice.service.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Syed Wajid
 * Rest api controller for person bean
 */

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = {"/api/v1/persons"}, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)

public class PersonController {

    private static final String NEW_PERSON_LOG = "New person was created id:{}";
    private static final String PERSON_UPDATED_LOG = "Person:{} was updated";
    private static final String PERSON_DELETED_LOG = "Person with id:{} was deleted";

    /**
     * Person service reference
     */
    private IPersonService personService;

    /**
     * Returns a list of persons and sorted based on the query parameters
     * @param pageable
     * @return ResponseEntity<Page<PersonDTO>>
     */
    @Operation(summary = "Returns a list of persons and sorted based on the query parameters")
    @ApiResponse(responseCode = "200", description = "List of persons",
                 content = {@Content(schema = @Schema(implementation = Page.class))})
    @GetMapping
    public ResponseEntity<Page<PersonDTO>> getPersons(Pageable pageable){
        Page<PersonDTO> result = personService.list(pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * Rest api for creating person
     * @param person
     * @return ResponseEntity<PersonDTO>
     */
    @Operation(summary = "Crate a new person")
    @ApiResponse(responseCode = "201", description = "Person is created",
                 content = {@Content(schema = @Schema(implementation = PersonDTO.class))})
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(
            @Valid @RequestBody PersonDTO person) {
        final PersonDTO createdPerson = personService.create(person);
        log.info(NEW_PERSON_LOG, createdPerson.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    /**
     * Fetch person by personId
     * @param personId
     * @return ResponseEntity<PersonDTO>
     */
    @Operation(summary = "Get a person by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the person",
                                content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)})
    @GetMapping(path = "/{personId}")
    public ResponseEntity<PersonDTO> list(@PathVariable(required = true) Long personId) {
        return ResponseEntity.ok(personService.findById(personId));
    }

    /**
     * Update person object
     * @param personId
     * @param person
     * @return ResponseEntity<PersonDTO>
     */
    @Operation(summary = "Update a person by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Person was updated",
                            content = {@Content(schema = @Schema(implementation = PersonDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)})
    @PutMapping(path = "/{personId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable long personId,
                                                  @Valid @RequestBody PersonDTO person) {
        PersonDTO updatedPerson = personService.update(personId, person);
        log.info(PERSON_UPDATED_LOG, updatedPerson.toString());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedPerson);
    }

    /**
     * Delete person object
     * @param personId
     * @return ResponseEntity
     */
    @Operation(summary = "Delete person by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Person was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)})
    @DeleteMapping(path = "/{personId}")
    public ResponseEntity deletePerson(@PathVariable long personId) {
        personService.delete(personId);
        log.info(PERSON_DELETED_LOG, personId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
