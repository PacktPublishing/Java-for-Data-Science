package packt.twitterdatascienceproject;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class TwitterStream {
    private int numberOfTweets;
    private String topic;

    public TwitterStream() {
        this(100, "Stars Wars");
    }

    public TwitterStream(int numberOfTweets, String topic) {
        this.numberOfTweets = numberOfTweets;
        this.topic = topic;
    }

    public Stream<TweetHandler> stream() {
        String myKey = "sl2WbCf4UnIr08xvHVitHJ99r";
        String mySecret = "PE6yauvXjKLuvoQNXZAJo5C8N5U5piSFb3udwkoI76paK6KyqI";
        String myToken = "1098376471-p6iWfxCLtyMvMutTb010w1D1xZ3UyJhcC2kkBjN";
        String myAccess = "2o1uGcp4b2bFynOfu2cA1uz63n5aruV0RwNsUjRpjDBZS";

        out.println("Creating Twitter Stream");
        BlockingQueue<String> statusQueue = new LinkedBlockingQueue<>(1000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList("twitterapi", this.topic));
        endpoint.stallWarnings(false);
        Authentication twitterAuth = new OAuth1(myKey, mySecret, myToken, myAccess);

        BasicClient twitterClient = new ClientBuilder()
                .name("Twitter client")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(twitterAuth)
                .processor(new StringDelimitedProcessor(statusQueue))
                .build();

        twitterClient.connect();

        List<TweetHandler> list = new ArrayList();
        List<String> twitterList = new ArrayList();

        statusQueue.drainTo(twitterList);
        for(int i=0; i<numberOfTweets; i++) {
            String message;
            try {
                message = statusQueue.take();
                list.add(new TweetHandler(message));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

//        for (int msgRead = 0; msgRead < this.numberOfTweets; msgRead++) {
//            try {
//                if (twitterClient.isDone()) {
//                  //  out.println(twitterClient.getExitEvent().getMessage());
//                    break;
//                }
//
//                String msg = statusQueue.poll(10, TimeUnit.SECONDS);
//                if (msg == null) {
//                    out.println("Waited 10 seconds - no message received");
//                } else {
//                    list.add(new TweetHandler(msg));
//                    out.println("Added message: " + msg.length());
//                }
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }
        twitterClient.stop();
        out.printf("%d messages processed!\n", twitterClient.getStatsTracker().getNumMessages());

        return list.stream();
    }

}
