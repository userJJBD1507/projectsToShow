package usa.bogdan.WebCrawler.HTMLParser;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usa.bogdan.WebCrawler.Deduplicator.Deduplicator;
import usa.bogdan.WebCrawler.Models.ContentEntity;
import usa.bogdan.WebCrawler.Models.URLqueueEntity;
import usa.bogdan.WebCrawler.Repositories.MongoDBRepository;
import usa.bogdan.WebCrawler.Repositories.Repository;
import usa.bogdan.WebCrawler.URLextractor.URLextractor;

import java.io.IOException;
import java.util.List;

@Service
public class HTMLParser {
    @Autowired
    private Repository repository;

    @Autowired
    private URLextractor urlExtractor;

    @Autowired
    private Deduplicator deduplicator;
    @Autowired
    private MongoDBRepository mongoDBRepository;
    private int id = 0;

    @PostConstruct
    public void init() throws Exception {
        String URL = "https://google.com/";
        Document document = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
                .get();
        Elements title = document.select("title");
        repository.save(new URLqueueEntity(URL));
        ContentEntity contentEntity = new ContentEntity(title.toString()
                .replace("<title>", "")
                .replace("</title>", ""));
        mongoDBRepository.save(contentEntity);
        repository.updateId(contentEntity.getId(), URL);
    }
    public void parseHtml() {
        while (true) {
            List<URLqueueEntity> listOK = repository.getAfterID(id);
            if (listOK.isEmpty()) {
                break;
            }
            for (URLqueueEntity go : listOK) {
                String url = go.getUrl();
                try {
                    Document document = Jsoup.connect(url).get();
                    String baseUrl = document.baseUri();
                    urlExtractor.extractor(url, baseUrl, document);
                } catch (IOException e) {
                    System.out.println("Error connecting to URL: " + url);
                } catch (Exception e) {
                    System.out.println("Error processing URL: " + url);
                    e.printStackTrace();
                }
            }
            id += listOK.size();
            System.out.println("NEXT");
        }
    }
}