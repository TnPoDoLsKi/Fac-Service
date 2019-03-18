package tn.igc.projectone.Home.Classes;
public class Matiere {
    private String id_matr;
    private String nom_matr;
    private int img_matr;
    private int Cour;
    private int TD;
    private int DS;
    private int EX;
    private int TP;


    public Matiere() {
    }

    public void setId(String id) {
        this.id_matr = id;
    }

    public String getId() {

        return id_matr;
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

    public Matiere(String  id_matr , String nom_matr, int img_matr,int c,int td,int ds,int ex,int tp) {
        this.id_matr = id_matr ;
        this.nom_matr = nom_matr;
        this.img_matr = img_matr;
        this.Cour=c;
        this.TD=td;
        this.DS=ds;
        this.EX=ex;
        this.TP=tp;

    }


    public int getCour_c() {
        return Cour;
    }

    public int getTD_c() {
        return TD;
    }

    public int getDS_c() {
        return DS;
    }

    public int getEX_c() {
        return EX;
    }

    public int getTP_c() {
        return TP;
    }
}