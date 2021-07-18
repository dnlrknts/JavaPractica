import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadData
{
    private final Connection connection;
    private PreparedStatement selectRecByTopic = null;
    private PreparedStatement selectNRecByTopic = null;

    public ReadData(Connection connection)
    {
        this.connection = connection;
    }

    private Person takePerson(ResultSet set) throws Exception
    {
        return new Person(set.getInt(Person.ID),
                set.getString(Person.NAME),
                set.getString(Person.SURNAME),
                set.getString(Person.PATRONYMIC),
                set.getDate(Person.DATE_OF_BIRTH));
    }

    public Person findUserWithMinLen() throws Exception
    {
        final String query = "SELECT * From persons Where id = (SELECT senderid FROM letters GROUP BY senderid ORDER BY sum(LENGTH(content)) limit 1)";
        try(Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(query))
        {
            result.next();
            return takePerson(result);
        }
    }

    public List<Person> selectRecByTopic(String topic) throws Exception
    {
        if(selectRecByTopic ==null)
        {
            final String query = "SELECT persons.Id,Name,Surname,Patronymic,Date_of_birth FROM persons INNER JOIN letters l on persons.Id = l.RecipientId WHERE topic=?";
            selectRecByTopic = connection.prepareStatement(query);
        }
        selectRecByTopic.setString(1,topic);
        List<Person> people = new ArrayList<>();
        try(ResultSet result = selectRecByTopic.executeQuery())
        {
            while (result.next())
                people.add(takePerson(result));
        }
        return people;
    }
    public List<Person> selectNRecByTopic(String topic) throws Exception
    {
        if(selectNRecByTopic ==null)
        {
            final String query = "SELECT persons.Id,Name,Surname,Patronymic,Date_of_birth FROM persons INNER JOIN letters l on persons.Id = l.RecipientId WHERE topic!=?";
            selectNRecByTopic = connection.prepareStatement(query);
        }
        selectNRecByTopic.setString(1,topic);
        List<Person> people = new ArrayList<>();
        try(ResultSet result = selectNRecByTopic.executeQuery())
        {
            while (result.next())
                people.add(takePerson(result));
        }
        return people;
    }
}
