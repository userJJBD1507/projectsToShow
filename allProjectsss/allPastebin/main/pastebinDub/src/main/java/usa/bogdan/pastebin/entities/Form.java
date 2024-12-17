package usa.bogdan.pastebin.entities;

public class Form {
    private String content;
    private String deletion_time;

    public Form() {

    }

    public Form(String content, String deletion_time) {
        this.content = content;
        this.deletion_time = deletion_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeletion_time() {
        return deletion_time;
    }

    public void setDeletion_time(String deletion_time) {
        this.deletion_time = deletion_time;
    }

    @Override
    public String toString() {
        return "Form{" +
                "content='" + content + '\'' +
                ", deletion_time='" + deletion_time + '\'' +
                '}';
    }
}