package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Task;

public class AddNewTaskFormController {
    public JFXTextField txtTtle;
    public JFXTextArea txtDescription;
    TaskService taskService = new TaskController();


    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        String title = txtTtle.getText();

        if(title.isBlank()){
            new Alert(Alert.AlertType.ERROR, "Title cannot be empty!").show();
            return;
        }

        boolean isTaskAdded = taskService.addTask(new Task(taskService.generateTaskId(), title, txtDescription.getText(), null));

        if(isTaskAdded){
            new Alert(Alert.AlertType.INFORMATION, "Task Added!").show();
        }
    }
}
