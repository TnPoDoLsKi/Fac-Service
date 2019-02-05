package tn.igc.projectone.documentList.classes;

public class Document {
    private int profilePic;
    private int checked;
    private String docName;
    private String profileName;

    public Document(int profilePic, int checked, String docName, String profileName) {
        this.profilePic = profilePic;
        this.checked = checked;
        this.docName = docName;
        this.profileName = profileName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
