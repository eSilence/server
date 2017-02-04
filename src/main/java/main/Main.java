package main;

import accounts.AccountService;
import accounts.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import servlets.WebSocketChatServlet;

/**
 * ??Задача:
 Часть первая (аналогично заданию 2)
 Написать сервер с двумя сервлетами:
 SignUpServlet для обработки запросов на signup и
 SignInServlet для обработки запросов на signin

 Сервлеты должны слушать POST запросы с параметрами
 login
 password

 При получении POST запроса на signup сервлет SignUpServlet должн запомнить логин и пароль в AccountService.
 После этого польователь с таким логином считается зарегистрированным.
 При получении POST запроса на signin, после регистрации, SignInServlet проверяет,
 логин/пароль пользователя. Если пользователь уже зарегистрирован, север отвечает

 Status code (200)
 и текст страницы:
 Authorized: login

 если нет:
 Status code (401)
 текст страницы:
 Unauthorized

 Часть вторая (работа с базой)
 Для запоминания пользователя AccountService должен использовать базу данных.
 Для теста используйте базу H2 над файлом в той же директории, что и src

 String url = "jdbc:h2:./h2db";
 String name = "test";
 String pass = "test";

 JdbcDataSource ds = new JdbcDataSource();
 ds.setURL(url);
 ds.setUser(name);
 ds.setPassword(pass);

 Для хранения данных пользователя используйте таблицу users:
 create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id));
 Сервер должен создавать таблицу при старте если она не существует.

 При получении запроса на signup сервлет должен обратиться к DBService и записать логин и пароль в таблицу.

 Тестирующая система:
 1. запускает сервер
 2. присылает на сервер запрос на регистрацию (signup)
 3. перезапускает сервер
 4. присылает запрос на авторизацию (signin)

 Инструкция подготовки к локальной проверке:
 Соберите сервер со всеми зависимостями на библиотеки в server.jar
 Для этого запустите Maven projects/<Project name>/Plugins/assembly/assembly:single
 либо assembly.sh (assembly.bat)

 Скопируйте server.jar на уровень src и запустите
 java -jar server.jar

 В логах консоли вы должны увидеть сообщения о старте сервера.
 Проверьте, что сервер отвечает на запросы браузера.

 Инструкция подготовки к автоматической проверке:
 Добавьте в лог сообщение "Server started". По появлению в логе этого сообщения тестирующая система пойдет, что к вашему серверу можно обращаться.
 Соберите server.jar содержащий все библиотеки.

 Во время проверки тестовая система:
 запускает ваш сервер, ждет пока "Server started",
 посылает POST запрос на
 http://localhost:8080/signup
 останавливает сервер
 запускает ваш сервер, ждет пока "Server started",
 посылает POST запрос на
 http://localhost:8080/signin
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));
        accountService.addSession("1", accountService.getUserProfile(1));
        accountService.addSession("2", accountService.getUserProfile(2));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new WebSocketChatServlet(accountService)), "/chat");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }

}
