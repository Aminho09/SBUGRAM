package Server;

import common.Post;
import common.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class DataBase {
    public static final String FILE_PREFIX = "./src/Server/Database/";
    public static final String PROFILES_FILE = FILE_PREFIX + "AccountsDB";
    public static final String POSTS_FILE =  FILE_PREFIX + "PostDB";


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
            FileInputStream fin = new FileInputStream(DataBase.POSTS_FILE);
            ObjectInputStream inFromFile = new ObjectInputStream(fin);
            Server.allPosts = new Vector<>( (ArrayList<Post>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.allPosts = new Vector<>();
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

            fout = new FileOutputStream(POSTS_FILE);
            objToFile = new ObjectOutputStream(fout);
            objToFile.writeObject(new ArrayList<>(Server.allPosts)); // writing mails
            objToFile.close();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
