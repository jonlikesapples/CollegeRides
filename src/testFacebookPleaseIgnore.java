import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Comment;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * Created by JonWong on 11/1/16.
 */
public class testFacebookPleaseIgnore {
    @SuppressWarnings({"deprecation"})
    public static void main(String[] args) {

        try {
            String MY_ACCESS_TOKEN = "EAACEdEose0cBANI2g8HwxZAhe8d53q9YsK4Gs46ENuF3mRXjkq6EYXsxZAhKgkYUmRCD0PKu8bItYcSOAI9hnnJf7opvZCKyAkGxXT7q8S68TqgZCoICjCZBQzrAUjKRRyObu2AZAUd4nO8b7GNDh67DqZA8Qo5uLFUwsOnUSZA53AZDZD";
            FacebookClient fbclient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

            User me = fbclient.fetchObject("me", User.class);
            System.out.println(me.getName());

            Connection<Group> groupsFeed = fbclient.fetchConnection("me/groups", Group.class);
            for (java.util.List<Group> page : groupsFeed) {
                for (Group aGroup : page) {

                    if (aGroup.getName().contains("LBCSJ")) {

                        System.out.println("PAGE:" + aGroup.getName());
                        Connection<Post> postFeed = fbclient.fetchConnection(aGroup.getId() + "/feed", Post.class);


                        while (postFeed.hasNext())
                        {
                            for (java.util.List<Post> postPage : postFeed) {
                                System.out.println("New postpage!");
                                System.out.println("size of postpage: " + postPage.size());

                            }
                        }

                    }

                }
            }
        }
        catch(FacebookOAuthException E)
        {
            System.out.println("Token Expired!");
        }
    }
}



