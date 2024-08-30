package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class CompleteTaskTableFormController implements Initializable {

    public TableView tblCompletedTasks;
    private TaskService taskService = new TaskController();

    @FXML
    private TableColumn<?, ?> colCompletedDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCompletedDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));

        ObservableList<Task> taskObservableList = taskService.getAllCompletedTasks();
        tblCompletedTasks.setItems(taskObservableList);
    }
}
