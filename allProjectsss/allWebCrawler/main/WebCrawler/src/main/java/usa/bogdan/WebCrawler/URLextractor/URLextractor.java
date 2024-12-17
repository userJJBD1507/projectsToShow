package usa.bogdan.WebCrawler.URLextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usa.bogdan.WebCrawler.Models.ContentEntity;
import usa.bogdan.WebCrawler.Models.URLqueueEntity;
import usa.bogdan.WebCrawler.Repositories.MongoDBRepository;
import usa.bogdan.WebCrawler.Repositories.Repository;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Service
public class URLextractor {
    private int id = 1;

    @Autowired
    private Repository repository;

    @Autowired
    private MongoDBRepository mongoDBRepository;

    private final Set<String> visitedUrls = new HashSet<>();

    public void extractor(String url, String baseUrl, Document document) throws Exception {
        Elements elements = document.select("a[href]");
        for (Element element : elements) {
            String attributeHref = element.attr("href");
            attributeHref = cleanUrl(attributeHref);
            String fullUrl = resolveUrl(baseUrl, attributeHref);

            if (fullUrl != null && !visitedUrls.contains(fullUrl)) {
                visitedUrls.add(fullUrl);
                System.out.println(fullUrl);
                repository.save(new URLqueueEntity(fullUrl));
                extractorContent(fullUrl, Jsoup.connect(fullUrl).get());
            }
        }
    }

//    public void extractorContent(String url, Document document) throws Exception {
//        Elements titles = document.select("title, h1, h2, h3, h4, h5, h6");
//        if (!titles.isEmpty()) {
//            StringBuilder contentBuilder = new StringBuilder();
//            for (Element title : titles) {
//                contentBuilder.append(title.text()).append("\n");
//            }
//            String contentText = contentBuilder.toString();
//            System.out.println(contentText);
//            ContentEntity contentEntity = new ContentEntity();
//            contentEntity.setContent(contentText);
//            mongoDBRepository.save(contentEntity);
//            String s = contentEntity.getId();
//            repository.updateId(s, url);
//        }
//    }
        public void extractorContent(String url, Document document) throws Exception {
        Elements titles = document.select("title");
        if (!titles.isEmpty()) {
            String titleText = titles.first().text();
//            Thread.sleep(1000);
            System.out.println(titleText);
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setContent(titleText);
            mongoDBRepository.save(contentEntity);
            String s = contentEntity.getId();
            repository.updateId(s, url);
        }
    }
    public String resolveUrl(String baseUrl, String relativeUrl) {
        try {
            URL base = new URL(baseUrl);
            URL resolved = new URL(base, relativeUrl);
            return resolved.toString();
        } catch (Exception e) {
            System.out.println("Invalid URL: " + relativeUrl);
            return null;
        }
    }

    public String cleanUrl(String url) {
        int mailtoToIndex = url.indexOf("mailto:");
        if (mailtoToIndex != -1) {
            url = url.substring(0, mailtoToIndex);
        }
        return url;
    }
}