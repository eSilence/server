package servlets;


import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import forms.Chat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
         response.getWriter().println("Sign Ip:");
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.getWriter().println("Sign In All:");

        String users = accountService.getAllUsersBySession() ;
        response.getWriter().println(users);

    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long id = 0;

        try {
            id = accountService.getUserByLogin(login);
        } catch (DBException ex) {
            Logger.getLogger(SignInServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UserProfile profile = null;
        try {
            profile = accountService.getUserProfile(id);
        } catch (DBException ex) {
            Logger.getLogger(SignInServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (profile == null || !profile.getPass().equals(pass)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            response.getWriter().println("<html><body><p> <a href='index.html'>Вернуться</a> </p></body></html>");
            response.getWriter().println("<html><body><p> <a href='login.html'>Повторить</a> </p></body></html>");
            return;
        }

        accountService.addSession(request.getSession().getId(), profile);
        //Gson gson = new Gson();
        //String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        //response.getWriter().println(json);
        //response.getWriter().println("Authorized: "+login);
        //response.getWriter().println("sigin request.getSession().getId(): "+request.getSession().getId());
        response.setStatus(HttpServletResponse.SC_OK);
        //response.getWriter().println("<html><body><p> <a href='index.html'>Вернуться</a> </p></body></html>");
        //response.sendRedirect(request.getContextPath() + "/chat.html");
        response.getWriter().println(Chat.htmlChat(login));
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
    }
}
