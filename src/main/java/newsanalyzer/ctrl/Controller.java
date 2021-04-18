package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

import java.util.List;

public class Controller {

	public static final String APIKEY = "90dc8a555be74ebb98621345c5d99166";

	public void process(Endpoint endpoint, String qValue, Country country, Category category) {
		System.out.println("Start process");
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setEndPoint(endpoint)
				.setQ(qValue)
				.setSourceCountry(country)
				.setSourceCategory(category)
				.createNewsApi();
		NewsReponse newsResponse = newsApi.getNews();
		if (newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis

		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
