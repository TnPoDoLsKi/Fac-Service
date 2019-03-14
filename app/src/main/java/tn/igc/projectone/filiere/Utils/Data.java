package tn.igc.projectone.filiere.Utils;

import android.util.Log;

import java.util.ArrayList;

/**
 * a class to hold data ( like a major's name and id)
 */
public class Data {
    private String id, name;

    public Data(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * copy name values from an array list of objects to an array
     *
     * @param dataArrayList : array list of objects
     * @return : an array of names
     */
    static public String[] copyValues(ArrayList<Data> dataArrayList) {

        String[] res = new String[dataArrayList.size()];
        for (int i = 0; i < dataArrayList.size(); i++) {
            res[i] = dataArrayList.get(i).getName();
        }

        return res;
    }

    static public String[] copyValues(Data[] dataArrayList) {

        String[] res = new String[dataArrayList.length];
        for (int i = 0; i < dataArrayList.length; i++) {
            res[i] = dataArrayList[i].getName();
        }

        return res;
    }

    static public ArrayList<String> copyIds(ArrayList<Data> dataArrayList) {

        ArrayList<String> res = new ArrayList<>();

        for (Data data : dataArrayList) {
            res.add(data.getId());
        }

        return res;
    }

    static public String getNameFromId(String id, ArrayList<Data> dataArrayList) {
        for (Data d :
            dataArrayList) {
            if (d.getId().equals(id))
                return d.getName();
        }
        return "Error";
    }

    static public String getIdFromName(String name, ArrayList<Data> dataArrayList) {
        for (Data d : dataArrayList)
            if (d.getName().equals(name))
                return d.getId();

        return "Error";
    }

    static public Data getDataFromName(String name, ArrayList<Data> dataArrayList) {
        for (Data d :
            dataArrayList) {
            if (d.getName().equals(name))
                return d;
        }
        return null;
    }

    static public void remove(String name, ArrayList<Data> dataArrayList) {
        for (int i = 0; i < dataArrayList.size(); i++) {
            if (dataArrayList.get(i).getName().equals(name)) {
                dataArrayList.remove(i);
                break;
            }
        }
        Log.d("matarr", "remove: " + dataArrayList);
    }

    static public boolean contains(String name, ArrayList<Data> dataArrayList) {
        for (int i = 0; i < dataArrayList.size(); i++) {
            if (dataArrayList.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
