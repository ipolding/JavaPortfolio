package HtmlParsing;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Document doc = null;

        File input = new File("C:/tutorial/javaTutorials/HtmlParsing/src/main/resources/tmp/html.html");
        doc = Jsoup.parse(input, "UTF-8");

        Elements html_novels = doc.getElementsByTag("a");
        List<String> novels = new ArrayList<String>();

        for (Element element: html_novels ){
//            Only print elements that are a name or an author
            if (!element.className().equals("external autonumber")) {
                    System.out.println(element.text());
                    novels.add(element.text());
            }

        }
    }

    private void generateCsv(List book_title){



    }


}
