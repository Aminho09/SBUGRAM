package Server;

import common.Post;
import common.User;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class DataBase {
    public static final String FILE_PREFIX = "./src/Server/Database/";
    public static final String PROFILES_FILE = FILE_PREFIX + "AccountsDB";
    public static final String MAILS_FILE =  FILE_PREFIX + "PostDB";


    private static DataBase ourInstance = new DataBase();


    public static DataBase getInstance() {
        return ourInstance;
    }

    public synchronized void initializeServer(){
        try {
            FileInputStream fin=new FileInputStream(DataBase.PROFILES_FILE);
            ObjectInputStream inFromFile=new ObjectInputStream(fin);
            Server.allUsers = new ConcurrentHashMap<>( (ConcurrentHashMap<String, User>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.allUsers = new ConcurrentHashMap<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(DataBase.MAILS_FILE);
            ObjectInputStream inFromFile = new ObjectInputStream(fin);
            Server.allPosts = new ConcurrentSkipListSet<>( (ConcurrentSkipListSet<Post>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.allPosts = new ConcurrentSkipListSet<>();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void updateDataBase(){
        try {
            FileOutputStream fout = new FileOutputStream(PROFILES_FILE);
            ObjectOutputStream objToFile = new ObjectOutputStream(fout);
            objToFile.writeObject(Server.allUsers); //writing profiles
            objToFile.close();
            fout.close();

            fout = new FileOutputStream(MAILS_FILE);
            objToFile = new ObjectOutputStream(fout);
            objToFile.writeObject(Server.allPosts); // writing mails
            objToFile.close();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
