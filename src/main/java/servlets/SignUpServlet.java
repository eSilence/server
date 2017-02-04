package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import chat.ChatService;
import chat.ChatWebSocket;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get public user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Sign Up:");

        try {
            ArrayList<UsersDataSet> users = accountService.getAllUsers();
            response.getWriter().println(users);
        } catch (DBException e) {
            e.printStackTrace();
        }

    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            //        UserProfile profile = accountService.getUserByLogin(login);
//        if (profile != null) {
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().println("This login already exists");
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
//            return;
//        }
            
            accountService.addNewUser(new UserProfile(login, pass));
        } catch (DBException ex) {
            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Gson gson = new Gson();
        //String json = gson.toJson(accountService.getUserByLogin(login));
        //response.setContentType("application/json; charset=utf-8");
        //response.getWriter().println(json);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Success registered");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<html><body><p> <a href='index.html'>Вернуться</a> </p></body></html>");

    }

    //change profile
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
    }

}
