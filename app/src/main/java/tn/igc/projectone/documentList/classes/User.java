package tn.igc.projectone.documentList.classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tn.igc.projectone.R;

public class User extends RealmObject {

    private  String type ;
    private  Boolean deleted;
    @PrimaryKey
    private  String _id;
    private  String email;
    private  String firstName;
    private  String lastName;
    private  int avatar;
    public User(){}
    public User(String firstName,String lastName,int avatar){
        this.firstName=firstName;
        this.lastName=lastName;
        this.avatar=avatar;
    }

    public User(String type, Boolean deleted, String _id, String email, String firstName, String lastName, int avatar) {
        this.type = type;
        this.deleted = deleted;
        this._id = _id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        if (avatar==0){
            this.avatar= R.drawable.index;
        }
        else {
            this.avatar = avatar;
        }

    }
    public String getName(){
        String name =firstName.concat(" ").concat(lastName);
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar= avatar;
    }
}
