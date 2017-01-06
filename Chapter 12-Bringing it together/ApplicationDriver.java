package packt.twitterdatascienceproject;

import static java.lang.System.out;
import java.util.Scanner;
import java.util.stream.Stream;

public class ApplicationDriver {

    private String topic;
    private String subTopic;
    private int numberOfTweets;

    public ApplicationDriver() {
        Scanner scanner = new Scanner(System.in);
        TweetHandler swt = new TweetHandler();
        swt.buildSentimentAnalysisModel();

        boolean running = true;
        while (running) {
            out.println("Welcome to the Tweet Analysis Application");
            out.print("Enter a topic: ");
            this.topic = scanner.nextLine();
            out.print("Enter a sub-topic: ");
            this.subTopic = scanner.nextLine().toLowerCase();
            out.print("Enter number of tweets: ");
            this.numberOfTweets = scanner.nextInt();
            performAnalysis();
        }
    }

    public void performAnalysis() {
        Stream<TweetHandler> stream
                = new TwitterStream(this.numberOfTweets, this.topic).stream();
        stream
                .map(s -> s.processJSON())
                .map(s -> s.toLowerCase())
                .filter(s -> s.isEnglish())
                .map(s -> s.removeStopWords())
                .filter(s -> s.containsCharacter(this.subTopic))
                .map(s -> s.performSentimentAnalysis())
                .forEach((TweetHandler s) -> {
                    s.computeStats();
                    out.println(s);
                });
        out.println();
        out.println("Positive Reviews: "
                + TweetHandler.getNumberOfPositiveReviews());
        out.println("Negative Reviews: "
                + TweetHandler.getNumberOfNegativeReviews());
    }

    public static void main(String[] args) {
        new ApplicationDriver();
    }

}
