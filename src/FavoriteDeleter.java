/**
 * 
 * @author Scotty Ward
 * Twitter: @Scotty_ward9
 * Email: sward2011@my.fit.edu
 *
 */

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class FavoriteDeleter {

    public static void main(String[] args) throws InterruptedException {
        Twitter twitter = TwitterFactory.getSingleton();
        
        int likesRemoved = 0;
        
        List<Status> tweets = new ArrayList();
        
        try {
            while (twitter.getFavorites().size() > 0) {
                /*
                 * TODO Add more Ratelimit checks
                 */
                Paging p = new Paging(1, 200);
                tweets.addAll(twitter.getFavorites(p));
                while (tweets.size() > 0) {
                    // Remove first element from list and delete
                    Status s = tweets.remove(0);
                    twitter.destroyFavorite(s.getId());
                    /*
                     *  TODO Add method to save Favorites from certain people
                     */
                    //System.out.printf("%04d Deleted: %10s \n",likesRemoved, s.getId());
                    likesRemoved++;
                }

                // Sleep for 1 second.
                System.out.println("Sleeping for a second..");
                Thread.sleep(1000);
            }
        } catch (TwitterException e) {
            /*
             * Checks to see if Rate limit is exceded. If it is, sends a message
             * to restart in ___ seconds
             */
            if (e.exceededRateLimitation()) {
                System.out.printf("Rate limit excedded. Please try again in %d seconds",
                        e.getRateLimitStatus().getSecondsUntilReset() );
            }
            //e.printStackTrace();
        }
        
        System.out.printf("Removed %s likes\n", likesRemoved);
    }
}
