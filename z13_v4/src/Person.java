import java.util.Date;

public class Person
{
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PATRONYMIC = "patronymic";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;

    public Person(int id, String name, String surname, String patronymic, Date dateOfBirth)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
