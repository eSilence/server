package chat;

import accounts.AccountService;
import chat.mapWords.DirtyWords;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private Session session;
    private AccountService accountService;
    private DirtyWords dirtyWords;
   // private String sessionId;

    public ChatWebSocket(ChatService chatService, AccountService accountService/*, String sessionId*/) {
        this.chatService = chatService;
        this.accountService = accountService;
        dirtyWords = new DirtyWords();
      //  String s = "users:"+accountService.getAllUsersBySession();
      //  chatService.sendMessage("users:\n"+accountService.getAllUsersBySession());
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;

        Gson gson = new Gson();
        String json = gson.toJson(new ChatMess(1, "users:\n"+accountService.getAllUsersBySession() ));

        chatService.sendMessage(json);

    }

    @OnWebSocketMessage
    public void onMessage(String data) {

     //   chatService.sendMessage(data);

//        String [] wordArr = data.split(":")[1].split(" ");
//        for(int i = 0; i < wordArr.length; i++) {
//          if (dirtyWords.findFirstWord(wordArr[i]))
//              wordArr[i] =  dirtyWords.replaceWord(wordArr[i]);
//        }
//        String data2 = "";
//        for(String word: wordArr){
//            data2 = data2.concat(word+" ");
//        }
        Gson gson = new Gson();
        String json = gson.toJson(new ChatMess(0, data));
        chatService.sendMessage(json);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
