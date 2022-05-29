package Frontend;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

class FileParser{

    public static List<String> readLines(String path) {
        List<String> lines = new LinkedList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String nextLine = reader.readLine();
            while (nextLine != null) {
                lines.add(nextLine);
                nextLine = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Could not read file " + path);
        }
        return lines;
    }
}
