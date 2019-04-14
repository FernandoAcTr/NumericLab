package nolinearsystems.controller;

import com.jfoenix.controls.JFXTabPane;
import ecuationsolutions.model.Function;
import ecuationsolutions.model.GraphicData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nolinearsystems.model.FileFunction;
import nolinearsystems.model.NoLinearSolver;
import utils.MyUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private MenuItem mnuNew;

    @FXML
    private MenuItem mnuOpen;

    @FXML
    private MenuItem mnuSave;

    @FXML
    private MenuItem mnuSaveAs;

    @FXML
    private MenuItem mnuClose;

    @FXML
    private MenuItem mnuHowGraphic;

    @FXML
    private MenuItem mnuHowResolv;

    @FXML
    private MenuItem mnuAbout;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private TextField txtFunction;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtTo;

    @FXML
    private Button btnShowGraphic;

    @FXML
    private TextField txtFunction2;

    @FXML
    private TextField txtFrom2;

    @FXML
    private TextField txtTo2;

    @FXML
    private Button btnShowGraphic2;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField txtF1;

    @FXML
    private TextField txtF1x;

    @FXML
    private TextField txtF1y;

    @FXML
    private TextField txtF2;

    @FXML
    private TextField txtF2x;

    @FXML
    private TextField txtF2y;

    @FXML
    private TextField txtX;

    @FXML
    private TextField txtY;

    @FXML
    private TextField txtEP;

    @FXML
    private TextArea txtAreaProcedure;

    @FXML
    private Button btnResolve;

    private NoLinearSolver solver;
    FileFunction fileFunction;
    FileChooser fileChooser;

    public void initialize(URL location, ResourceBundle resources) {
        initData();
        initComponents();
    }

    private void initData() {
        fileFunction = new FileFunction();
        fileChooser = new FileChooser();
        fileChooser.setInitialFileName("*.func");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Function File", "*.func"));
    }

    private void initComponents() {

        btnShowGraphic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                double from, to;
                String function;
                try {
                    from = Double.parseDouble(txtFrom.getText());
                    to = Double.parseDouble(txtTo.getText());
                    function = txtFunction.getText();
                    showGraphic(function, from, to, true);
                } catch (NumberFormatException ex) {
                    MyUtils.showMessage("Ingresa correctamente el intervalo", "Error", null, Alert.AlertType.ERROR);
                }
            }
        });

        btnShowGraphic2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                double from, to;
                String function;
                try {
                    from = Double.parseDouble(txtFrom2.getText());
                    to = Double.parseDouble(txtTo2.getText());
                    function = txtFunction2.getText();
                    showGraphic(function, from, to, false);
                } catch (NumberFormatException ex) {
                    MyUtils.showMessage("Ingresa correctamente el intervalo", "Error", null, Alert.AlertType.ERROR);
                }
            }
        });

        mnuClose.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/common_res/layout_principal.fxml"));
                    Scene scene = new Scene(root, 730, 600);
                    scene.getStylesheets().add("/org/kordamp/bootstrapfx/bootstrapfx.css");
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    ((Stage) btnShowGraphic.getParent().getScene().getWindow()).close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/common_res/layout_about.fxml"));
                    Scene scene = new Scene(root, 420, 360);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mnuHowGraphic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String help = "Posicionados en la pestaña gáfica:" +
                        "\nUsted debe de ingresar las funciones que desea graficar y el intervalo numérico en el cuál se graficará cada una"
                        + "\nposteriormente debe dar click en el botón graficar." +
                        "\nEs usual que ambas funciones se deseen graficar en el mismo intervalo, por tanto el segundo intervalo." +
                        "\nse replica a medida que usted escribe el primero, pero puede modificarse libremente. " +
                        "\nNo es necesario graficar ambas funciones, cada función funge de manera independiente." +
                        "\nLas funciones deben estar en forma explícita, no se soporta la graficación implícita.";
                String information = "Para las funcioes se aceptan los siguientes simbolos: "
                        + "\n Potencias: ^"
                        + "\n Seno: sin(), sinh()"
                        + "\n Cosen: cos(), cosh()"
                        + "\n Tangente: tan()"
                        + "\n ArcTangente: atan()"
                        + "\n ArcSeno: asin()"
                        + "\n ArcCoseno: acos()"
                        + "\n Absoluto: abs()"
                        + "\n logaritmo base N: logn(x)"
                        + "\n logaritmo natural: log(x)"
                        + "\n Raiz: sqrt()"
                        + "\n Los signos de agrupacion aceptados son: (), {}, []"
                        + "\n Además se soporta la multiplicación implícita";
                MyUtils.showHelpMessage(help + "\n" + information, 700, 450);
            }
        });

        mnuHowResolv.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String help = "Posicionado en la pestaña Métodos de Solución:"
                        + "\nUsted debe ingresar los datos en todas las cajas de texto que aparecen en el panel. " +
                        "\nLos datos que se requieren son: F1(x,y), F2(x,y) y para cada una sus respectivas " +
                        "\nderivadas parciales respecto de x e y. Así como el punto inicial x, el punto inicial" +
                        "\ny, y el error permitido";
                MyUtils.showHelpMessage(help, 600, 250);
            }
        });

        mnuSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                mnuSaveAction((Stage) btnShowGraphic.getScene().getWindow(), false);
            }
        });

        mnuSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                mnuSaveAction((Stage) btnShowGraphic.getScene().getWindow(), true);
            }
        });

        mnuOpen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                mnuOpenAction((Stage) btnShowGraphic.getScene().getWindow());
            }
        });

        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                cleanAll();
            }
        });

        txtFrom.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                txtFrom2.setText(txtFrom.getText());
            }
        });

        txtTo.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                txtTo2.setText(txtTo.getText());
            }
        });

        btnResolve.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String f1 = txtF1.getText();
                String f2 = txtF2.getText();
                String f1x = txtF1x.getText();
                String f1y = txtF1y.getText();
                String f2x = txtF2x.getText();
                String f2y = txtF2y.getText();
                DecimalFormat format = new DecimalFormat("##.000000");
                try {
                    double x = Double.parseDouble(txtX.getText());
                    double y = Double.parseDouble(txtY.getText());
                    double error = Double.parseDouble(txtEP.getText());

                    if (verifyData(f1, f2, f1x, f1y, f2x, f2y)) {
                        solver = new NoLinearSolver(f1, f2, f1x, f1y, f2x, f2y, error);
                        double[] results = solver.resolvByNewton_Raphson_Multivariable(x, y);
                        txtAreaProcedure.setText(solver.getProcedure());
                        txtAreaProcedure.appendText("\n\nX = "+format.format(results[0]));
                        txtAreaProcedure.appendText("\nY = "+format.format(results[1]));
                    } else {
                        MyUtils.showMessage("No debes dejar datos en blanco.",
                                "Error", null, Alert.AlertType.ERROR);
                    }

                } catch (NumberFormatException ex) {
                    MyUtils.showMessage("Hubo un error al ingresar datos. Por favor asegurate que estan correctos",
                            "Error", null, Alert.AlertType.ERROR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean verifyData(String f1, String f2, String f1x, String f1y, String f2x, String f2y) {
        return f1.length() != 0 && f2.length() != 0 && f1x.length() != 0 && f1y.length() != 0 && f2x.length() != 0 && f2y.length() != 0;
    }

    /**
     * Dibuja una linea de funcion en la grafica
     *
     * @param from        punto donde comienza la grafica
     * @param to          punto donde termina la grafica
     * @param add         si la linea es agregada como una nueva grafica o borra las anteriores. true si debe borrarse, false si se agrega
     * @param defFunction la definicion de la funcion como String
     */
    private void showGraphic(String defFunction, double from, double to, boolean add) {
        Function function = new Function(defFunction);
        double increment;

        increment = Math.abs(from - to) / 800;

        try {
            double xValues[] = function.generateRange(from, to, increment);
            double yValues[] = function.evaluateFrom(xValues);
            GraphicData graphicData = new GraphicData();
            XYChart.Series serie = graphicData.getSerie(defFunction, xValues, yValues);

            if (add)
                lineChart.getData().clear();

            lineChart.getData().add(serie);

            final DecimalFormat formatter = new DecimalFormat("#.###");

            for (int i = 0; i < lineChart.getData().size(); i++)
                for (final XYChart.Data data : lineChart.getData().get(i).getData())
                    Tooltip.install(data.getNode(), new Tooltip("X: " + formatter.format(data.getXValue()) + " Y: " + formatter.format(data.getYValue())));

        } catch (Exception ex) {
            MyUtils.showMessage("",
                    "Error", "Por favor revisa la ayuda acerca de como ingresar la funcion", Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Guarda una funcion
     *
     * @param stage
     * @param typeSave True para Guardar. False para Guardar como...
     */
    private void mnuSaveAction(Stage stage, boolean typeSave) {
        boolean save = false;
        byte typeMethod = FileFunction.BeanFunction.NEWTON_RAPHSON_MULTIVARIABLE;
        FileFunction.BeanFunction beanFunction = null;

        String f1 = txtF1.getText().trim();
        String f2 = txtF2.getText().trim();
        String f1x = txtF1x.getText().trim();
        String f1y = txtF1y.getText().trim();
        String f2x = txtF2x.getText().trim();
        String f2y = txtF2y.getText().trim();
        String x = txtX.getText().trim();
        String y = txtY.getText().trim();
        String errorPermited = txtEP.getText().trim();
        String graphicFun1 = txtFunction.getText().trim();
        String graphicFun2 = txtFunction2.getText().trim();
        String from1 = txtFrom.getText().trim();
        String from2 = txtFrom2.getText().trim();
        String to1 = txtTo.getText().trim();
        String to2 = txtTo2.getText().trim();

        beanFunction = new FileFunction.BeanFunction(typeMethod, f1, f2, f1x, f1y, f2x, f2y, x, y, errorPermited);
        beanFunction.setGraphicFunction1(graphicFun1, from1, to1);
        beanFunction.setGraphicFunction2(graphicFun2, from2, to2);

        if (fileFunction.getFunctionFile() != null && typeSave) {
            fileFunction.openFile(fileFunction.getFunctionFile());
            save = true;
        } else {
            fileChooser.setTitle("Save As...");
            File file = refactorFileName(fileChooser.showSaveDialog(stage));
            if (file != null) {
                fileFunction.openFile(file);
                save = true;
            }
        }

        if (save) {
            fileFunction.saveFunction(beanFunction);
            fileFunction.closeFile();
            ((Stage) txtFunction.getParent().getScene().getWindow()).setTitle(fileFunction.getFunctionFile().getName());
        }
    }

    private File refactorFileName(File file) {
        File refactorFile = file;
        if (file != null)
            if (!file.getName().endsWith(".func"))
                refactorFile = new File(file.getPath() + ".func");

        return refactorFile;
    }

    private void mnuOpenAction(Stage stage) {
        fileChooser.setTitle("Open");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileFunction.openFile(file);
            FileFunction.BeanFunction bean = fileFunction.readFunction();
            byte type = bean.getTypeMethod();


            if (type != FileFunction.BeanFunction.NEWTON_RAPHSON_MULTIVARIABLE) {
                MyUtils.showMessage("Estas en Ecuaciones No lineales e intentas abrir otro tipo de archivo ",
                        "Error al abrir el archivo", null, Alert.AlertType.WARNING);
                return;
            }

            txtFunction.setText(bean.getGraphicFunction1());
            txtFunction2.setText(bean.getGraphicFunction2());
            txtFrom.setText(bean.getFrom1());
            txtTo.setText(bean.getTo1());
            txtFrom2.setText(bean.getFrom2());
            txtTo2.setText(bean.getTo2());
            txtF1.setText(bean.getF1());
            txtF2.setText(bean.getF2());
            txtF1x.setText(bean.getF1x());
            txtF1y.setText(bean.getF1y());
            txtF2x.setText(bean.getF2x());
            txtF2y.setText(bean.getF2y());
            txtX.setText(bean.getX());
            txtY.setText(bean.getY());
            txtEP.setText(bean.getErrorPermited());

            fileFunction.closeFile();
            ((Stage) txtFunction.getParent().getScene().getWindow()).setTitle(file.getName());
        }
    }

    private void cleanAll(){
        txtFunction.clear();
        txtFunction2.clear();
        txtFrom.clear();
        txtFrom2.clear();
        txtTo.clear();
        txtTo2.clear();
        txtF1.clear();
        txtF2.clear();
        txtF1x.clear();
        txtF1y.clear();
        txtF2x.clear();
        txtF2y.clear();
        txtX.clear();
        txtY.clear();
        txtEP.clear();
        lineChart.getData().clear();
        txtF1.requestFocus();
        tabPane.getSelectionModel().select(1);
        ((Stage) txtFunction.getParent().getScene().getWindow()).setTitle("New Document");
    }

}
