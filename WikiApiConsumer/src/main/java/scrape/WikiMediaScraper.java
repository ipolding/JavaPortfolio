package scrape;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.XPathOperations;

import JsonWrappers.WikipediaArticle;

@Service
public class WikiMediaScraper  {
	
	private XPathOperations xpathTemplate = new Jaxp13XPathTemplate();
	private static final String API_ENTRY_POINT = "http://en.wikipedia.org/w/api.php?";
	private static final String REV_XPATH = "//rev";
		
//	public static final Pattern INFOBOX_REGEX = Pattern.compile("(?<=infobox )(.*?)");
	public static final Pattern INFOBOX_REGEX = Pattern.compile("i");
	
	public String getInfobox(Source source) {		
		return getInfoboxString(getRevNodeText(source));
	}
	
	
	public String getInfoboxString(String input) {
		Matcher matcher = INFOBOX_REGEX.matcher(input);
		return matcher.group();	
		
	}
	
	
	static HashMap<String, String> params = new HashMap<String, String>();
	
	final Logger LOGGER = LoggerFactory.getLogger(WikiMediaScraper.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String getRevNodeText(Source source) {
		
		return xpathTemplate.evaluateAsString(REV_XPATH, source);
		
	}

	public WikipediaArticle getJsonResult(String url) {
		url = url + "&format=json";
		LOGGER.info(url);
		return restTemplate.getForObject(url, WikipediaArticle.class);
		}
	
	public Source getXmlInfoboxResult(String url) {
		url = url + "&format=xml";
		LOGGER.info(url);
		return restTemplate.getForObject(url, Source.class);

		}
	
	public String createURL(String band, String format) {
		String url = API_ENTRY_POINT;

		
		params.put("action", "query");
		params.put("prop", "revisions");
		params.put("rvprop", "timestamp|user|comment|content");
		params.put("titles", band);

		for (Map.Entry<String, String> entry : params.entrySet()) {
			String urlParameter = '&' + entry.getKey() + "=" + entry.getValue();
			url = url + urlParameter;
		}
		LOGGER.info("Created URL: " + url);
		return url;

	}

}
