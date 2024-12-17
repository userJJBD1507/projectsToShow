package usa.bogdan.ENS.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ens_shape_table", schema = "ens")
public class ENSentity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "contact_type")
    private String contactType;
    @Column(name = "shape")
    private String shape;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private String status;
    public ENSentity() {

    }

    public ENSentity(String name, String contactType, String shape, String email, String status) {
        this.name = name;
        this.contactType = contactType;
        this.shape = shape;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @Override
    public String toString() {
        return "ENSentity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactType='" + contactType + '\'' +
                ", shape='" + shape + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}