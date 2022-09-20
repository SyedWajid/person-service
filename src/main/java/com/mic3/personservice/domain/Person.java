package com.mic3.personservice.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/**
 * Person entity
 */
public class Person {
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
     * Excluding contacts set from equals, hashcode and toString
     */
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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
