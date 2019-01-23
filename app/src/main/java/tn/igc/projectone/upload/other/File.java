package tn.igc.projectone.upload.other;

public class File {
    String image;
    String name;
    String percentage;


    public File(String image, String name, String percentage) {
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
        return "File{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}
