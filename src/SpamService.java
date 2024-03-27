import java.util.*;
import java.util.stream.Collectors;

public class SpamService {

    /**
     * Calculates the spam probability for each email in the provided list and returns a sorted map
     * where each email is associated with its spam probability.
     *
     * @param emails A list of emails to calculate spam probabilities for.
     * @return A map where each email is associated with its spam probability, sorted by spam probability in ascending order.
     */
    public Map<Email, SpamProbability> getSpamProbabilities(List<Email> emails) {
        Map<Email, SpamProbability> result = new HashMap<>();

        for (Email email : emails) {
            SpamProbability spamProbability = calculateSpamProbability(email, emails);
            result.put(email, spamProbability);
        }

        // We just for fun sort the map by spam probability
        return result
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(SpamProbability::percentage)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private SpamProbability calculateSpamProbability(Email targetEmail, List<Email> emails) {
        double totalSimilarity = 0.0;

        for (Email email : emails) {
            if (email.equals(targetEmail)) {
                continue;
            }

            double similarity = calculateJaccardSimilarity(targetEmail.Content(), email.Content());
            totalSimilarity += similarity;
        }

        return new SpamProbability(totalSimilarity / (emails.size() - 1));
    }

    private double calculateJaccardSimilarity(String text1, String text2) {
        if (text1.isEmpty() && text2.isEmpty()) {
            return 1.0; // Both texts are empty, indicating complete similarity
        } else if (text1.equals(text2)) {
            return 1.0; // Both texts are identical, indicating complete similarity
        }

        Set<String> words1 = new HashSet<>(Arrays.asList(text1.split(" ")));
        Set<String> words2 = new HashSet<>(Arrays.asList(text2.split(" ")));

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        return (double) intersection.size() / union.size();
    }
}
