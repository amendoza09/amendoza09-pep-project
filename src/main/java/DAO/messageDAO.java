package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class messageDAO {
    
    private Message extractMessage(ResultSet rs) throws SQLException{
        return new Message(
            rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch")
        );
    }

    public Message createMessage(Message msg) {
        if(msg == null) return null;

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES = (?,?,?) RETURNING *";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return extractMessage(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> msgs = new ArrayList<>();
        String sql = "SELECT * FROM message";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                msgs.add(extractMessage(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return msgs;
    }

    public Message getMessageById(int id) {

        String sql = "SELECT * FROM message WHERE message_id = ?";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return extractMessage(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessageById (int id) {
        String sql = "SELECT * FROM message WHERE message_id = ? RETURNING *";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                extractMessage(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message updateMessage(int id, String messageText) {
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ? RETURNING *";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,messageText);
            ps.setInt(2,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                extractMessage(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Message> getMessagesByUser(int id) {
        List<Message> messageList = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE posted_by = ?";

        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                messageList.add(extractMessage(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
