package usa.bogdan.WebCrawler.Models;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ContentEntity {
    @Id
    private String id;
    private String content;

    public ContentEntity() {

    }

    public ContentEntity(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
