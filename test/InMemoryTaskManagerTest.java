import management.HistoryManager;
import management.InMemoryHistoryManager;
import management.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    // группа тестов:
    // проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    public void testAddAndGetSimpleTaskByID() {
        SimpleTask simpleTask = new SimpleTask("Simple Task 1", "Description of Simple Task 1", Status.NEW);
        taskManager.addSimpleTask(simpleTask);

        SimpleTask retrievedTask = taskManager.getSimpleTaskByID(simpleTask.getId());

        assertNotNull(retrievedTask);
        assertEquals("Simple Task 1", retrievedTask.getTitle());
        assertEquals("Description of Simple Task 1", retrievedTask.getDescription());
    }

    @Test
    public void testAddAndGetEpicByID() {
        Epic epic = new Epic("Epic 1", "Description of Epic 1");
        taskManager.addEpic(epic);

        Epic retrievedEpic = taskManager.getEpicByID(epic.getId());

        assertNotNull(retrievedEpic);
        assertEquals("Epic 1", retrievedEpic.getTitle());
        assertEquals("Description of Epic 1", retrievedEpic.getDescription());
    }

    @Test
    public void testAddAndGetSubTaskByID() {
        Epic epic = new Epic("Epic for SubTask", "Description of Epic for SubTask");
        taskManager.addEpic(epic);

        SubTask subTask = new SubTask("SubTask 1", "Description of SubTask 1", Status.NEW, epic.getId());
        taskManager.addSubTask(subTask);

        SubTask retrievedSubTask = taskManager.getSubTaskByID(subTask.getId());

        assertNotNull(retrievedSubTask);
        assertEquals("SubTask 1", retrievedSubTask.getTitle());
        assertEquals("Description of SubTask 1", retrievedSubTask.getDescription());
        assertEquals(epic.getId(), retrievedSubTask.getColID());
    }

    // группа тестов: проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
    @Test
    void testSimpleTaskIdConflict() {
        SimpleTask task1 = new SimpleTask("Task 1", "Description 1", Status.NEW);
        SimpleTask task2 = new SimpleTask("Task 2", "Description 2", Status.IN_PROGRESS);

        taskManager.addSimpleTask(task1);
        taskManager.addSimpleTask(task2);

        int id1 = task1.getId();
        int id2 = task2.getId();

        Assertions.assertNotEquals(id1, id2, "IDs of SimpleTasks should not be equal");
    }

    @Test
    void testEpicIdConflict() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        int id1 = epic1.getId();
        int id2 = epic2.getId();

        Assertions.assertNotEquals(id1, id2, "IDs of Epics should not be equal");
    }

    @Test
    void testSubTaskIdConflict() {
        Epic epic = new Epic("Parent Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask("SubTask 1", "Description 1", Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("SubTask 2", "Description 2", Status.IN_PROGRESS, epic.getId());

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        int id1 = subTask1.getId();
        int id2 = subTask2.getId();

        Assertions.assertNotEquals(id1, id2, "IDs of SubTasks should not be equal");
    }

    // группа тестов:
    // создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    // я не понял это условие, правда
    @Test
    void testSimpleTaskUnchanged() {
        // Создаем задачу
        SimpleTask originalTask = new SimpleTask("Task 1", "Description 1", Status.NEW);

        // Добавляем задачу в менеджер
        taskManager.addSimpleTask(originalTask);

        // Получаем задачу обратно из менеджера по её id
        SimpleTask newTask = taskManager.getSimpleTaskByID(originalTask.getId());

        // Сравниваем исходную задачу и полученную задачу
        Assertions.assertEquals(originalTask, newTask, "Задача изменилась");
    }

    @Test
    void testEpicUnchanged() {
        // Создаем исходный эпик
        Epic originalEpic = new Epic("Epic 1", "Description 1");

        // Добавляем эпик в менеджер
        taskManager.addEpic(originalEpic);

        // Получаем эпик обратно из менеджера по его id
        Epic newEpic = taskManager.getEpicByID(originalEpic.getId());

        // Сравниваем исходный эпик и полученный эпик
        Assertions.assertEquals(originalEpic, newEpic, "Задача изменилась");
    }

    @Test
    void testSubTaskUnchanged() {
        // Создаем исходную подзадачу
        Epic epic = new Epic("Parent Epic", "Description");
        taskManager.addEpic(epic);
        SubTask originalSubTask = new SubTask("SubTask 1", "Description 1", Status.NEW, epic.getId());

        // Добавляем подзадачу в менеджер
        taskManager.addSubTask(originalSubTask);

        // Получаем подзадачу обратно из менеджера по её id
        SubTask newSubTask = taskManager.getSubTaskByID(originalSubTask.getId());

        // Сравниваем исходную подзадачу и полученную подзадачу
        Assertions.assertEquals(originalSubTask, newSubTask, "Задача изменилась");
    }

    // Тест: убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    // Тут я тоже не понял до конца, что от меня требуется
    @Test
    public void testAddAndGetHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        // Создаем задачу и добавляем ее в историю
        Task originalTask = new Task("Task 1", "Description 1", Status.NEW);
        historyManager.add(originalTask);

        // Изменяем задачу
        originalTask.setDescription("Updated description");

        // Добавляем изменённую задачу в историю
        historyManager.add(originalTask);

        // Получаем историю задач
        ArrayList<Task> history = historyManager.getHistory();

        // Проверяем, что история содержит две версии задачи
        assertEquals(2, history.size());

        // Проверяем, что первая версия задачи соответствует первоначальным данным
        Task version1 = history.get(0);
        assertEquals("Task 1", version1.getTitle());
        assertEquals("Description 1", version1.getDescription());

        // Проверяем, что вторая версия задачи соответствует измененным данным
        Task version2 = history.get(1);
        assertEquals("Task 1", version2.getTitle());
        assertEquals("Updated description", version2.getDescription());
    }
}
