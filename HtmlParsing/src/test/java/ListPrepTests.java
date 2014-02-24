import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static HtmlParsing.HtmlListConverter.cleanHtmlList;
import static HtmlParsing.HtmlListConverter.generateCsv;
import static HtmlParsing.HtmlListConverter.generateTupleList;

/**
 * Created by ian.polding on 24/02/14.
 */
public class ListPrepTests {

    @Test
    public void thisAlwaysPasses(){

    }

    @Test public void testTupleListGenerator() throws IOException {
        Document doc = null;

        File input = new File("C:/github/javaPortfolio/HtmlParsing/src/main/resources/tmp/html.html");
        doc = Jsoup.parse(input, "UTF-8");

        Elements sourceList = doc.getElementsByTag("a");

        Elements cleanElementList = new Elements();

        for (Element element : sourceList)
            if (!element.className().equals("external autonumber")) {
                cleanElementList.add(element);
            }
    List<String[]> tupleList = generateTupleList(2, cleanElementList);
        for (String[] tuple : tupleList) {
            System.out.println(Arrays.toString(tuple));

        }

    }
    @Test
    public void testCSVGenerator() throws IOException {
        Elements cleanElementList = cleanHtmlList("src/test/resources/input/html.html","a", "external autonumber");
        List<String[]> tupleList = generateTupleList(2, cleanElementList);

        Random testGenerator = new Random();

        String[] column_headers = new String[] {"Title", "Author"};
        String filePath = "src/test/resources/output/test_tuple_output" + testGenerator.nextInt(999) + ".csv";
        generateCsv( tupleList, column_headers, filePath );

        }

    }