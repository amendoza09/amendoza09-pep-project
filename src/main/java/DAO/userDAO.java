package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userDAO {
    
    public Account createUser(Account account){

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    return new Account(
                        rs.getInt(1),
                        account.getUsername(),
                        account.getPassword()
                    );
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findUserAccount(String name, String password) {

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

    public Account findUserById (int id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
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

    public Account findUserByUsername (String username) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
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
