package sample;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class KeywordFixer {
    private Map<String, String> map;

    public KeywordFixer(String file) {
        map = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            StringBuilder builder = new StringBuilder();
            while (line != null) {
                builder.append("\\s{1,1}");
                for (int i = 0; i < line.length(); i++) {
                    builder.append("[");
                    builder.append(line.charAt(i));
                    builder.append((char) (line.charAt(i)-32));
                    builder.append("]");
                }
                builder.append("\\s{1,1}");
                map.put(" " + line+" ", builder.toString());
                System.out.println(builder.toString());
                builder = new StringBuilder();
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String fix(String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        StringBuilder result = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            line = line.replaceAll("[(]", " ( ");
            for (Map.Entry<String, String> keyword : map.entrySet()) {
                line = line.replaceAll(keyword.getValue(), keyword.getKey());
            }
            line = line.replaceAll(" [(] ", "(");
            result.append(line + "\n");
            line = reader.readLine();
        }
        return result.toString();
    }
}
