package com.jair.curso.springboot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @Column(name = "programming_lange")
    private String programminLanguage;

    @Embedded
    private Audit audit = new Audit();

    public Person() {
    }

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Person(Long id, String name, String lastname, String programminLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programminLanguage = programminLanguage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProgramminLanguage() {
        return programminLanguage;
    }

    public void setProgramminLanguage(String programminLanguage) {
        this.programminLanguage = programminLanguage;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programminLanguage="
                + programminLanguage + ", createAt=" + audit.getCreateAt() + ", updateAt=" + audit.getUpdateAt() + "]";
    }
}
