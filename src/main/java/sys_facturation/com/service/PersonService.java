package sys_facturation.com.service;

import java.util.List;

import sys_facturation.com.entity.Person;

public interface PersonService {
    public List<Person> ListPerson();
	
	public Person RegisterPerson(Person person);

	public Person EditPerson(Person person);
	
	public Person PersonById(Long id);
	
	public void RemovePerson(Long id);
}

