package scrape.spring;

import javax.xml.transform.Source;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import scrape.WikiMediaScraper;

public class SpringContext {
	
public static void main(String[] args) {
		
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	context.scan("scrape");
	
	WikiMediaScraper wikiMediaScraper = context.getBean(WikiMediaScraper.class);
	String url = wikiMediaScraper.createURL("Radiohead", "xml");	
	System.out.println(
			wikiMediaScraper.getInfobox(wikiMediaScraper.getXmlInfoboxResult(url))
			
			);
	
	
	
	
	
	
	
		
	}
	

}
