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

public class HtmlListConverter
{

    public static Elements cleanHtmlList(String inputFilePath, String desiredTag, String unwantedClass) throws IOException {
        Document doc = null;
        File input = new File(inputFilePath);
        doc = Jsoup.parse(input, "UTF-8");
        Elements sourceList = doc.getElementsByTag(desiredTag);

        Elements cleanElementList = new Elements();

        for (Element element : sourceList)
            if (!element.className().equals(unwantedClass)) {
                cleanElementList.add(element);
            }
        return cleanElementList;
    }


    public static List<String[]> generateTupleList(int numberOfValues, Elements cleanHtmlElementList){

        int elementsInList = cleanHtmlElementList.size();

        List<String[]> tupleList = new ArrayList<String[]>();
        int listPosition = 0;
        while (listPosition < elementsInList) {

        String[] localTuple;
        localTuple = new String[numberOfValues];
        for (int i = 0; i< numberOfValues; i++ ){

                localTuple[i] = cleanHtmlElementList.get(listPosition).text();
                listPosition++;

            }
        tupleList.add(localTuple);
        }

        return tupleList;
    }

    public static void generateCsv(List<String[]> tupleList, String[] columnHeaders, String filePath){
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filePath);

            for (String header: columnHeaders){
                fileWriter.append(header);
                fileWriter.append(", ");
            }
            fileWriter.append("\n");

            int listPosition = 0;

            while (listPosition < tupleList.size()) {
                String[] tuple = tupleList.get(listPosition);

            for (int i = 0; i < tuple.length; i++) {
                {fileWriter.append(tuple[i]);
                    fileWriter.append(", ");
                }

            }
                fileWriter.append("\n");
                listPosition++;
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex)
        {ex.printStackTrace();}

    }




    private static void generateCsvFromStringList(List strings, String[] columnHeaders){
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
