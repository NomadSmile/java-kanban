import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    int nextID = 1;

    HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SubTask> subTasks = new HashMap<>();


    // Группа методов по добавлению задач
    public void addSimpleTask(SimpleTask simpleTask) {
        simpleTask.id = nextID;
        nextID++;
        simpleTasks.put(simpleTask.id, simpleTask);
    }

    public void addEpic(Epic epic) {
        epic.id = nextID;
        nextID++;
        epics.put(epic.id, epic);
    }

    public void addSubTask(SubTask subTask) {
        // Сначала добавляется эпик(общая задача), которая декомпозируется на подзадачи
        // Когда мы добавляем подзадачу, то уже знаем, к какому эпику она относится
        // И этот эпик указывается в параметрах метода. Получается, без эпика подзадачу не добавить.
        subTask.id = nextID;
        nextID++;
        subTasks.put(subTask.id, subTask);

        // тут меняем статус эпика в зависимости от подзадач
        changeEpicStatus(subTask);
    }
    // закрытый метод по обновлению статуса Эпика в зависимости от статусов Подзадач
    private void changeEpicStatus (SubTask subTask) {
        Epic epic = epics.get(subTask.colID);

        ArrayList<Status> Statuses = new ArrayList<>();
        for (Integer checkID : epic.subIDs) {
            Statuses.add(subTasks.get(checkID).taskStatus);
        }
        if(Statuses.contains(Status.NEW) && (!Statuses.contains(Status.IN_PROGRESS))
                && (!Statuses.contains(Status.DONE)) || Statuses.contains(null)) {
            epic.taskStatus = Status.NEW;
        } else if ((!Statuses.contains(Status.NEW)) && (!Statuses.contains(Status.IN_PROGRESS))
                && Statuses.contains(Status.DONE)) {
            epic.taskStatus = Status.DONE;
        } else {
            epic.taskStatus = Status.IN_PROGRESS;
        }
        if (!epic.subIDs.contains(subTask.id)) {
            epic.subIDs.add(subTask.id);
        }

    }

    // Группа методов по обновлению задач по id
    public void updateSimpleTask(SimpleTask simpleTask) {
        simpleTasks.put(simpleTask.id, simpleTask);

    }

    public void updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.id);
        epic.subIDs = currentEpic.subIDs; // передача списка ID подзадач из старого эпика в новый
        epics.put(epic.id, epic);

    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.id, subTask);
        changeEpicStatus(subTask);

    }

    // Группа методов по получению всех задач
    public ArrayList<SimpleTask> getSimpleTasks() {
        ArrayList<SimpleTask> listOfSimpleTasks = new ArrayList<>();
        for (SimpleTask task : simpleTasks.values()) {
            listOfSimpleTasks.add(task);
        }
        return listOfSimpleTasks;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> listOfEpics = new ArrayList<>();
        for (Epic task : epics.values()) {
            listOfEpics.add(task);
        }
        return listOfEpics;
    }

    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> listOfSubs = new ArrayList<>();
        for (SubTask task : subTasks.values()) {
            listOfSubs.add(task);
        }
        return listOfSubs;
    }

    // Группа методов по удалению всех задач
    public void cleanSimpleTasks() {
        simpleTasks.clear();
    }

    public void cleanEpics() {
        epics.clear();
        subTasks.clear(); // т.к. подзадачи без эпиков у меня существовать не могут, то долой их
    }

    public void cleanSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.subIDs.clear(); // очистка всех эпиков от ID подзадач
            epic.taskStatus = Status.NEW; // при удалении всех подзадач статус эпиков становится NEW
        }

    }

    // Группа методов получения задач по ID
    public SimpleTask getSimpleTaskByID(int id) {
        return simpleTasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskByID(int id) {
        return subTasks.get(id);
    }

    // Группа методов удаления задач по ID
    public void removeSimpleTaskByID(Integer id) {
        simpleTasks.remove(id);
    }

    public void removeEpicByID(Integer id) {
        epics.remove(id);
        for(SubTask task : subTasks.values()) {
            if (task.colID == id) {
                subTasks.remove(task.id); // при удалении эпика удаляем его подзадачи
                return;
            }
        }
    }

    public void removeSubTaskByID(Integer id) {
        subTasks.remove(id);
        for(Epic epic : epics.values()) { // поиск и удаление подзадачи из эпика
            if (epic.subIDs.contains(id)) {
                epic.subIDs.remove(id);

                // корректировка статуса Эпика
                ArrayList<Status> Statuses = new ArrayList<>();
                for (Integer checkID : epic.subIDs) {
                    Statuses.add(subTasks.get(checkID).taskStatus);
                }
                if(Statuses.contains(Status.NEW) && (!Statuses.contains(Status.IN_PROGRESS))
                        && (!Statuses.contains(Status.DONE)) || Statuses.contains(null)) {
                    epic.taskStatus = Status.NEW;
                } else if ((!Statuses.contains(Status.NEW)) && (!Statuses.contains(Status.IN_PROGRESS))
                        && Statuses.contains(Status.DONE)) {
                    epic.taskStatus = Status.DONE;
                } else {
                    epic.taskStatus = Status.IN_PROGRESS;
                    return;
                }
            }
        }
    }

    // Получение списка всех подзадач определённого эпика
    public ArrayList<SubTask> getSubsFromEpic(int id) {
        ArrayList<SubTask> subsFromEpic = new ArrayList<>();
        Epic epic = epics.get(id);
        for (int i = 0; i < epic.subIDs.size(); i++) {
            subsFromEpic.add(subTasks.get(epic.subIDs.get(i)));
        }
        return subsFromEpic;
    }
}
