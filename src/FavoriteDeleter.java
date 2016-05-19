import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * 
 * @author Scotty Ward
 * Twitter: @Scotty_ward9
 * Email: sward2011@my.fit.edu
 *
 */
public class FavoriteDeleter {

    public static void main(String[] args) throws InterruptedException {
        Twitter twitter = TwitterFactory.getSingleton();
        
        int likesRemoved = 0;
        
        List<Status> tweets = new ArrayList();
        
        try {
            while (twitter.getFavorites().size() > 0 && likesRemoved < 500) {
                Paging p = new Paging(1, 200);
                System.out.println(tweets.size());
                tweets.addAll(twitter.getFavorites(p));
                System.out.println(tweets.size());
                while (tweets.size() > 0) {
                    // Remove first element from list and delete
                    Status s = tweets.remove(0);
                    twitter.destroyFavorite(s.getId());
                    //System.out.printf("%04d Deleted: %10s \n",likesRemoved, s.getId());
                    likesRemoved++;
                }

                // Sleep for 1 second.
                Thread.sleep(1000);
            }
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.printf("Removed %s likes\n", likesRemoved);
    }
}
