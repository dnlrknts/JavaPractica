public class Main
{
    public static void main(String[] args) throws Exception
    {
        try(Database database = Database.instance())
        {
            ReadData rd = new ReadData(database.getConnection());
            ModifyData md = new ModifyData(database.getConnection());
            System.out.println(rd.findUserWithMinLen());
            System.out.println(rd.selectRecByTopic("чистят").subList(0,10));
            System.out.println(rd.selectNRecByTopic("чистят").subList(0,10));
            md.SendToAll(1,"1","2");
        }
    }
}
