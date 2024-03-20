import java.util.Objects;

public record Email(int Id, String Content) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Id == email.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
