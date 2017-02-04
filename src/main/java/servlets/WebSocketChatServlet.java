package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.ChatService;
import chat.ChatWebSocket;

import java.io.IOException;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;
    private AccountService accountService;
    //private String sessionId;

    public WebSocketChatServlet(AccountService accountService) {
        this.chatService = new ChatService();
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("WebSocketChatServlet:");
//        String username = request.getParameter("username");
//        System.out.print(username);
//        sessionId = request.getSession().getId();
//        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        if (profile == null) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            Gson gson = new Gson();
//            String json = gson.toJson(profile);
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().println(json);
//            response.getWriter().println(sessionId)   ;
//            response.setStatus(HttpServletResponse.SC_OK);
//        }

    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService, accountService /*, sessionId*/));
    }
}
