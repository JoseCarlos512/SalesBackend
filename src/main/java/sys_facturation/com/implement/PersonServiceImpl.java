package sys_facturation.com.implement;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sys_facturation.com.entity.Person;
import sys_facturation.com.repository.PersonDao;

@Repository
public class PersonServiceImpl implements PersonDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Person> ListPerson() {
        return em.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    @Transactional
    public Person RegisterPerson(Person person) {
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
        return person;
    }

    @Override
    @Transactional
    public Person PersonById(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    @Transactional
    public void RemovePerson(Long id) {
        Person person = PersonById(id);
        if (person != null) {
            em.remove(person);
        }
    }

    @Override
    @Transactional
    public Person EditPerson(Person person) {
        return em.merge(person);
    }

    
    
}
