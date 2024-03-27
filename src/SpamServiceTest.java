import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SpamServiceTest {
    private SpamService spamService;

    @BeforeEach
    public void setup() {
        spamService = new SpamService();
    }

    @Test
    public void testGetSpamProbabilitiesWithEmptyEmails() {
        List<Email> emails = Collections.emptyList();
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSpamProbabilitiesWithWhitespaceEmail() {
        Email email = new Email(1, "     ");
        List<Email> emails = List.of(email);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(0.0), result.get(email), "Spam probability of an email with only spaces should be 0%");
    }

    @Test
    public void testGetSpamProbabilitiesWithWhitespaceEmails() {
        Email email1 = new Email(1, "     ");
        Email email2 = new Email(2, "    ");
        List<Email> emails = List.of(email1, email2);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(0.0), result.get(email1), "Spam probability of an email with only spaces should be 0%");
        assertEquals(new SpamProbability(0.0), result.get(email2), "Spam probability of an email with only spaces should be 0%");
    }

    @Test
    public void testGetSpamProbabilitiesWithMixedEmails() {
        Email email1 = new Email(1, "     ");
        Email email2 = new Email(2, "    ");
        Email email3 = new Email(3, "   d   ");
        List<Email> emails = List.of(email1, email2, email3);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(0.0), result.get(email1), "Spam probability of an email with only spaces should be 0%");
        assertEquals(new SpamProbability(0.0), result.get(email2), "Spam probability of an email with only spaces should be 0%");
        assertEquals(new SpamProbability(0.0), result.get(email3), "Spam probability of a unique email should be 0%");
    }

    @Test
    public void testGetSpamProbabilitiesWithWhiteSpaceEmailsButAlsoSpamEmails() {
        Email email1 = new Email(1, "     ");
        Email email2 = new Email(2, "    ");
        Email email3 = new Email(3, "What is the capital of France?");
        Email email4 = new Email(4, "What is the capital of Franceo?");
        List<Email> emails = List.of(email1, email2, email3, email4);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(0.0), result.get(email1), "Spam probability of an email with only spaces should be 0%");
        assertEquals(new SpamProbability(0.0), result.get(email2), "Spam probability of an email with only spaces should be 0%");
        assertNotEquals(new SpamProbability(0.0), result.get(email3), "Spam probability should not be 0% when there are other emails with similar text");
        assertNotEquals(new SpamProbability(0.0), result.get(email4), "Spam probability should not be 0% when there are other emails with similar text");
    }

    @Test
    public void testGetSpamProbabilitiesWithSingleEmail() {
        Email email = new Email(1, "Hello, how are you, John?");
        List<Email> emails = List.of(email);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(0.0), result.get(email), "Spam probability of a single email should be 0%");
    }

    @Test
    public void testGetSpamProbabilitiesWithTwoExactEmails() {
        Email email1 = new Email(1, "Hello, how are you, John?");
        Email email2 = new Email(2, "Hello, how are you, John?");
        List<Email> emails = List.of(email1, email2);
        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);
        assertEquals(new SpamProbability(1.0), result.get(email1));
        assertEquals(new SpamProbability(1.0), result.get(email2));
    }

    @Test
    public void testSpamProbabilityNotNaN() {
        Email emailWithNaN = new Email(1, "");

        List<Email> emails = List.of(emailWithNaN);

        Map<Email, SpamProbability> result = spamService.getSpamProbabilities(emails);

        assertFalse(result.isEmpty());
        result.forEach((email, spamProbability) -> assertFalse(Double.isNaN(spamProbability.percentage()),
                "Spam probability should not be NaN"));
    }
}