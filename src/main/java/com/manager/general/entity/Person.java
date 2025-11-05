package com.manager.general.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="persons")
public class Person {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
}
