package management;

public class Managers {

    // ТЗ написано кривовато, перечитал раз 20-ть, но всё-равно мог напутать, чего от меня хотят

    /*
    "У Managers будет метод getDefault. При этом вызывающему неизвестен конкретный класс —
    только то, что объект,который возвращает getDefault, реализует интерфейс TaskManager.
    Метод getDefault будет без параметров. Он должен возвращать объект-менеджер,
    поэтому типом его возвращаемого значения будет TaskManager."
    */
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /*
    "Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory.
    Он должен возвращать объект InMemoryHistoryManager — историю просмотров."
    */
    // Не понимаю, почему InMemoryHistoryManager() - это история просмотров.
    // Это класс, из которого можно достать историю просмотров

    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
