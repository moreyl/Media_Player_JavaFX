package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.Amedia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import javax.naming.Binding;
import javax.script.Bindings;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class DXMLDocumentController implements Initializable {

    private MediaPlayer mediaPlayer;

    @FXML
    private Slider slider;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider seekSlider;

    private String filePath;


    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media (filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            width.bind(javafx.beans.binding.Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(javafx.beans.binding.Bindings.selectDouble(mediaView.sceneProperty(),"height"));

            slider.setValue(mediaPlayer.getVolume()* 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100);


                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
            });

            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });

            mediaPlayer.play();



        }


    }

    public void playVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    public void pauseVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void stopVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.stop();
    }

    public void fasterVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.setRate(2);
    }

    public void fastVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.setRate(1.5);
    }

    public void slowVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.setRate(.75);
    }

    public void slowerVideo(javafx.event.ActionEvent actionEvent) {
        mediaPlayer.setRate(.5);
    }

    public void exitVideo(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
