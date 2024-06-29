public class SubTask extends Task {

    int colID;

    public SubTask(String title, String description, Status taskStatus, int colID) {
        super(title, description, taskStatus);
        this.colID = colID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", colID=" + colID +
                '}';
    }
}
