package usa.bogdan.WebCrawler.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import usa.bogdan.WebCrawler.HTMLParser.HTMLParser;
import usa.bogdan.WebCrawler.Models.ContentEntity;
import usa.bogdan.WebCrawler.Models.URLqueueEntity;
import usa.bogdan.WebCrawler.Repositories.MongoDBRepository;
import usa.bogdan.WebCrawler.Repositories.Repository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    MongoDBRepository mongoDBRepository;
    @Autowired
    Repository repository;
    @Autowired
    HTMLParser htmlParser;
    @GetMapping
    public String getForm() {
        return "form";
    }
    @GetMapping("/search")
    public String search(@RequestParam(name = "query") String query, Model model) {
        model.addAttribute("query", query);
        List<ContentEntity> contentEntities = mongoDBRepository.findByContentContainingIgnoreCase(query);
        List<String> ids = new ArrayList<>();
        for (ContentEntity contentEntity : contentEntities) {
            ids.add(contentEntity.getId());
        }
        List<URLqueueEntity> urlQueueEntities = repository.getById(ids);
        model.addAttribute("results", urlQueueEntities);

        return "result";
    }
}