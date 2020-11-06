package com.mic3.personservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person implements Serializable {
    /**
     * Id field
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * Person name
     */
    @NotEmpty
    private String name; //required

    /**
     * Person surname
     */
    @NotEmpty
    private String surname;// (required)

    /**
     * Person age
     */
    Integer age;

    /**
     * Person sex
     */
    private String sex;

    /**
     * Person birthday
     */
    Date birthday;

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
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Person> contacts;

    /**
     * Date when person was created
     */
    private Timestamp created;

    /**
     * Last modification date
     */
    private Timestamp modified;
}
