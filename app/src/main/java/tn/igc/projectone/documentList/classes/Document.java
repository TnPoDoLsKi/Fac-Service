package tn.igc.projectone.documentList.classes;


import tn.igc.projectone.R;


public class Document {
    private String type;
    private int semestre;
    private Boolean approved;
    private int NBDowloads;
    private boolean verifiedByProf;
    private String session;
    private String _id;
    private String title;
    private String filePath;
    private User user;
    private String major;
    private String subject;
    private int year;
    private String profName;
    private String description;
    private String createdAt;
    private String updatedAt;

    public Document() {
    }

    public Document(String _id, boolean verifiedByProf, String title, User user, String filePath) {
        this._id = _id;
        this.verifiedByProf = verifiedByProf;
        this.title = title;
        this.user = user;
        this.filePath = filePath;

    }

    public Document(String _id, String title, User user, String filePath) {
        this._id = _id;
        this.title = title;
        this.user = user;
        this.filePath = filePath;

    }


    public Document(String type, int semestre, Boolean approved, int NBDowloads, boolean verifiedByProf, String session, String _id, String title, String filePath, User user, String major, String subject, int year, String profName, String description, String createdAt, String updatedAt) {
        this.type = type;
        this.semestre = semestre;
        this.approved = approved;
        this.NBDowloads = NBDowloads;
        this.verifiedByProf = verifiedByProf;
        this.session = session;
        this._id = _id;
        this.title = title;
        this.filePath = filePath;
        this.user = user;
        this.major = major;
        this.subject = subject;
        this.year = year;
        this.profName = profName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public int getNBDowloads() {
        return NBDowloads;
    }

    public void setNBDowloads(int NBDowloads) {
        this.NBDowloads = NBDowloads;
    }

    public Boolean getVerifiedByProf() {
        return this.verifiedByProf;
    }

    public int isVerifiedByProf() {
        if (verifiedByProf) {
            return R.drawable.ic_check_circle_24px;
        } else {
            return 0;
        }
    }

    public void setVerifiedByProf(boolean verifiedByProf) {
        this.verifiedByProf = verifiedByProf;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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
        if (filePath.startsWith("/")) {
            return "http://igc.tn:3005" + filePath;
        } else {
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
