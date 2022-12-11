package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import classes.Komponent;

import java.awt.event.MouseEvent;
import java.util.Objects;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label rate;

    private Komponent komponent;
    private MyListener myListener;

    public void setData(Komponent komponent, MyListener myListener){
        this.komponent = komponent;
        this.myListener = myListener;
        name.setText(komponent.getNazwa());
        price.setText(komponent.getCena() + "zł");
        rate.setText(Double.toString(komponent.getOceny()));
        Image image = new Image(getClass().getResourceAsStream(komponent.getImage()));
        img.setImage(image);

    }

    public void click(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(komponent);
    }
}
