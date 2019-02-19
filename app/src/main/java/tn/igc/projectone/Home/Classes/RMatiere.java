package tn.igc.projectone.Home.Classes;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RMatiere extends RealmObject {


    public static final String PROPERTY_MAJ_ID = "maj_id";
    public static final String PROPERTY_SEM = "sem";


    @PrimaryKey
    @Required
    public String id;
    public String nom;
    public int img;
    public int sem;
    public String maj_id;





}
