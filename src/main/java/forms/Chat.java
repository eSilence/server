package forms;

public class Chat {
    public static String htmlChat(String userName){
        return ("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                head(userName)+
                body(userName)+
                "</html>");

    }
    public static String head(String title) {
        return( "<head>\n"+
                    "<title>Chat " + title + "</title>\n" +
                    "<link rel='stylesheet' href='css/style.css'>\n" +
                    "<script type='text/javascript' src='script.js' ></script>\n" +
                "</head>\n");
    }
    public static String body (String username){
        return ("<body onload='init();'>\n" +
                    "<div id=\"body\">\n" +
                        "<div id=\"menu\">\n" +
                            "<p class=\"welcome\">\n" +
                                 "<h2> Welcome, <input id=\"username\" value='"+username+"' readonly=\"readonly\"/></h2>\n"+
                            "</p>\n </div>\n"+
                            "<div class=\"wrapper\">"+
                            "<div class=\"chatbox\" id=\"chatbox\">"+
                            "   <textarea id=\"messages\" rows=\"20\" cols=\"60\" readonly=\"readonly\"></textarea>"+
                            "</div>"+
                            "<div class=\"users\" id=\"chatbox\">\n"+
                            "   <textarea id=\"users\" rows=\"20\" cols=\"20\" readonly=\"readonly\"></textarea>\n"+
                            "</div>\n"+
                        "</div>\n"+
                        "<form name='message' action=''  onsubmit='sendMessage()'>\n"+
                            "<input name=\"usermsg\" type=\"text\" id=\"message\" size=\"40\"/>\n"+
                            "<input type='button' name='submitmsg' value='Send...' onclick='sendMessage()'/>\n"+
                        "</form>\n"+
                    "</div>"+
                "</body>");
    }
}
