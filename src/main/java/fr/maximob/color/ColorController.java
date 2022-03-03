package fr.maximob.color;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import fr.maximob.color.model.Color;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ColorController implements Initializable {

    private Color color;

    // CONTROLS
    // sliders
    @FXML private Slider colorRedSlider;
    @FXML private Slider colorGreenSlider;
    @FXML private Slider colorBlueSlider;
    // text fields
    @FXML private TextField colorRedField;
    @FXML private TextField colorGreenField;
    @FXML private TextField colorBlueField;
    @FXML private TextField hexValueField;

    // result pane displaying color
    @FXML private Pane resultPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // declare color object
        this.color = new Color("#5BC392");

        initializeRGBControl(colorRedSlider, colorRedField);
        initializeRGBControl(colorGreenSlider, colorGreenField);
        initializeRGBControl(colorBlueSlider, colorBlueField);
        this.setHexValueFieldEvents(this.hexValueField);
    }

    private void initializeRGBControl(Slider slider, TextField textField) {
        slider.setMin(0);
        slider.setMax(255);
        this.setSliderValue(slider, Double.parseDouble(textField.getText()));
        //set control events
        this.setSliderEvents(slider, textField);
        this.setTextFieldEvents(textField, slider);
    }

    private void setSliderValue(Slider slider, double val) {
        if (val >= slider.getMin() && val <= slider.getMax()) slider.valueProperty().setValue(val);
    }

    private void setTextFieldValue(TextField textField, String val) {
        if (Integer.parseInt(val) >= 0 && Integer.parseInt(val) <= 255) textField.textProperty().setValue(val);
    }

    private void setHexValueField(String hexValue) {
        if (this.color.isValidHexValue(hexValue)) this.hexValueField.textProperty().setValue(hexValue);
    }

    private void updateColor(int r, int g, int b) {
        this.color.setRed(r);
        this.color.setGreen(g);
        this.color.setBlue(b);
    }

    private void updateColor(String hexValue) throws IllegalArgumentException {
        if (! this.color.isValidHexValue(hexValue)) throw new IllegalArgumentException();
        this.color.setHexValue(hexValue);
    }

    private void setSliderEvents(Slider slider, TextField textField) {
        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            this.setTextFieldValue(textField, new DecimalFormat("##").format(newValue));
            this.updateColor(
                    Integer.parseInt(this.colorRedField.getText()),
                    Integer.parseInt(this.colorGreenField.getText()),
                    Integer.parseInt(this.colorBlueField.getText())
            );
            this.setHexValueField(this.color.getHexValue());
            this.resultPane.setStyle("-fx-background-color: " + this.color.getHexValue());
        });
    }

    private void setTextFieldEvents(TextField textField, Slider slider) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (Exception e) {
                newValue = "0";
            }
            if (Integer.parseInt(newValue) > slider.getMax()) newValue = new DecimalFormat("##").format(slider.getMax());
            this.setSliderValue(slider, Double.parseDouble(newValue));
            this.setTextFieldValue(textField, newValue);
            this.updateColor(
                    Integer.parseInt(this.colorRedField.getText()),
                    Integer.parseInt(this.colorGreenField.getText()),
                    Integer.parseInt(this.colorBlueField.getText())
            );
            this.setHexValueField(this.color.getHexValue());
            this.resultPane.setStyle("-fx-background-color: " + this.color.getHexValue());
        });
    }

    private void setHexValueFieldEvents(TextField hexValueField) {
        hexValueField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            newValue = newValue.toUpperCase(Locale.ROOT);
            if (this.color.isValidHexValue(newValue)) {
                // todo => update slider/textField controllers
                this.updateColor(newValue);
                this.resultPane.setStyle("-fx-background-color: " + this.color.getHexValue());
            } else if (newValue.length() == 7) {
                hexValueField.textProperty().setValue(oldValue);
            }
        });
    }
}