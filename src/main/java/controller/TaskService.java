package controller;

import javafx.collections.ObservableList;
import model.Task;

public interface TaskService {
    boolean addTask(Task task);
    String generateTaskId();
    ObservableList<Task> getAllIncompletedTasks();
    ObservableList<Task> getAllCompletedTasks();
    boolean setTaskCompleted(String id);
}
