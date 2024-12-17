package usa.bogdan.ENS.Entity;

public class KafkaMessage {
    private String fileContent;
    private String shape;

    public KafkaMessage() {

    }

    public KafkaMessage(String fileContent, String shape) {
        this.fileContent = fileContent;
        this.shape = shape;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "fileContent='" + fileContent + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }
}
