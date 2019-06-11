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

    private GameParamaters myGameParamaters;

    private MinePanel minePanel;
    private MineBlockData mineBlockData;

    public MineControl(BasicFrame basicFrame) {
        myGameParamaters = GameParamaters.getGameProperties();

        minePanel = basicFrame.getMinePanel();
        minePanel.addMouseListener(new mineMouseListener());
        mineBlockData = new MineBlockData();

        new Thread(() -> {
           runThread();
        }).start();
    }

    private void runThread() {
        clickEvent(0, -1, -1);
    }

    private void clickEvent(int clickStatus, int x, int y) {
        // 左右键同时点击
        if (clickStatus == 1) {
        // 左键单击
        } else if (clickStatus == 2) {
            mineBlockData.setOpened(x, y);
        // 右键单击
        } else if (clickStatus == 3) {
            mineBlockData.setHasFlag(x, y);
        }
        minePanel.render(mineBlockData);
    }

    private class mineMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point position = event.getPoint();

            int x = position.y / myGameParamaters.BLOCK_HEIGHT;
            int y = position.x / myGameParamaters.BLOCK_WIDTH;

            // 双击:事件1
            if (event.getButton() == MouseEvent.BUTTON1 && event.getButton() == MouseEvent.BUTTON3) {

            // 左键单击:事件2
            } else if (event.getButton() == MouseEvent.BUTTON1) {
                clickEvent(2, x, y);
            // 右键单击:事件3
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                clickEvent(3, x, y);
            }
        }
    }

}
