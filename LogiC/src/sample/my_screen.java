package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class my_screen {

    my_screen(){}

    /**
     * функція для створення скріну
     * @param node object для фотосесії
     * @param fname імя для скріна
     * @param ssp якийся 3-тій параметр
     */
    void saveAsPng(Node node, String fname, SnapshotParameters ssp) {
        WritableImage image = node.snapshot(ssp, null);
        File file = new File("src/sample/screen/" + fname+".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
            System.out.println("error!");
        }
    }
}