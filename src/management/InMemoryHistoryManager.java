package management;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> taskHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.remove(0); // эксперементировал с тестами, нужно было, чтобы ссылки были разныые
        }
        taskHistory.add(task);
    }

    // Сначала нас просят занести этот метод в TaskManager, затем, вроде бы, нужно перенести его сюда.
    // Но это не точно.
    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }
}
