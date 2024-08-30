package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Task;

import java.sql.*;
import java.time.LocalDate;

public class TaskController implements TaskService{

    @Override
    public boolean addTask(Task task) {
        String SQL = "INSERT INTO Task (task_id, title, description) VALUES(?,?,?);";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, task.getTaskId());
            psTm.setObject(2, task.getTitle());
            psTm.setObject(3, task.getDescription());

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateTaskId() {
        try {
            String SQL = "SELECT COUNT(*) FROM Task";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            resultSet.next();
            return String.format("T%03d", (resultSet.getInt(1)+1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Task> getAllIncompletedTasks() {
        String SQL = "SELECT * FROM Task WHERE completed_date IS NULL";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
            while (resultSet.next()){
                Task task = new Task(
                        resultSet.getString("task_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        null
                );

                taskObservableList.add(task);
            }
            return taskObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Task> getAllCompletedTasks() {
        String SQL = "SELECT * FROM Task WHERE completed_date IS NOT NULL";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
            while (resultSet.next()){
                Task task = new Task(
                        resultSet.getString("task_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("completed_date").toLocalDate()
                );

                taskObservableList.add(task);
            }
            return taskObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean setTaskCompleted(String id) {
        String SQL = "UPDATE Task SET completed_date = ? WHERE task_id = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setDate(1, Date.valueOf(LocalDate.now()));
            psTm.setObject(2, id);
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
