package com.mic3.personservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
public class PersonDTO {
    /**
     * Id field
     */
    private Long id;

    /**
     * Person name
     */
    private String name; //required

    /**
     * Person surname
     */
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
    private String email;

    /**
     * Contacts for person
     */
    //@OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
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
