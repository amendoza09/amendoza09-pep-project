package Service;
import DAO.userDAO;
import Model.Account;

public class userService {

    private userDAO userDAO;

    public userService() {
        this.userDAO = new userDAO();
    }
    
    public userService(userDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Account registerUser(Account account) {
        if(account.getUsername().isBlank() || account.getUsername() == null
        || account.getPassword().length() < 4) {
            return null;
        } 
        return userDAO.createUser(account);    
    }

    public Account userLogin (String username, String password) {
        return userDAO.findUserAccount(username, password);
    }

    public Account findAccount(Account account) {
        return userDAO.findUserAccount(account.getUsername(), account.getPassword());
}
    
}
