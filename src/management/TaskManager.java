package management;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

/*
Класс TaskManager станет интерфейсом. В нём нужно собрать список методов, которые должны быть у любого
объекта-менеджера. Вспомогательные методы, если вы их создавали, переносить в интерфейс не нужно.
 */

public interface TaskManager {

    List<Task> getTaskHistory();

    // Группа методов по добавлению задач
    void addSimpleTask(SimpleTask simpleTask);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    // Группа методов по обновлению задач по id
    void updateSimpleTask(SimpleTask simpleTask);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    // Группа методов по получению всех задач
    List<SimpleTask> getSimpleTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

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
    List<SubTask> getSubsFromEpic(Integer id);

}
