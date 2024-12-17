package usa.bogdan.ENS.Entity;

import org.springframework.web.multipart.MultipartFile;

public class Form {
    private MultipartFile file;
    private String shape;

    public Form() {

    }

    public Form(MultipartFile file, String shape) {
        this.file = file;
        this.shape = shape;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "Form{" +
                "file=" + file +
                ", shape='" + shape + '\'' +
                '}';
    }
}