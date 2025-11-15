public class SignupRequest {
    private String email;

    public SignupRequest() {
    }

    public SignupRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}