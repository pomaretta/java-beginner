import services.Database;

import javax.xml.crypto.Data;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/*

    Online Chat Application
        For terminal
    www.carlospomares.es

    Login or signup, you can use your account,
    You can enter rooms or create one,
    And most important, you can connect with others.

*/

public class One {

    private User usuario;
    private Sala sala;
    private Scanner scanner = new Scanner(System.in);
    public static String idSala;

    private One(){
        usuario = new User();
    }

    private boolean checking(Scanner order){
        System.out.println(internalMessage("CHECKING_MODULE"));
        boolean check = false;
        boolean loop = true;
        do{
            System.out.print("SI/NO: ");
            if(order.next().toLowerCase().equals("si")){
                check = true;
                loop = false;
            } else if(order.next().toLowerCase().equals("no")){
                check = false;
                loop = false;
            }
        }
        while(loop);
        return check;
    }

    public static void main(String[] args){

        boolean noexit = true;
        String order;
        One App = new One();

        do {
            boolean success = false;

            System.out.println(App.internalMessage("WELCOME"));
            System.out.println("---- QUE DESEAS HACER ----");
            System.out.println("---- LOGIN-SIGNUP ----");
            boolean loop = true;
            do {
                System.out.print("ORDEN: ");
                order = App.scanner.next();
                if(order.toLowerCase().equals("login") || order.toLowerCase().equals("signup")){
                    loop = false;
                }
            } while (loop);
            success = true;
            do {
                boolean ordenAnterior = false;
                boolean loggin = true;
                while(loggin) {

                    if (order.toLowerCase().equals("login")) {

                        String user, passwd;

                        System.out.println(App.internalMessage("LOGIN_MODULE"));
                        System.out.print("USUARIO: ");
                        user = App.scanner.next();
                        System.out.print(user + ", ¿ES CORRECTO?: ");
                        if (App.checking(App.scanner)) {
                            System.out.print("CONTRASEÑA: ");
                            passwd = App.scanner.next();
                            System.out.print(passwd + ", ¿ES CORRECTO?: ");
                            if (App.checking(App.scanner)) {
                                App.usuario = App.usuario.logIn(user, passwd);
                                App.sala = new Sala(App.usuario);
                                loggin = false;
                            } else {
                                success = false;
                            }
                        } else {
                            success = false;
                        }

                    } else if (order.toLowerCase().equals("signup")) {

                        String user, passwd;

                        System.out.println(App.internalMessage("SIGNUP_MODULE"));
                        System.out.print("USUARIO: ");
                        user = App.scanner.next();
                        System.out.print(user + ", ¿ES CORRECTO?: ");
                        if (App.checking(App.scanner)) {
                            System.out.print("CONTRASEÑA: ");
                            passwd = App.scanner.next();
                            System.out.print(passwd + ", ¿ES CORRECTO?: ");
                            if (App.checking(App.scanner)) {
                                App.usuario = App.usuario.signUp(user,passwd);
                                App.sala = new Sala(App.usuario);
                                loggin = false;
                            } else {
                                success = false;
                            }
                        } else {
                            success = false;
                        }

                    }
                }

                System.out.println(App.internalMessage("CONNECTED", App.usuario));
                System.out.println(App.internalMessage("ROOM_MODULE"));
                System.out.println("Ahora que tus sueños se han cumplido, que deseas hacer.");
                System.out.println("La recomendación del chef es CREAR o CONECTARSE a una sala de chat.");
                ;
                boolean connecting = true;
                do {

                    System.out.print("ORDEN: ");
                    order = App.scanner.next();

                    if(order.toLowerCase().equals("crear")){
                        ordenAnterior = true;
                        connecting = false;
                    } else if(order.toLowerCase().equals("conectarse")){
                        ordenAnterior = false;
                        connecting = false;
                    }

                } while (connecting);

                if(ordenAnterior){
                    do {} while (App.crearSala(App.scanner));
                }
                do {

                } while (App.conectarseSala(App.scanner));

                success = false;

            }while (success);
        } while (noexit);

    }

