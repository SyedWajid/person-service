package com.mic3.personservice.rest;

import com.mic3.personservice.domain.Person;
import com.mic3.personservice.service.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Syed Wajid
 * Rest api controller for person bean
 */

@RestController
@Slf4j
@RequestMapping(path = {"/api/v1/persons"}, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
//@OpenAPIDefinition(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class PersonController {

    /**
     * Person service reference
     */
    private IPersonService personService;

    /**
     * Using constructor injection
     * @param personService
     */
    public PersonController(IPersonService personService){
        this.personService = personService;
    }

    /**
     * Returns a list of persons and sorted based on the query parameters
     * @param page
     * @param size
     * @param sortField
     * @param direction
     * @return
     */
    @Operation(summary = "Returns a list of persons and sorted based on the query parameters")
    @ApiResponse(responseCode = "200", description = "List of persons",
                 content = {@Content(schema = @Schema(implementation = Page.class))})
    @GetMapping
    public ResponseEntity<Page<Person>> getPersons(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size,
                                                   @RequestParam(required = false, name = "sortField",
                                                           defaultValue = "created") String sortField,
                                                   @RequestParam(required = false, name = "direction",
                                                           defaultValue = "DESC") String direction){
        log.info("persons are " + personService.getPersons(PageRequest.of(page, size)));
        Page<Person> result = personService.getPersons(PageRequest.of(page, size,
                                                       Sort.Direction.fromString(direction), sortField));
        return ResponseEntity.ok(result);
    }

    /**
     * Rest api for creating person
     * @param Person
     * @return
     */

    @Operation(summary = "Crate a new person")
    @ApiResponse(responseCode = "201", description = "Person is created",
                 content = {@Content(schema = @Schema(implementation = Person.class))})
    public ResponseEntity<Person> createPerson(
            @Valid @RequestBody Person Person) {
        final Person createdPerson = null;//personService.createPerson(Person);
        //log.info(NEW_ORDER_LOG, createdPerson.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    /**
     * Fetch person by personId
     * @param personId
     * @return
     */
    @Operation(summary = "Get an person by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the person",
                                content = {@Content(schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)})
    @GetMapping(path = "/{personId}")
    public ResponseEntity<Person> loadPerson(@PathVariable(required = true) String personId) {
        final Optional<Person> person = null;//personService.loadPerson(id);
        if (person.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person.get());
    }

    /**
     * Update person object
     * @param personId
     * @param Person
     * @return
     */
    @Operation(summary = "Update an person by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person was updated",
                            content = {@Content(schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)})
    @PutMapping(path = "/{personId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updateCustomQuoteRequest(
            @PathVariable(required = true) String personId,
            @Valid @RequestBody Person Person) {
        final Optional<Person> updatedPerson = null;
                //personService.updatePerson(personId, Person);
        if (updatedPerson.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        //logger.info(ORDER_UPDATED_LOG, updatedPerson.toString());
        return ResponseEntity.ok(updatedPerson.get());
    }
}
