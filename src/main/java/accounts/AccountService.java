package accounts;


import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private DBService dbService;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        sessionIdToProfile = new HashMap<>();
        
         dbService = new DBService();
        // dbService.printConnectInfo();
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        dbService.addUser(userProfile.getLogin(),  userProfile.getPass());
        //loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserProfile(long id) throws DBException {
        UsersDataSet userDataSet = dbService.getUser(id);
        return userDataSet.getUserProfile();
    }
    
    public UsersDataSet getUser(long id) throws DBException {
        return dbService.getUser(id);
    }

    public long getUserByLogin(String login) throws DBException {
        return dbService.getUserId(login);
    }

    public ArrayList<UsersDataSet> getAllUsers() throws DBException {
        return dbService.getAllUsers();
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public String getAllUsersBySession(){
        String result = "";
        for (UserProfile user : sessionIdToProfile.values()){
            result = result.concat(user.getLogin()+"\n");
        }
        return result;
    }
}