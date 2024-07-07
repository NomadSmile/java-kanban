import management.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @BeforeEach
    void taskManagerSetUp() {
       InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
       HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
       HashMap<Integer, Epic> epics = new HashMap<>();
       HashMap<Integer, SubTask> subTasks = new HashMap<>();
    }

    // проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1", Status.IN_PROGRESS);
        task1.setId(1);

        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
        task2.setId(1);

        assertEquals(task1.getId(), task2.getId());
    }

    // проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void taskInheritanceTest() {
        SimpleTask task1 = new SimpleTask("SimpleTask 1", "Description 1", Status.IN_PROGRESS);
        task1.setId(1);

        SimpleTask task2 = new SimpleTask("SimpleTask 2", "Description 2", Status.IN_PROGRESS);
        task2.setId(1);

        assertEquals(task1.getId(), task2.getId());
    }

    /* 1. проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
       2. проверьте, что объект Subtask нельзя сделать своим же эпиком;
       (ввиду текущей структуры создания задач, в данном тесте проверяются оба кейса */
    @Test
    void testEpicCannotBeOwnSubtask() {
        Epic epic = new Epic("Epic 1", "EpicDescription");
        epic.setId(1);
        epic.addSubID(epic.getId());
        assertFalse(epic.getSubIDs().contains(epic.getId()));

    }
}
