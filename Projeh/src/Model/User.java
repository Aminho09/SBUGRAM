package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class User implements Serializable {
    private String firstname;
    private String surname;
    private int yearBirth;
    private int monthBirth;
    private int dayBirth;
    private Gender gender;
    private String username;
    private String password;
    private byte[] profileImage;
    private ArrayList<Post> userPosts = new ArrayList<>();


    public User(String firstname, String surname, int yearBirth, int monthBirth
            , int dayBirth, Gender gender, String username, String password, byte[] profileImage) {
        this.firstname = firstname;
        this.surname = surname;
        this.yearBirth = yearBirth;
        this.monthBirth = monthBirth;
        this.dayBirth = dayBirth;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
    }

    public void print(){
        System.out.println(Arrays.toString(profileImage));
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setUserPosts(ArrayList<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public void addPost(Post post){
        userPosts.add(post);
    }


    public int getMonthBirth() {
        return monthBirth;
    }

    public int getDayBirth() {
        return dayBirth;
    }

    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

}
