public class SimpleTask extends Task {

    public SimpleTask(String title, String description, Status taskStatus) {
        super(title, description, taskStatus);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
