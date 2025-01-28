package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    
    public Account createUser(Account account){
        if(account.getUsername() == null && account.getPassword().length() < 4) return null;

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO account (username, password) VALUES = (?,?) RETURNING account_id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery(); 

            if(rs.next()) {
                account.setAccount_id(rs.getInt("account_id"));
                return account;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findUserAccount(String name, String password) {
        if(name == null || password == null) return null;

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }   
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
