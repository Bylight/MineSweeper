import control.MineControl;
import view.BasicFrame;
import view.MinePanel;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 初始化总面板
        EventQueue.invokeLater(() -> {
            BasicFrame basicFrame = new BasicFrame();
            MineControl mineControl = new MineControl(basicFrame);
        });
    }
}