    private boolean crearSala(Scanner sc){

        String id,title;

        System.out.println("---- CREAR SALA ----");
        System.out.println("---- CONFIGURACIÓN ----");
        System.out.print("IDENTIFICADOR(NOMBRE): ");
        id = sc.next();
        System.out.print("IDENTIFICADOR=" + id);
        System.out.print("TÍTULO: ");
        title = sc.next();
        System.out.print("TÍTULO=" + title);
        if(checking(sc)){
            sala = new Sala(id,this.usuario.getId(),title, this.usuario);
            System.out.println("SALA CREADA: " + id + "-" + this.usuario.getId()+"-"+title);
        }
        return false;
    }

    private boolean conectarseSala(Scanner sc){

        boolean loop = true;

        String id = null;

        do {

            System.out.println("BIENVENIDO AL MÓDULO DE CONEXIÓN");
            System.out.println("PARA CONECTÁRTE A UNA SALA NECESITAS SU IDENTIFICADOR");
            System.out.println("PARA ELLO DEBERÁS SABERLO.");
            System.out.println("INTRODUCE EL IDENTIFICADOR DE TU SALA.");
            System.out.print("IDENTIFICADOR: ");
            id = sc.next();
            System.out.println("CONECTANDO CON LA SALA " + id);
            this.idSala = id;
            boolean connection = true;
            do {
                Collection<Message> mensajes = sala.getMessages();
                mensajes.forEach((m) -> {
                    System.out.println("<"+m.getUserID()+">: " + m.getContent());
                });
                System.out.println("OPCIONES: RECARGAR-ENVIAR-DESCONECTAR");
                String order = sc.next();
                switch (order.toLowerCase()){
                    case "recargar":
                        break;
                    case "enviar":
                        System.out.println("---- ENVIAR MENSAJE ----");
                        try{
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            String mensajeParaEnviar = br.readLine();
                            sala.sendMessage(mensajeParaEnviar,this.idSala);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        break;
                    case "desconectar":
                        connection = false;
                        break;
                }
            } while (connection);
            loop = false;
        } while (loop);

        return false;
    }

    private String internalMessage(String message){
        String mensaje = null;
        switch (message){
            case "WELCOME":
                mensaje = "---- HELLO VIAJERO DEL UNIVERSO ----";
                break;
            case "BYE":
                mensaje = "---- ADIÓS VIAJERO, NOS VEMOS EN LA PRÓXIMA ----";
                break;
            case "LOGIN_MODULE":
                mensaje = "---- LOGIN MODULE ----";
                break;
            case "SIGNUP_MODULE":
                mensaje = "---- SIGNUP MODULE ----";
                break;
            case "ROOM_MODULE":
                mensaje = "---- ROOM MODULE ----";
                break;
            case "CONNECTION_MODULE":
                mensaje = "---- CONNECTION MODULE ----";
                break;
            case "CHECKING_MODULE":
                mensaje = "---- CHECKING MODULE ----";
                break;
        }
        return mensaje;
    }

    private String internalMessage(String message, User e){
        String mensaje = null;
        switch (message){
            case "CONNECTED":
                mensaje = "BIENVENIDO " + e.getName() + " aquí podrás chatear con otras personas y compartir sueños...";
                break;
        }
        return mensaje;
    }
}

class User {

    private Database driver = null;

    private String nickname;
    private String password;
    private Integer id;

    public User(){

        driver = new Database();

    }

    public User(Integer id,String nickname, String password){

        this.nickname = nickname;
        this.password = password;
        this.id = id;

    }

    public String getName(){
        return this.nickname;
    }
    public Integer getId(){
        return this.id;
    }

