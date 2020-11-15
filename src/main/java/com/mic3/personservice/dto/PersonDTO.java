package com.mic3.personservice.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PersonDTO {
    /**
     * Id field
     */
    private Long id;

    /**
     * Person name
     */
    @NotNull
    private String name; //required

    /**
     * Person surname
     */
    @NotNull
    private String surname;// (required)

    /**
     * Person age
     */
    private Integer age;

    /**
     * Person sex
     */
    private String sex;

    /**
     * Person birthday
     */
    private LocalDate birthday;

    /**
     * Person phone number
     */
    private String phone;

    /**
     * Person email address
     */
    @Email
    private String email;

    /**
     * Contacts for person
     */
    private Set<PersonDTO> contacts;

    /**
     * Date when person was created
     */
    private Timestamp created;

    /**
     * Last modification date
     */
    private Timestamp modified;

}
