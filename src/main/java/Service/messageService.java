package Service;

import DAO.messageDAO;
import DAO.userDAO;
import Model.Message;

import java.util.List;

public class messageService {
    
    private messageDAO messageDAO;
    private userDAO userDAO;

    public messageService() {
        this.messageDAO = new messageDAO();
        this.userDAO = new userDAO();
    }

    public messageService(userDAO userDAO, messageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    public Message createMessage(Message message) {
        if(message.getMessage_text().length() <= 255 && !message.getMessage_text().isBlank() 
        && userDAO.findUserById(message.posted_by) != null){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }
    public Message updateMessage(int id, String msg) {
        if(msg == null || msg.isBlank() || msg.length() > 255) {
            return null;
        }

        Message existingMsg = messageDAO.getMessageById(id);
        if(existingMsg == null) {
            return null;
        }

        existingMsg.setMessage_text(msg);
        return messageDAO.updateMessage(id, msg);
    }
    public List<Message> getMessagesByUserId(int id) {
        return messageDAO.getMessagesByUser(id);
    }
}
