package control;

import model.MineBlockData;
import tool.GameParamaters;
import view.BasicFrame;
import view.MinePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 控制器--雷区面板
 */

public class MineControl {
    private static int DELAY = 5;
    private static int LEFT_RIGHT_MOUSE_EVENT = 1;
    private static int LEFT_MOUSE_EVENT = 2;
    private static int RIGHT_MOUSE_EVENT = 3;

    private GameParamaters myGameParamaters;

    private MinePanel minePanel;
    private MineBlockData mineBlockData;

    public MineControl(BasicFrame basicFrame) {
        myGameParamaters = GameParamaters.getGameProperties();

        minePanel = basicFrame.getMinePanel();
        minePanel.addMouseListener(new MineMouseListener());
        mineBlockData = new MineBlockData();

        new Thread(this::runThread).start();
    }

    private void runThread() {
        clickEvent(0, -1, -1);
    }

    private void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    private void clickEvent(int clickStatus, int x, int y) {
        // 左右键同时点击
        if (clickStatus == LEFT_RIGHT_MOUSE_EVENT) {
        // 左键单击
        } else if (clickStatus == LEFT_MOUSE_EVENT) {
            mineBlockData.setOpened(x, y);
        // 右键单击
        } else if (clickStatus == RIGHT_MOUSE_EVENT) {
            mineBlockData.setHasFlag(x, y);
        }
        pause(DELAY);
        minePanel.render(mineBlockData);
    }

    private class MineMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point position = event.getPoint();

            int x = position.y / myGameParamaters.getBlockHeight();
            int y = position.x / myGameParamaters.getBlockWidth();

            // 左键单击
            if (event.getButton() == MouseEvent.BUTTON1) {
                clickEvent(LEFT_MOUSE_EVENT, x, y);
            // 右键单击
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                clickEvent(RIGHT_MOUSE_EVENT, x, y);
            }
        }

        @Override
        public void mousePressed(MouseEvent event) {
            Point position = event.getPoint();

            int x = position.y / myGameParamaters.getBlockHeight();
            int y = position.x / myGameParamaters.getBlockWidth();

            // 左右键长按
            if (event.getButton() == MouseEvent.BUTTON1 && event.getButton() == MouseEvent.BUTTON3) {
                clickEvent(LEFT_RIGHT_MOUSE_EVENT, x, y);
            }
        }
    }

}
