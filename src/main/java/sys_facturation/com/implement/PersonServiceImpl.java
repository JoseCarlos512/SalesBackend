package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.Person;
import sys_facturation.com.repository.PersonDao;
import sys_facturation.com.service.PersonService;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    @Transactional(readOnly = true)
    public List<Person> ListPerson() {
        return personDao.findAll();
    }

    @Override
    @Transactional
    public Person RegisterPerson(Person person) {
        return personDao.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person PersonById(Long id) {
        return personDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void RemovePerson(Long id) {
        personDao.deleteById(id);
    }

    @Override
    @Transactional
    public Person EditPerson(Person person) {
        return personDao.save(person);
    }
}
