import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Email> emails = SpamService.generateDummyMails();

        SpamService spamService = new SpamService();

        spamService.getSpamProbabilities(emails).forEach((email, spamPercentage) ->
                System.out.println(email.Content() + " - " + spamPercentage.toString() + " spam probability"));
    }
}