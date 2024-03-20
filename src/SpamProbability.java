/**
 * Value Object representing the spam probability of an email.
 */
public record SpamProbability(Double percentage) {

    /**
     * @return The spam probability as a percentage string.
     */
    @Override
    public String toString() {
        return Math.round(percentage * 100) + "%";
    }
}