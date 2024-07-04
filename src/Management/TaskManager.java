package Management;

import java.util.ArrayList;
import java.util.HashMap;
import Tasks.*;

public class TaskManager {

    private int nextID = 1;

    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();


    // Группа методов по добавлению задач
    public void addSimpleTask(SimpleTask simpleTask) {
        simpleTask.setId(nextID);
        nextID++;
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void addEpic(Epic epic) {
        epic.setId(nextID);
        nextID++;
        epics.put(epic.getId(), epic);
    }

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
    private void changeEpicStatus (Epic epic) {

        ArrayList<Status> statuses = new ArrayList<>();
        for (Integer checkID : epic.getSubIDs()) {
            statuses.add(subTasks.get(checkID).getTaskStatus());
        }
        if(statuses.contains(Status.NEW) && (!statuses.contains(Status.IN_PROGRESS))
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
    public void updateSimpleTask(SimpleTask simpleTask) {
        if(simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
        } else {
            System.out.println("Вы пытаетесь обновить задачу, отсутствующую в списке.");
        }

    }

    public void updateEpic(Epic epic) {

        if(epics.containsKey(epic.getId())) {
            epic.setTitle(epics.get(epic.getId()).getTitle());
            epic.setDescription(epics.get(epic.getId()).getDescription());
        } else {
            System.out.println("Вы пытаетесь обновить Эпик, отсутствующий в списке.");
        }
    }

    public void updateSubTask(SubTask subTask) {

        if (!subTasks.containsKey(subTask.getId())) {
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
            for (Integer subID : epics.get(id).getSubIDs()) {
                subTasks.remove(subID);
            }
            epics.remove(id);
        } else {
            System.out.println("Вы пытаетесь удалить Эпик, отсутствующий в списке.");
        }
    }

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
