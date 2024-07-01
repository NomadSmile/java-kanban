import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int nextID = 1;

    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();


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
        // Сначала создается и добавляется в таблицу эпик(общая задача), которая декомпозируется на подзадачи
        // Когда мы создаём подзадачу, то уже знаем, указываем id эпика, к которому она относится

        if(epics.containsKey(subTask.getColID())) {
            subTask.id = nextID;
            nextID++;
            subTasks.put(subTask.id, subTask);
            epics.get(subTask.getColID()).addSubID(subTask.id); // добавляем id новой Подзадачи в Эпик

            changeEpicStatus(epics.get(subTask.getColID())); // тут меняем статус эпика в зависимости от подзадач
        } else {
            System.out.println("Введённая подзадача относится к Эпику, не внесённому в список.");
        }
    }
    // закрытый метод по обновлению статуса Эпика в зависимости от статусов Подзадач
    private void changeEpicStatus (Epic epic) {

        ArrayList<Status> statuses = new ArrayList<>();
        for (Integer checkID : epic.getSubIDs()) {
            statuses.add(subTasks.get(checkID).taskStatus);
        }
        if(statuses.contains(Status.NEW) && (!statuses.contains(Status.IN_PROGRESS))
                && (!statuses.contains(Status.DONE)) || statuses.contains(null)) {
            epic.taskStatus = Status.NEW;
        } else if ((!statuses.contains(Status.NEW)) && (!statuses.contains(Status.IN_PROGRESS))
                && statuses.contains(Status.DONE)) {
            epic.taskStatus = Status.DONE;
        } else {
            epic.taskStatus = Status.IN_PROGRESS;
        }

    }

    // Группа методов по обновлению задач по id
    public void updateSimpleTask(SimpleTask simpleTask) {
        if(simpleTasks.containsValue(simpleTask)) {
            simpleTasks.put(simpleTask.id, simpleTask);
        } else {
            System.out.println("Вы пытаетесь обновить задачу, отсутствующую в списке.");
        }

    }

    public void updateEpic(Epic epic, String title, String description) {

        /* мог неверно понять замечание. В данном случае пользователь вызывает метод, в параметрах указывает
        Эпик и поля. Если эпик есть в таблице, внутри метода поля обновляются.
        Есть сомнения в правильности, т.к. пользователь может хотеть обновить только одно поле.
        */
        if(epics.containsKey(epic.id)) {
            epic.title = title;
            epic.description = description;
        } else {
            System.out.println("Вы пытаетесь обновить Эпик, отсутствующий в списке.");
        }
    }

    public void updateSubTask(SubTask subTask) {

        if (!subTasks.containsKey(subTask.getColID())) {
            SubTask existingSubTask = subTasks.get(subTask.getId());
            if (existingSubTask.getColID() == subTask.getColID()) {
                subTasks.put(subTask.getId(), subTask);
                changeEpicStatus(epics.get(subTask.getColID()));
            }
        }
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
            epic.cleanSubIDs(); // очистка всех эпиков от ID подзадач
            changeEpicStatus(epic); // при удалении всех подзадач статус эпиков становится NEW
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
        if (epics.containsKey(id)) {
            for (Integer subID : getEpicByID (id).getSubIDs()) {
                removeSubTaskByID(subID);
            }
        } else {
            System.out.println("Вы пытаетесь удалить Эпик, отсутствующий в списке.");
        }
    }

    public void removeSubTaskByID(Integer id) {

        if (subTasks.containsKey(id) && subTasks.get(id) != null) {
            int colID = getSubTaskByID(id).getColID();
            Epic epic = epics.get(colID);
            epic.removeSubID(id);

            changeEpicStatus(epic);
            subTasks.remove(id);
        } else {
            System.out.println("Вы хотите удалить подзадачу, отсутствующую в списке");
        }
    }


    // Получение списка всех подзадач определённого эпика
    public ArrayList<SubTask> getSubsFromEpic(int id) {
        ArrayList<SubTask> subsFromEpic = new ArrayList<>();
        Epic epic = epics.get(id);
        if(epic != null) {
            for (int i = 0; i < epic.getSubIDs().size(); i++) {
                subsFromEpic.add(subTasks.get(epic.getSubIDs().get(i)));
            }
        }
        return subsFromEpic;
    }
}
