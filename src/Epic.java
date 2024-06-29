import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> subIDs = new ArrayList<>();

    public Epic(String title, String description, Status taskStatus) {
        super(title, description, taskStatus);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subIDs=" + subIDs +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
