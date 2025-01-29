package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.messageService;
import Service.userService;
import Model.Account;
import Model.Message;
import java.util.List;

import DAO.userDAO;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private final userService userService;
    private final messageService messageService;

    public SocialMediaController() {
        this.messageService = new messageService();
        this.userService = new userService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerUser);
        app.post("/login", this::userLogin);
        app.post("/messages", this::createMessage);
        
        app.get("/messages", this::allMessages);
        app.get("/messages/{message_id}", this::messageById);
        app.get("/accounts/{account_id}/messages", this::messagesByUser);

        app.delete("/messages/{message_id}", this::deleteMessage);
        
        app.patch("/messages/{message_id}", this::updateMessage);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account createUser = userService.registerUser(account);

        if(createUser != null) {
            ctx.json(createUser);
            return;
        } else {
            ctx.status(400);
        }
    }
    
    private void userLogin(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account authenticatedAccount = userService.userLogin(account.getUsername(), account.getPassword());

        if(authenticatedAccount != null) {
            ctx.status(200).json(authenticatedAccount);
            return;
        } else {
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message newMessage = messageService.createMessage(message);

        if(newMessage != null) {
            ctx.json(newMessage);
            return;
        } else {
            ctx.status(400);
        }
    }

    private void allMessages(Context ctx){
        List<Message> allMsg = messageService.getAllMessages();
        ctx.json(allMsg);
    }

    private void messageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if(message == null) {
            ctx.status(200);
        } else {
            ctx.json(message);
        }
    }

    private void deleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        Message deletedmessage = messageService.deleteMessageById(messageId);

        if(deletedmessage == null && message == null) ctx.status(200);
        else ctx.json(message);
    
    }

    private void updateMessage(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessage = ctx.bodyAsClass(Message.class);
        if (newMessage == null || newMessage.getMessage_text() == null || newMessage.getMessage_text().isBlank()) {
            ctx.status(400);
            return;
        }
        Message message = messageService.updateMessage(messageID, newMessage.getMessage_text());
        
        if(message != null) {
            ctx.json(message);
        }
        else ctx.status(400);

    }

    private void messagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> userMessages = messageService.getMessagesByUserId(accountId);
        ctx.status(200).json(userMessages);
    }
}