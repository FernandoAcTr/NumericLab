package interpolation.model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.MyUtils;

import java.util.List;

public class InterpolationPoint implements EventHandler<KeyEvent> {
    private TextField txtValueFor;
    private TextField txtInterpolation;
    private Button btnInterpolate;

    private final int LINEAR_INTERPOLATION = 0;
    private final int SQUARE_INTERPOLATION = 1;

    private int index, typeInterpolation;
    private float x, y;
    private List<XYPoint> listPoints;

    public InterpolationPoint(int index, TableView tableView, List<XYPoint> listPoints, int typeInterpolation) {
        txtValueFor = new TextField();
        txtValueFor.setOnKeyTyped(this);

        txtInterpolation = new TextField();
        txtInterpolation.setEditable(false);
        txtInterpolation.setFocusTraversable(false);
        btnInterpolate = new Button("Interpolar");
        btnInterpolate.setOnAction(event -> interpolate());

        this.index = index;
        this.typeInterpolation = typeInterpolation;
        this.listPoints = listPoints;
        initTextField(tableView);
    }

    private void initTextField(final TableView tableView) {

        txtValueFor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) //focused
                tableView.getSelectionModel().select(index);
            else {        //unfocused
                if (txtValueFor.getText().length() > 0)
                    x = Float.valueOf(txtValueFor.getText());

                tableView.refresh();
            }

        });

        txtValueFor.setOnKeyTyped(this);
        txtInterpolation.setOnKeyTyped(this);
    }

    public void interpolate(){
        if(txtValueFor.getText().length() == 0)
            txtValueFor.setStyle("-fx-border-color: red; -fx-background-color: rgba(255, 0, 0, 0.2)");
        else {
            Interpolator interpolator = new Interpolator();
            float v;
            switch (typeInterpolation) {
                case LINEAR_INTERPOLATION:
                    v = interpolator.getLinearInterpolation(listPoints.get(0), listPoints.get(1), Float.valueOf(txtValueFor.getText()));
                    txtInterpolation.setText(MyUtils.format(v));
                    break;
                case SQUARE_INTERPOLATION:
                    v = interpolator.getSquareInterpolation(listPoints.get(0), listPoints.get(1), listPoints.get(2), Float.valueOf(txtValueFor.getText()));
                    txtInterpolation.setText(MyUtils.format(v));
            }
        }
    }

    public void handle(KeyEvent event) {
        if (Character.isLetter(event.getCharacter().charAt(0)))
            event.consume();
    }

    // <editor-fold defaultstate="collapsed" desc=" getters() & setters() ">

    public TextField getTxtValueFor() {
        return txtValueFor;
    }

    public void setTxtValueFor(TextField txtValueFor) {
        this.txtValueFor = txtValueFor;
    }

    public TextField getTxtInterpolation() {
        return txtInterpolation;
    }

    public void setTxtInterpolation(TextField txtInterpolation) {
        this.txtInterpolation = txtInterpolation;
    }

    public Button getBtnInterpolate() {
        return btnInterpolate;
    }

    public void setBtnInterpolate(Button btnInterpolate) {
        this.btnInterpolate = btnInterpolate;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    // </editor-fold>
}