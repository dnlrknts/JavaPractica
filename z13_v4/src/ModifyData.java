import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class ModifyData
{
    private final Connection connection;
    private PreparedStatement statement = null;

    public ModifyData(Connection connection)
    {
        this.connection = connection;
    }

    public void SendToAll(int senderID, String topic, String content) throws Exception
    {
        if (statement == null)
        {
            final String query = "INSERT INTO letters (SenderId, RecipientId,Topic,Content,Departure_date) value (?,?,?,?,?)";
            statement = connection.prepareStatement(query);
        }
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT id FROM persons");
        Set<Integer> peopleID = new HashSet<>();
        while (resultSet.next())
            peopleID.add(resultSet.getInt(1));
        peopleID.remove(senderID);

        statement.setInt(1, senderID); //sender
        statement.setString(3, topic); //topic
        statement.setString(4, content);//content
        statement.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));

        for (Integer i : peopleID)
        {
            statement.setInt(2, i);
            statement.execute();
        }
    }
}
