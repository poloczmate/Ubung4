package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.beans.Source;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import newsreader.downloader.Downloader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

	private List<Article> last;

	public static final String APIKEY = "90dc8a555be74ebb98621345c5d99166";

	public void process(Endpoint endpoint, String qValue, Country country, Category category) {
		System.out.println("Start process");

		try {
			NewsApi newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setEndPoint(endpoint)
					.setQ(qValue)
					.setSourceCountry(country)
					.setSourceCategory(category)
					.createNewsApi();
			NewsReponse newsResponse = newsApi.getNews();
			if (newsResponse == null) throw new NewsApiException("No response");
			if (newsResponse.getStatus().equals("error")){
				throw new NewsApiException("Status is error!");
			}
			//TODO implement Error handling?
			List<Article> articles = newsResponse.getArticles();
			last = articles;

			if(articles.size() == 0) throw new NewsApiException("No articles found");
			articles.stream().forEach(article -> System.out.println(article.toString()));
			System.out.println("Anzahl der Artikel: " + articles.stream().count());



			Map<String, Long> map = articles.stream().collect(Collectors.groupingBy(Article::getSourceName, Collectors.counting()));
			Optional<Map.Entry<String, Long>> a = map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
			System.out.println("Provider mit meisten Artikel: " + a.get().getKey());

			Collections.sort(articles, Comparator.comparing(Article::getAuthor));
			articles.stream()
					.takeWhile(article -> article.getAuthor() == "")
					.forEach(article -> System.out.println(article.getAuthor()));

			Collections.sort(articles, Comparator.comparing(Article::getTitle));
		}catch (NewsApiException e){
			System.out.println(e.getMessage());
		}

		System.out.println("End process");
	}

	public void downloadArticles(Downloader d){
		List<String> URLs = last.stream().map(Article::getUrl).filter(o -> o != null).collect(Collectors.toList());
		long startTime = System.nanoTime();
		d.process(URLs);
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Time elapsed: " + timeElapsed/1000000000 + " seconds");
	}

	public Object getData() {
		
		return null;
	}
}
