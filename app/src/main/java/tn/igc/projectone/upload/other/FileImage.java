package tn.igc.projectone.upload.other;

import okhttp3.MultipartBody;

public class FileImage {
    String image;
    String name;
    MultipartBody.Part part;


    public MultipartBody.Part getPart() {
        return part;
    }

    public FileImage(String image, String name, MultipartBody.Part part) {
        this.image = image;
        this.name = name;
        this.part = part;

    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileImage{" +
            "image='" + image + '\'' +
            ", name='" + name + '\'' +
            ", part=" + part +
            '}';
    }
}
