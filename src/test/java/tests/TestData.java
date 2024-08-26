package tests;

import lombok.Data;
import java.util.LinkedList;
import java.util.List;

@Data
public class TestData {

    String paswword = "Karpuk20!";
    String username = "DimKa";
    public String isbn() {
        List<String> isbns = new LinkedList<>();
        isbns.add("9781449325862");
        isbns.add("9781449331818");
        isbns.add("9781449337711");
        isbns.add("9781449365035");
        isbns.add("9781491904244");
        isbns.add("9781491950296");
        isbns.add("9781593275846");
        isbns.add("9781593277574");
        int random = (int) (Math.random() * isbns.size());
        String isbn = isbns.get(random);
        return isbn;
    }
}