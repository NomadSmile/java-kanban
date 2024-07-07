package management;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> taskHistory = new ArrayList<>();

    // реализация именно такая ввиду моего понимания одного из запрашиваемых тестов в ТЗ
    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.remove(0);
        }
        Task checkedTask = new Task (task.getTitle(), task.getDescription(), task.getTaskStatus());
        taskHistory.add(checkedTask);
    }

    // Сначала нас просят занести этот метод в TaskManager, затем, вроде бы, нужно перенести его сюда.
    // Но это не точно.
    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }
}
