package usa.bogdan.pastebin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "hash_table", schema = "pastebin")
public class HashEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "hash", unique = true)
    private String hash;
    @Column(name = "metadata_id", unique = true)
    private int metadata_id;

    public HashEntity() {

    }

    public HashEntity(String hash, int metadata_id) {
        this.hash = hash;
        this.metadata_id = metadata_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMetadata_id() {
        return metadata_id;
    }

    public void setMetadata_id(int metadata_id) {
        this.metadata_id = metadata_id;
    }

    @Override
    public String toString() {
        return "HashEntity{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", metadata_id=" + metadata_id +
                '}';
    }
}
