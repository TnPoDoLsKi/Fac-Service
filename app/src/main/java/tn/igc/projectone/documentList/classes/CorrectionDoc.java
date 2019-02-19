package tn.igc.projectone.documentList.classes;

import android.text.SpannableString;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tn.igc.projectone.R;

public class CorrectionDoc extends RealmObject {
 private Boolean approved;
 private Boolean verifiedByProf;
 private int score;
 @PrimaryKey
 private String _id;
 private String title;
 private String filePath;
 private User user;
 private String document;
 private String createdAt;
 private String updatedAt;
 public  CorrectionDoc(){}

    public CorrectionDoc(Boolean approved, Boolean verifiedByProf, int score, String _id, String title, String filePath, User user, String document, String createdAt, String updatedAt) {
        this.approved = approved;
        this.verifiedByProf = verifiedByProf;
        this.score = score;
        this._id = _id;
        this.title = title;
        this.filePath = filePath;
        this.user = user;
        this.document = document;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public int isVerifiedByProf() {
        if (verifiedByProf){
            return R.drawable.ic_check_circle_24px;
        }
        else {
            return 0;
        }
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getVerifiedByProf() {
        return verifiedByProf;
    }

    public void setVerifiedByProf(Boolean verifiedByProf) {
        this.verifiedByProf = verifiedByProf;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        if (filePath.startsWith("/")){
            return "http://igc.tn:3005"+filePath;
        }else {
            return filePath;
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

