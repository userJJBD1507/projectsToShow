package usa.bogdan.WebCrawler.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "url_table", schema = "web_crawler")
public class URLqueueEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "url")
    private String url;
    @Column(name = "content_id")
    private String content_id;

    public URLqueueEntity() {

    }

    public URLqueueEntity(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    @Override
    public String toString() {
        return "URLqueueEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", content_id='" + content_id + '\'' +
                '}';
    }
}
