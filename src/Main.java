import management.InMemoryTaskManager;
import tasks.Status;
import management.TaskManager;
import tasks.*;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        SimpleTask simpleTask1 = new SimpleTask("КСГ", "Разработка графика", Status.NEW);
        SimpleTask simpleTask2 = new SimpleTask("Обед", "Сходить покушать", Status.NEW);

        inMemoryTaskManager.addSimpleTask(simpleTask1);
        inMemoryTaskManager.addSimpleTask(simpleTask2);

        Epic epic1 = new Epic("Разработка ПД", "Разработать 20 разделов ПД");
        Epic epic2 = new Epic("Разработка РД", "Разработать 20 разделов РД");

        inMemoryTaskManager.addEpic(epic1);
        inMemoryTaskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Книга 1 ПД", "Разработка книги 1 ПД", Status.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("Книга 2 ПД", "Разработка книги 2 ПД", Status.NEW, epic1.getId());
        SubTask subTask3 = new SubTask("Книга 1 РД", "Разработка книги 1 РД", Status.NEW, epic2.getId());

        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);

        System.out.println(inMemoryTaskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpics());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println(" ");

        simpleTask1.setTaskStatus(Status.IN_PROGRESS);
        simpleTask2.setTaskStatus(Status.IN_PROGRESS);

        subTask1.setTaskStatus(Status.DONE);
        subTask2.setTaskStatus(Status.DONE);
        subTask3.setTaskStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.updateSimpleTask(simpleTask1);
        inMemoryTaskManager.updateSimpleTask(simpleTask2);
        inMemoryTaskManager.updateSubTask(subTask1);
        inMemoryTaskManager.updateSubTask(subTask2);
        inMemoryTaskManager.updateSubTask(subTask3);

        System.out.println(inMemoryTaskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpics());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println(" ");

        inMemoryTaskManager.removeSimpleTaskByID(1);
        inMemoryTaskManager.removeSubTaskByID(6);

        System.out.println(inMemoryTaskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpics());
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println(" ");

    }
}