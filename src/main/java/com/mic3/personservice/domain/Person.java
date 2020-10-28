package com.mic3.personservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name; //required

    private String surname;// (required)

    Integer age;

    private String sex;

    Date birthday;

    private String phone;

    private String email;

    @OneToMany
    private Set<Person> contacts;

    private Timestamp created;

    private Timestamp modified;
}
