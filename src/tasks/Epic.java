package tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subIDs = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
    }

    public void removeSubID(Integer id) {
        subIDs.remove(id);
    }

    public void cleanSubIDs() {
        subIDs.clear();
    }

    public void addSubID(Integer id) {
        if (id != this.id) {
            subIDs.add(id);
        } else {
            System.out.println("Вы пытаетесь добавить Эпик в список его собственных подзадач.");
        }
    }

    public ArrayList<Integer> getSubIDs() {
        ArrayList<Integer> copyOfSubIDs = subIDs;
        return  copyOfSubIDs;
    }

    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "subIDs=" + subIDs +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
