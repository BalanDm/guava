public class UserProduct {
    private Long id;

    public UserProduct(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "id=" + id +
                '}';
    }
}
