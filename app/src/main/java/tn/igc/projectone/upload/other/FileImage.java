package tn.igc.projectone.upload.other;

public class FileImage {
    String image;
    String name;
    String percentage;


    public FileImage(String image, String name, String percentage) {
        this.image = image;
        this.name = name;
        this.percentage = percentage;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "FileImage{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}
