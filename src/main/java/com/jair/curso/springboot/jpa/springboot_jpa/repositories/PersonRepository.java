package com.jair.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jair.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.jair.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE LENGTH(p.name)=(SELECT MIN(LENGTH(p.name)) FROM Person p)")
    List<Object[]> getShorterName();

    List<Person> findAllByOrderByNameDesc();

    List<Person> findByIdBetween(Long id1, Long id2);

    List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);

    List<Person> findByIdBetweenOrderByNameAscLastnameAsc(Long id1, Long id2);

    @Query("SELECT p FROM Person p WHERE p.name BETWEEN ?1 AND ?2 ORDER BY p.name ASC, p.lastname ASC")
    List<Person> findAllBetweenName(String n1, String n2);

    @Query("SELECT p FROM Person p WHERE p.id BETWEEN 2 AND 7")
    List<Person> findAllBetweenId();

    @Query("select UPPER(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullnameConcatUpper();

    @Query("select COUNT(DISTINCT p.name) from Person p")
    List<String> findAllNamesDistinctCount();

    @Query("select DISTINCT p.name from Person p")
    List<String> findAllNamesDistinct();

    @Query("select new com.jair.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllPersonalizedObjectPerson();

    @Query("select p.name from Person p where p.id=?1")
    Optional<String> getNameById(Long id);

    @Query("select p.id, p.name, p.lastname, p.programminLanguage from Person p")
    List<Object[]> obtenerPersonaDataFull();

    @Query("select p, p.programminLanguage from Person p")
    List<Object[]> findAllMixPerson();

    // One
    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    List<Person> findByNameStartingWith(String name);

    // Listas
    List<Person> findByProgramminLanguage(String programminLanguage);

    @Query("select p from Person p where p.programminLanguage=?1 and p.name=?2")
    List<Person> buscarByProgramminLanguage(String programminLanguage, String name);

    List<Person> findByProgramminLanguageAndName(String programminLanguage, String name);

    @Query("select p.name, p.programminLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programminLanguage from Person p where p.programminLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programminLanguage, String name);
}
