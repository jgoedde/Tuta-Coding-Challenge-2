import java.util.ArrayList;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        SpamService spamService = new SpamService();

        ArrayList<Email> emails = IntStream.range(0, args.length)
                .mapToObj(i -> new Email(i, args[i]))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        spamService.getSpamProbabilities(emails).forEach((email, spamPercentage) ->
                System.out.println(email.Content() + " - " + spamPercentage.toString() + " spam probability"));
    }
}