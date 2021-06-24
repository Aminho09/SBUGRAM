package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class User implements Serializable, Comparable {
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
    private ArrayList<User> follower = new ArrayList<>();
    private ArrayList<User> following = new ArrayList<>();
    private long timeOfUser = Time.getMilli();
    private String timeString = Time.getTime();

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

    public User() {}

    public User(String firstname, String surname, int yearBirth, int monthBirth, int dayBirth,
                Gender gender, String username, String password, byte[] profileImage, ArrayList<Post> userPosts) {
        this.firstname = firstname;
        this.surname = surname;
        this.yearBirth = yearBirth;
        this.monthBirth = monthBirth;
        this.dayBirth = dayBirth;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.userPosts = userPosts;
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

    public String getTimeString() {
        return timeString;
    }

    public void setUserPosts(ArrayList<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }

    public void setMonthBirth(int monthBirth) {
        this.monthBirth = monthBirth;
    }

    public void setDayBirth(int dayBirth) {
        this.dayBirth = dayBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setFollower(ArrayList<User> follower) {
        this.follower = follower;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
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

    public ArrayList<User> getFollower() {
        return follower;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public long getTimeOfUser() {
        return timeOfUser;
    }

    public void setTimeOfUser(long timeOfUser) {
        this.timeOfUser = timeOfUser;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public int compareTo(Object o) {
        User anotherUser = (User) o;
        if (this.timeOfUser > anotherUser.timeOfUser)
            return -1;
        else
            return 1;
    }

    @Override
    public boolean equals(Object user) {
        return ((User) user).username.equals(this.username);
    }
}
