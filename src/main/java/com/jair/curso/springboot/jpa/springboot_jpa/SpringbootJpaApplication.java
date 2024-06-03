package com.jair.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.jair.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.jair.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		update();
	}

	public void subQueries() {
		System.out.println("========= Sub Queries =========");
		repository.getShorterName().forEach(data -> {
			System.out.println("name=" + data[0] + " - length=" + data[1]);
		});
		;
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesbetween() {
		repository.findByIdBetween(2L, 7L).forEach(System.out::println);
		// repository.findAllBetweenId().forEach(System.out::println);
		System.out.println("====== Ordenando por nombre ======");
		repository.findAllBetweenName("C", "N").forEach(System.out::println);

		System.out.println("====== Between por id y Ordenando por nombre ======");
		repository.findByIdBetweenOrderByNameAsc(2L, 7L).forEach(System.out::println);

		System.out.println("====== Ordenando por nombre ======");
		repository.findAllByOrderByNameDesc().forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedConcatUpperLower() {
		repository.findAllFullnameConcatUpper().forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinc() {
		System.out.println("============= consulta nombres unicos =============");
		repository.findAllNamesDistinct().forEach(System.out::println);

		System.out.println("============= consulta cantidad por nombres unicos =============");
		repository.findAllNamesDistinctCount().forEach(System.out::println);
	}

	@Transactional
	public void personalizedQuery2() {
		System.out.println("============= consulta por objeto persona y lenguaje de programacion =============");
		repository.findAllMixPerson().forEach(data -> {
			System.out.println("programmingLanguage=" + data[1] + ", person=" + data[0]);
		});

		System.out.println(
				"============= consulta que puebla y devuelveobjeto entity de una instancia personalizada =============");
		repository.findAllPersonalizedObjectPerson().forEach(System.out::println);

		System.out.println(
				"============= consulta que puebla y devuelveobjeto un objeto dto de una clase personalizada =============");
		repository.findAllPersonDto().forEach(System.out::println);
	}

	@Transactional
	public void personalizedQuery() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("============= consulta solo el nombre por el id =============");
		System.out.println("Ingrese el id para el nombre:");
		Long id = scanner.nextLong();
		Optional<String> optionalName = repository.getNameById(id);
		optionalName.ifPresentOrElse(System.out::println,
				() -> System.out.println("No hay un registro con el id ingresado!!!"));
		scanner.close();

		repository.obtenerPersonaDataFull().forEach(data -> {
			System.out.println("id=" + data[0] + " name=" + data[1] + " lastname=" + data[2] + " lenguaje=" + data[3]);
		});
	}

	@Transactional
	public void delete2() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(repository::delete,
				() -> System.out.println("No existe la persona con ese id!!!"));

		scanner.close();
	}

	@Transactional
	public void delete() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar:");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		scanner.close();
	}

	@Transactional
	public void update() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(person -> {
			System.out.println(person);
			System.out.println("Ingrese el lenguaje de programación:");
			String programmingLanguage = scanner.next();
			person.setProgramminLanguage(programmingLanguage);

			Person personUpdated = repository.save(person);

			System.out.println(personUpdated);
			return;
		}, () -> System.out.println("El usuario no existe!!!"));

		scanner.close();
	}

	@Transactional
	public void create() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre:");
		String name = scanner.next();
		System.out.println("Ingrese el apellido:");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programación:");
		String programmingLanguage = scanner.next();

		Person person = new Person(null, name, lastname, programmingLanguage);
		Person newPerson = repository.save(person);
		System.out.println(newPerson);
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void findOne() {
		Person person = null;
		Optional<Person> optionalPerson = repository.findById(2L);

		if (optionalPerson.isPresent()) {
			person = optionalPerson.get();
		}

		System.out.println(person);
	}

	@Transactional(readOnly = true)
	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		// List<Person> persons = (List<Person>)
		// repository.buscarByProgramminLanguage("Java", "Jair");
		List<Person> persons = (List<Person>) repository.findByProgramminLanguageAndName("Java", "Jair");

		persons.forEach(System.out::println);

		List<Object[]> personValues = repository.obtenerPersonData();
		personValues.forEach(p -> System.out.println(p[0] + " es experto en " + p[1]));
	}
}
