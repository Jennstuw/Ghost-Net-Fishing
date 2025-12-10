package com.ghostnet.service;

import com.ghostnet.entity.Person;
import com.ghostnet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(String name, String phoneNumber, boolean isAnonymous) {
        Person person = new Person(name, phoneNumber, isAnonymous);
        return personRepository.save(person);
    }

    public Person createAnonymousPerson() {
        Person person = new Person("Anonymous", null, true);
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> getNonAnonymousPersons() {
        return personRepository.findByIsAnonymous(false);
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person updatePerson(Long id, String name, String phoneNumber) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(name);
            person.setPhoneNumber(phoneNumber);
            return personRepository.save(person);
        }
        return null;
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}