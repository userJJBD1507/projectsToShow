package usa.bogdan.pastebin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "metadata_link", schema = "pastebin")
public class MetadataEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "metadata_link")
    private String metadata_link;
    @Column(name = "deletion_time")
    private String deletion_time;

    public MetadataEntity() {

    }

    public MetadataEntity(String metadata_link, String deletion_time) {
        this.metadata_link = metadata_link;
        this.deletion_time = deletion_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetadata_link() {
        return metadata_link;
    }

    public void setMetadata_link(String metadata_link) {
        this.metadata_link = metadata_link;
    }

    public String getDeletion_time() {
        return deletion_time;
    }

    public void setDeletion_time(String deletion_time) {
        this.deletion_time = deletion_time;
    }

    @Override
    public String toString() {
        return "MetadataEntity{" +
                "id=" + id +
                ", metadata_link='" + metadata_link + '\'' +
                ", deletion_time='" + deletion_time + '\'' +
                '}';
    }
}
