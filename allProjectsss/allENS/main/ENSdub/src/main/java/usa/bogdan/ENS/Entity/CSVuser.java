package usa.bogdan.ENS.Entity;

public class CSVuser {
    private String name;
    private String contactType;
    private String contactValue;

    public CSVuser() {

    }

    public CSVuser(String name, String contactType, String contactValue) {
        this.name = name;
        this.contactType = contactType;
        this.contactValue = contactValue;
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

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    @Override
    public String toString() {
        return "CSVuser{" +
                "name='" + name + '\'' +
                ", contactType='" + contactType + '\'' +
                ", contactValue='" + contactValue + '\'' +
                '}';
    }
}