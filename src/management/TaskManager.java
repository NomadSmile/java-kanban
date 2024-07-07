package management;

import tasks.*;

import java.util.ArrayList;

/*
Класс TaskManager станет интерфейсом. В нём нужно собрать список методов, которые должны быть у любого
объекта-менеджера. Вспомогательные методы, если вы их создавали, переносить в интерфейс не нужно.
 */

public interface TaskManager {

    // Группа методов по добавлению задач
    void addSimpleTask(SimpleTask simpleTask);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    // Группа методов по обновлению задач по id
    void updateSimpleTask(SimpleTask simpleTask);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    // Группа методов по получению всех задач
    ArrayList<SimpleTask> getSimpleTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    // Группа методов по удалению всех задач
    void cleanSimpleTasks();

    void cleanEpics();

    void cleanSubTasks();

    // Группа методов получения задач по ID
    SimpleTask getSimpleTaskByID(Integer id);

    Epic getEpicByID(Integer id);

    SubTask getSubTaskByID(Integer id);

    // Группа методов удаления задач по ID
    void removeSimpleTaskByID(Integer id);

    void removeEpicByID(Integer id);

    void removeSubTaskByID(Integer id);

    // Получение списка всех подзадач определённого эпика
    ArrayList<SubTask> getSubsFromEpic(Integer id);

}
