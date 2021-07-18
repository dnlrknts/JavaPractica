import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException
    {
        FileWriter writer = new FileWriter("out.txt");
        LinkedList<String> list = Files.lines(Paths.get("test.txt"))
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(list);
        for (String s : list)
        {
            writer.write(s);
            writer.write('\n');
        }

        writer.close();
    }
}
