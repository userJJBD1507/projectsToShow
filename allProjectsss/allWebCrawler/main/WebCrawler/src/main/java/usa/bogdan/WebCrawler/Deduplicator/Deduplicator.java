package usa.bogdan.WebCrawler.Deduplicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usa.bogdan.WebCrawler.Models.URLqueueEntity;
import usa.bogdan.WebCrawler.Repositories.Repository;

import java.util.HashSet;
import java.util.Set;

@Service
public class Deduplicator {
    @Autowired
    private Repository repository;
    private final Set<String> seenUrls = new HashSet<>();

    public boolean isNewUrl(String href) {
        return seenUrls.add(href);
    }

    public void saveUrl(String href) {
        try {
            if (isNewUrl(href)) {
                System.out.println("Saving new URL: " + href);
                repository.save(new URLqueueEntity(href));
            }
        } catch (Exception e) {
            System.out.println("Error saving URL: " + href);
        }
    }
}