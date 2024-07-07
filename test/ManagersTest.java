import management.InMemoryTaskManager;
import management.Managers;
import management.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagersTest {

    // убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void testGetInitializedManager() { // работает
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        assertNotNull(manager);
        assertTrue(manager instanceof InMemoryTaskManager);
    }
}
