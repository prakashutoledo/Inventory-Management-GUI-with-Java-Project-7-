package inventorygui;
public class Member {
    public String memberName;
    public String memberLastName;
    public String userName;
    public String password;     
    public Member () {
        this.memberName = new  String();
        this.memberLastName = new String();
        this.userName = new String();
        this.password = new String();
    }
    public String getMemberName() {
        return memberName;
    }
     public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getMemberLastName() {
        return memberLastName;
    }
    public void setMemerLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
