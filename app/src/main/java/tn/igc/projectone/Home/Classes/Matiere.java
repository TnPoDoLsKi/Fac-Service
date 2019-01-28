package tn.igc.projectone.Home.Classes;

public class Matiere {
    private String nom_matr;
    private int img_matr;

    public Matiere() {
    }

    public String getNom_matr() {
        return nom_matr;
    }

    public void setNom_matr(String nom_matr) {
        this.nom_matr = nom_matr;
    }

    public int getImg_matr() {
        return img_matr;
    }

    public void setImg_matr(int img_matr) {
        this.img_matr = img_matr;
    }

    public Matiere(String nom_matr, int img_matr) {
        this.nom_matr = nom_matr;
        this.img_matr = img_matr;

    }
}
