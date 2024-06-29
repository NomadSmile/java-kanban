public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        SimpleTask simpleTask1 = new SimpleTask("КСГ", "Разработка графика", Status.NEW);
        SimpleTask simpleTask2 = new SimpleTask("Обед", "Сходить покушать", Status.NEW);

        taskManager.addSimpleTask(simpleTask1);
        taskManager.addSimpleTask(simpleTask2);

        Epic epic1 = new Epic("Разработка ПД", "Разработать 20 разделов ПД", Status.NEW);
        Epic epic2 = new Epic("Разработка РД", "Разработать 20 разделов РД", Status.NEW);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Книга 1 ПД", "Разработка книги 1 ПД", Status.NEW, epic1.id);
        SubTask subTask2 = new SubTask("Книга 2 ПД", "Разработка книги 2 ПД", Status.NEW, epic1.id);
        SubTask subTask3 = new SubTask("Книга 1 РД", "Разработка книги 1 РД", Status.NEW, epic2.id);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        System.out.println(taskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(taskManager.getEpics());
        System.out.println(" ");
        System.out.println(taskManager.getSubTasks());
        System.out.println(" ");

        simpleTask1.taskStatus = Status.IN_PROGRESS;
        simpleTask2.taskStatus = Status.IN_PROGRESS;

        subTask1.taskStatus = Status.DONE;
        subTask2.taskStatus = Status.DONE;
        subTask3.taskStatus = Status.IN_PROGRESS;

        taskManager.updateSimpleTask(simpleTask1);
        taskManager.updateSimpleTask(simpleTask2);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);

        System.out.println(taskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(taskManager.getEpics());
        System.out.println(" ");
        System.out.println(taskManager.getSubTasks());
        System.out.println(" ");

        taskManager.removeSimpleTaskByID(1);
        taskManager.removeSubTaskByID(6);

        System.out.println(taskManager.getSimpleTasks());
        System.out.println(" ");
        System.out.println(taskManager.getEpics());
        System.out.println(" ");
        System.out.println(taskManager.getSubTasks());
        System.out.println(" ");

    }
}