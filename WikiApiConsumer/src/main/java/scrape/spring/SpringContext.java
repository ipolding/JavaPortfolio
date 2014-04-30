package scrape.spring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import scrape.WikiMediaScraper;

public class SpringContext {
	
public static void main(String[] args) {
		
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	context.scan("scrape");
	
	WikiMediaScraper wikiMediaScraper = context.getBean(WikiMediaScraper.class);
	String url = wikiMediaScraper.createURL("Radiohead", "xml");	
	Source source = wikiMediaScraper.getXmlInfoboxResult(url);
	String articleString = wikiMediaScraper.getRevNodeText(source);
	
	// (?<=Infobox )
	
	Pattern INFOBOX_REGEX = Pattern.compile("(.*?)");
	
//	(?= \\}\\})
	
//	Pattern INFOBOX_REGEX = Pattern.compile("Infobox");
	
	Matcher matcher = INFOBOX_REGEX.matcher(articleString);
//	System.out.print(matcher.pattern());
	System.out.print(matcher.group()); 
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	}
	

}
