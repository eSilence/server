package chat.mapWords;

import accounts.AccountService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirtyWords {
    private final Map<String, String> mapWords;
   // private AccountService accountService;

    public DirtyWords(/*AccountService accountService*/) {
        this.mapWords = new HashMap<>();
       // this.accountService =  accountService;
        fromfile("dirtyWords.txt");

    }
    private void fromfile(String fileName) {
        List<String> lines = new ArrayList<>();
        System.out.println("files: "+ fileName);
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (String line : lines) {
                //String first =   line.substring(0, line.indexOf('-')).trim();
               // String second = line.substring(line.indexOf('-') + 1, line.length()).trim();
                String []words = line.split("-");
                mapWords.put(words[0].trim(), words[1].trim());
               // System.out.print(line);
            }
            for (Map.Entry entry: mapWords.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                System.out.println( key + "---" + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean findFirstWord(String first){
        return  mapWords.containsKey(first);
    }

    public String replaceWord(String first){
        return   mapWords.get(first);
    }
}
