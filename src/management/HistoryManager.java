package management;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    // спасибо за замечание. По правде говоря, теории по List у нас не было, поэтому использовал ArrayList
    List<Task> getHistory();
}
