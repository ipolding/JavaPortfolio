import scrape.*;


public class DevelopmentClass {
	
	public static void main (String[] args){
		WikiMediaScraper scraper = new WikiMediaScraper();
		String url = scraper.createURL("radiohead");
		System.out.println(url);
		System.out.println(scraper.getResult(url).toString());		
		
	}

}
