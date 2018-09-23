package root;

import javax.swing.*;
import java.awt.*;

public class Main {


    public static void main(String[] args) {

        JFrame frame = Scene.setFrame(500, 500);
        Scene canvas = new Scene(500 ,500);

        Dimension d = new Dimension(500, 500);
        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.setMinimumSize(d);

        frame.add(canvas);
        frame.pack();

        canvas.start();
    }
}
