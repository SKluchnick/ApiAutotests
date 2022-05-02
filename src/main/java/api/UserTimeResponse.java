package api;

public class UserTimeResponse extends UserTime{

    public String updatedAt;

    public UserTimeResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public UserTimeResponse(){
        super();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
