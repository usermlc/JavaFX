package com.await;

import com.await.persistance.User;
import com.await.repository.impl.UserRepositoryImplSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MainController {

    public Button generateDataBtn;
    public TextField countOfDataTxtf;
    UserRepositoryImplSingleton userRepo = UserRepositoryImplSingleton.getInstance();
    @FXML
    public TextField searchTxtF;
    @FXML
    public GridPane userTable;
    @FXML
    private Button searchBtn;
    @FXML
    private ComboBox<String> cmbox;

    @FXML
    private void generateDataBtnClick(ActionEvent event) {
        userRepo.generateData(Integer.parseInt(countOfDataTxtf.getText()));
    }
    @FXML
    private void fillUserTableSearchBtnClick(ActionEvent event) {

        var userSet = userRepo.findAll();
        int row = 1;

        for (User user : userSet) {

            Text[] userAttributes = {
                new Text(String.valueOf(row)),
                new Text(user.getFirstName()),
                new Text(user.getLastName()),
                new Text(user.getEmail()),
                new Text(user.getPhoneNumber()),
                new Text(user.getDateOfBirth().toString()),
                new Text(user.getAddress()),
                new Text(user.getUsername())
            };


            for (int j = 0; j < userAttributes.length; j++) {
                userTable.add(userAttributes[j], j, row);
            }

            row++;
        }
    }
    @FXML
    private void initialize() {
        cmbox.getItems().addAll("findall", "add", "remove", "firstname", "lastname", "email", "phonenum", "date", "address", "username");
        initializeUserTable();
    }

    private void initializeUserTable() {
        String[] tableData = {"id", "firstname", "lastname", "email", "phonenum", "date", "address", "username"};
        for (int i = 0; i < tableData.length; i++) {
            Text text = new Text(tableData[i]);
            userTable.add(text, i, 0);
            GridPane.setHalignment(text, HPos.CENTER);
            GridPane.setValignment(text, VPos.CENTER);
        }
    }

}
