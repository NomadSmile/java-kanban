import java.util.Objects;

public class Task {
    int id;
    String title;
    String description;
    Status taskStatus;

    public Task(String title, String description, Status taskStatus) {

        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description)
                && taskStatus == task.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, taskStatus);
    }
}
