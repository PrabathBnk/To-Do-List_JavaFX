package controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWindowFormController implements Initializable {

    public Label lblDate;
    private TaskService taskService = new TaskController();

    public ListView listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(LocalDate.now().toString());
        loadTaskList();
    }

    public void addNewTaskOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/add_new_task.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    private void loadTaskList(){
        ObservableList<Task> taskList = taskService.getAllIncompletedTasks();

        ObservableList<JFXCheckBox> list = FXCollections.observableArrayList();
        taskList.forEach(task -> {
            JFXCheckBox checkBox = new JFXCheckBox(task.getTitle() + " | " + task.getDescription());
            checkBox.setFont(new Font("Arial", 13));
            checkBox.setCheckedColor(new Color(0.26, 0.55, 0.99, 1));
            checkBox.setId(task.getTaskId());
            list.add(checkBox);
        });

        listView.setItems(list);
    }

    public void btnEditTaskOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteTaskOnAction(ActionEvent actionEvent) {

    }

    public void btnCompleteTaskTableOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/complete_task_table_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
        ObservableList taskList = listView.getItems();

        for (int i = 0; i < taskList.size(); i++) {
            JFXCheckBox task = (JFXCheckBox) taskList.get(i);
            if(task.isSelected()){
                taskService.setTaskCompleted(task.getId());
            }
        }

        loadTaskList();
    }
}
