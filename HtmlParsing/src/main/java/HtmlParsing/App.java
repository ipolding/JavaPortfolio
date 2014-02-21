package HtmlParsing;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Random;

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

        File input = new File("C:/github/javaPortfolio/HtmlParsing/src/main/resources/tmp/html.html");
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

// TODO fix this so that it works for odd number column headers
       String[] columnHeaders = {"Title","Author"};
       generateCsv(novels,columnHeaders);

    }

    private static void generateCsv(List strings, String[] columnHeaders){
        FileWriter fileWriter = null;
        Random testGenerator = new Random();
        try {
            fileWriter = new FileWriter("C:/github/javaPortfolio/HtmlParsing/src/main/resources/tmp/Example_output" + testGenerator.nextInt(999) + ".csv" );

            for (String header: columnHeaders){
                fileWriter.append(header);
                fileWriter.append(", ");
            }
            fileWriter.append("\n");

            int listPosition = 0;
            for (int i = 0; i < strings.size(); i += columnHeaders.length) {
                for (int j = 0; j < columnHeaders.length; j++)
                {fileWriter.append((String) strings.get(listPosition));
                 fileWriter.append(", ");
                    listPosition++;
                }
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex)
        {ex.printStackTrace();}

    }


}
