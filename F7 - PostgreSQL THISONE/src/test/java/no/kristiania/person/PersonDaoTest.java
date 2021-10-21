package no.kristiania.person;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        PersonDao dao = new PersonDao(createDataSource());

        Person person = randomPerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    private DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("Y?qy3Y4T!8GshJ3FS9");
        return dataSource;
    }

    private Person randomPerson() {
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Jane", "Jonas", "Josephine", "Jamal"));
        person.setLastName(pickOne("Persson", "Olsson", "Jensen", "Knutsen"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }


}