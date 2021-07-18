import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database implements AutoCloseable
{
    private final Connection connection;
    private static Database db;
    public static Database instance() throws Exception
    {
        if(db==null)
            db = new Database();
        return db;
    }
    private Database() throws Exception
    {
        String url = "jdbc:mysql://localhost/letters";
        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "фвьшт");
                prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        prop.put("useUnicode", "true");
        prop.put("useSSL", "true");
        prop.put("useJDBCCompliantTimezoneShift", "true");
        prop.put("useLegacyDatetimeCode", "false");
        prop.put("serverTimezone", "UTC");
        prop.put("serverSslCert", "classpath:server.crt");
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection(url,prop);
    }

    public Connection getConnection()
    {
        return connection;
    }

    @Override
    public void close() throws Exception
    {
        connection.close();
    }
}
