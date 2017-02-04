package dbService.dataSets;

import accounts.UserProfile;

@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {
    private final long id;
    private final UserProfile userProfile;

    public UsersDataSet(long id, String name, String pass) {
        this.id = id;
        this.userProfile = new UserProfile(name, pass);
    }

    public String getLogin() {
        return userProfile.getLogin();
    }
    
     public String getPassword() {
        return userProfile.getPass();
    }

    public long getId() {
        return id;
    }
    public UserProfile getUserProfile(){
       return userProfile; 
    }
    
    @Override
    public String toString() {
        return "\n{" +
                "id=" + id +
                ", log: '" + getLogin() + "\' pass:" + getPassword()+
                "}";
    }
}