    public void createUser(String user, String password){
        String stmt = "INSERT INTO user(nickname,password) VALUES ('" + user + "','" + password + "')";
        try {
            driver.newUpdate(stmt);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User signUp(String nickname,String passwd){
        User newUser = null;
        try {
            String stmt = "INSERT INTO user(nickname,password) VALUES ('"+nickname+"','"+passwd+"')" ;
            driver.newUpdate(stmt);
            newUser = new User().logIn(nickname,passwd);
        } catch (Exception e){
            e.printStackTrace();
        }
        return newUser;
    }

    public User logIn(String nickname,String passwd){

        String stmt = "SELECT * FROM user WHERE nickname = '"+ nickname +"'" ;
        ResultSet result = driver.newQuery(stmt);
        User r = null;

        try {

            while(result.next()){

                try {

                    if(passwd.equals(result.getString("password"))){
                        r = new User(result.getInt("id"),result.getString("nickname"),result.getString("password"));
                    }


                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        } catch (Exception e){ e.printStackTrace(); }


        return r;

    }

}

class Sala {

    private Database driver = null;
    private String roomID;
    private String title;
    private int owner;
    private User userConnected;
    private boolean Connection = false;
    private Message userMessage;

    public Collection<Message> messages = new ArrayList<Message>();
    private Collection<User> users;

    public Sala(User e){
        driver = new Database();
        userConnected = e;
    }

    public Sala(String id, int owner, String title, User e){

        driver = new Database();
        userConnected = e;

        this.roomID = id;
        this.title = title;
        this.owner = owner;

        users = new ArrayList<User>();

        createRoom();

    }

    public void setRoomID(String roomID){
        this.roomID = roomID;
    }

    private void createRoom(){

        String stmt = "INSERT INTO room(roomid,owner,title) VALUES ('"+ this.roomID +"','"+ this.owner +"','"+ this.title +"')";

        try {
            driver.newUpdate(stmt);
            System.out.println("ROOM CREATED");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getRoomID(){
        return this.roomID;
    }

    public boolean connect(String roomID){
        try {
            getMessages();
            this.Connection = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.Connection;
    }
    public void disconnect(){

    }

    private void addUser(User u){
        users.add(u);
    }

    private void removeUser(int userid){

    }

    public int sendMessage(String message,String roomID){
        Message toSend = new Message(userConnected.getId(),message, driver);
        toSend.setRoomID(roomID);
        return toSend.getTransactionID();
    }

    public ArrayList<Integer> getMessageId(){
        String stmt = "SELECT messageID FROM messageTransaction WHERE roomID = '"+One.idSala+"'";
        ResultSet rs;
        ArrayList<Integer> messagesID = new ArrayList<Integer>();
        try {

            rs = driver.newQuery(stmt);
            while(rs.next()){
                messagesID.add(rs.getInt("messageID"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return messagesID;
    }

    public Collection<Message> getMessages(){
        ResultSet rs;

        ArrayList<Integer> messagesID = getMessageId();

        try {

            for(int x = 0; x < messagesID.size(); x++){
                String stmt3 = "SELECT * FROM message WHERE messageid = '" + messagesID.get(x)+"'";
                rs = driver.newQuery(stmt3);
                if(rs.next()){
                    messages.add(new Message(rs.getInt("messageid"),rs.getInt("userid"),rs.getString("content")));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return messages;
    }
}

class Message {

    private int messageID;
    private int userID;
    private String content;
    private String sala;
    private int lastMessage;
    private int transactionID = 0;

    public Message(int userID,String content,Database driver){
        this.userID = userID;
        this.content = content;
        createMessage(driver);
        pullMessage(driver);
    }

    public Message(int messageID, int userID,String content){
        this.messageID = messageID;
        this.userID = userID;
        this.content = content;
    }

    public void setRoomID(String sala){
        this.sala = sala;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public int getTransactionID() {
        return transactionID;
    }

    private void createMessage(Database driver){
        String stmt = "INSERT INTO message(content,userid) VALUES ('" + this.content + "','" + this.userID + "')";
        try {
            driver.newUpdate(stmt);
            String check = "SELECT messageid FROM message WHERE userid = '" + this.userID + "'";
            ResultSet rs = driver.newQuery(check);
            int lastID = 0;
            while (rs.next()){
                lastID = rs.getInt("messageid");
            }
            this.lastMessage = lastID;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pullMessage(Database driver){
        String stmt = "INSERT INTO messageTransaction(roomID,messageID,userID) VALUES ('"+One.idSala+"','"+ this.lastMessage +"','"+ this.userID +"')";
        ResultSet rs = null;
        try {
            driver.newUpdate(stmt);
            String check = "SELECT transactionID FROM messageTransaction WHERE userID = '" + this.userID + "'";
            rs = driver.newQuery(check);
            int lastID = 0;
            while (rs.next()){
                lastID = rs.getInt("transactionID");
            }
            this.transactionID = lastID;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
