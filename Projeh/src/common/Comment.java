package common;

import java.io.Serializable;

public class Comment implements Serializable {
    private User writer;
    private String text;
    private Post commentedPost;
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getWriter() {
        return writer;
    }

    public String getText() {
        return text;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setCommentedPost(Post commentedPost) {
        this.commentedPost = commentedPost;
    }

    public Post getCommentedPost() {
        return commentedPost;
    }
}
