package Client.Controller;

import Client.PageLoader;
import common.Post;

import java.io.IOException;

public class CommentItemController {
    private Post post;

    public CommentItemController(Post post) throws IOException {
        new PageLoader().load("CommentItem", this);
        this.post = post;
    }
}
