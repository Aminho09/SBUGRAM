package common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileMaker {
    public static void main(String[] args) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\Ap projectss\\Projeh\\src\\Server\\Database\\AccountsDB");
            FileOutputStream fileOutputStream1 = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\Ap projectss\\Projeh\\src\\Server\\Database\\PostDB");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
