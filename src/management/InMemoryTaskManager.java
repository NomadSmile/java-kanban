package management;

import java.util.ArrayList;
import java.util.HashMap;
import tasks.*;

/*
Созданный ранее класс менеджера нужно переименовать в InMemoryTaskManager. Именно то, что менеджер хранит
всю информацию в оперативной памяти, и есть его главное свойство, позволяющее эффективно управлять задачами.
Внутри класса должна остаться реализация методов. При этом важно не забыть имплементировать TaskManager,
ведь в Java класс должен явно заявить, что он подходит под требования интерфейса.
 */

public class InMemoryTaskManager implements TaskManager {

    private int nextID = 1;

    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();


    /*
    Проверьте, что теперь InMemoryTaskManager обращается к менеджеру истории через интерфейс HistoryManager
    и использует реализацию, которую возвращает метод getDefaultHistory.
     */
    // ТЗ меня победило. Либо я не так всё прочитал, либо это надмозг избыточный
    public static ArrayList<Task> getTaskHistory() {
        return Managers.getDefaultHistory().getHistory();
    }

    // Группа методов по добавлению задач
    @Override
    public void addSimpleTask(SimpleTask simpleTask) {
        simpleTask.setId(nextID);
        nextID++;
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextID);
        nextID++;
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        // Сначала создается и добавляется в таблицу эпик(общая задача), которая декомпозируется на подзадачи
        // Когда мы создаём подзадачу, то уже знаем, указываем id эпика, к которому она относится

        if(epics.containsKey(subTask.getColID())) {
            subTask.setId(nextID);
            nextID++;
            subTasks.put(subTask.getId(), subTask);
            epics.get(subTask.getColID()).addSubID(subTask.getId()); // добавляем id новой Подзадачи в Эпик

            changeEpicStatus(epics.get(subTask.getColID())); // тут меняем статус эпика в зависимости от подзадач
        } else {
            System.out.println("Введённая подзадача относится к Эпику, не внесённому в список.");
        }
    }

    // закрытый метод по обновлению статуса Эпика в зависимости от статусов Подзадач
    private void changeEpicStatus(Epic epic) {

        ArrayList<Status> statuses = new ArrayList<>();
        for (Integer checkID : epic.getSubIDs()) {
            statuses.add(subTasks.get(checkID).getTaskStatus());
        }
        if (statuses.contains(Status.NEW) && (!statuses.contains(Status.IN_PROGRESS))
                && (!statuses.contains(Status.DONE)) || statuses.contains(null)) {
            epic.setTaskStatus(Status.NEW);
        } else if ((!statuses.contains(Status.NEW)) && (!statuses.contains(Status.IN_PROGRESS))
                && statuses.contains(Status.DONE)) {
            epic.setTaskStatus(Status.DONE);
        } else {
            epic.setTaskStatus(Status.IN_PROGRESS);
        }

    }

    // Группа методов по обновлению задач по id
    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        if(simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
        } else {
            System.out.println("Вы пытаетесь обновить задачу, отсутствующую в списке.");
        }

    }

    @Override
    public void updateEpic(Epic epic) {

        if(epics.containsKey(epic.getId())) {
            epics.get(epic.getId()).setTitle(epic.getTitle());
            epics.get(epic.getId()).setDescription(epic.getDescription());
        } else {
            System.out.println("Вы пытаетесь обновить Эпик, отсутствующий в списке.");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {

        if (subTasks.containsKey(subTask.getId())) { // добавил "!" потому что олень невнимательный
            SubTask existingSubTask = subTasks.get(subTask.getId());
            if (existingSubTask.getColID() == subTask.getColID()) {
                subTasks.put(subTask.getId(), subTask);
                changeEpicStatus(epics.get(subTask.getColID()));
            }
        }
    }
    // Группа методов по получению всех задач
    @Override
    public ArrayList<SimpleTask> getSimpleTasks() {
        ArrayList<SimpleTask> listOfSimpleTasks = new ArrayList<>();
        for (SimpleTask task : simpleTasks.values()) {
            listOfSimpleTasks.add(task);
        }
        return listOfSimpleTasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> listOfEpics = new ArrayList<>();
        for (Epic task : epics.values()) {
            listOfEpics.add(task);
        }
        return listOfEpics;
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> listOfSubs = new ArrayList<>();
        for (SubTask task : subTasks.values()) {
            listOfSubs.add(task);
        }
        return listOfSubs;
    }

    // Группа методов по удалению всех задач
    @Override
    public void cleanSimpleTasks() {
        simpleTasks.clear();
    }

    @Override
    public void cleanEpics() {
        epics.clear();
        subTasks.clear(); // т.к. подзадачи без эпиков у меня существовать не могут, то долой их
    }

    @Override
    public void cleanSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.cleanSubIDs(); // очистка всех эпиков от ID подзадач
            changeEpicStatus(epic); // при удалении всех подзадач статус эпиков становится NEW
        }

    }

    // Группа методов получения задач по ID
    @Override
    public SimpleTask getSimpleTaskByID(Integer id) {
        Managers.getDefaultHistory().add(simpleTasks.get(id));
        return simpleTasks.get(id);
    }

    @Override
    public Epic getEpicByID(Integer id) {
        Managers.getDefaultHistory().add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskByID(Integer id) {
        Managers.getDefaultHistory().add(subTasks.get(id));
        return subTasks.get(id);
    }

    // Группа методов удаления задач по ID
    @Override
    public void removeSimpleTaskByID(Integer id) {
        simpleTasks.remove(id);
    }

    @Override
    public void removeEpicByID(Integer id) {
        if (epics.containsKey(id)) {
            for (Integer subID : epics.get(id).getSubIDs()) {
                subTasks.remove(subID);
            }
            epics.remove(id);
        } else {
            System.out.println("Вы пытаетесь удалить Эпик, отсутствующий в списке.");
        }
    }

    @Override
    public void removeSubTaskByID(Integer id) {

        if (subTasks.containsKey(id)) {
            int colID = subTasks.get(id).getColID();
            Epic epic = epics.get(colID);
            epic.removeSubID(id);

            changeEpicStatus(epic);
            subTasks.remove(id);
        } else {
            System.out.println("Вы хотите удалить подзадачу, отсутствующую в списке");
        }
    }

    // Получение списка всех подзадач определённого эпика
    @Override
    public ArrayList<SubTask> getSubsFromEpic(Integer id) {
        ArrayList<SubTask> subsFromEpic = new ArrayList<>();
        Epic epic = epics.get(id);
        if(epic != null) {
            for (Integer subID : epic.getSubIDs()) {
                subsFromEpic.add(subTasks.get(subID));
            }
        }
        return subsFromEpic;
    }
}
