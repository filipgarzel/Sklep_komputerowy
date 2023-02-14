package gui;

import database.DataConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import classes.Komponent;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class ItemController {

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label rate;

    private Komponent komponent;
    private MyListener myListener;
    private DataConnection dataConnection;

    {
        try {
            dataConnection = new DataConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setData(Komponent komponent, MyListener myListener)  {
        this.komponent = komponent;
        this.myListener = myListener;
        name.setText(komponent.getNazwa());
        try {
            if (dataConnection.getIlosc(komponent.getNazwa())!=0){
                price.setText(komponent.getCena() + "zł");
            }
            else {
                price.setText("niedostępny");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rate.setText(komponent.getOceny());

    }

    public void click(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(komponent);
    }
}
