package control;

import model.MineBlockData;
import tool.GameParamaters;
import tool.GameTool;
import view.BasicFrame;
import view.MinePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 控制器--雷区面板
 */

public class MineControl {
    private static int LEFT_RIGHT_MOUSE_EVENT = 1;
    private static int LEFT_MOUSE_EVENT = 2;
    private static int RIGHT_MOUSE_EVENT = 3;

    private GameParamaters gameParamaters;
    private GameControl gameControl;

    private MinePanel minePanel;
    private MineBlockData mineBlockData;

    MineControl(BasicFrame basicFrame) {
        gameParamaters = GameParamaters.getGameProperties();
        gameControl = GameControl.getGameControl();

        minePanel = basicFrame.getMinePanel();
        minePanel.addMouseListener(new MineMouseListener());
        mineBlockData = new MineBlockData();

        minePanel.render(mineBlockData);
    }

    private void clickEvent(int clickStatus, int x, int y) {
        // 左右键同时点击
        if (clickStatus == LEFT_RIGHT_MOUSE_EVENT) {
            mineBlockData.previewBlockAround(x, y);
        // 左键单击
        } else if (clickStatus == LEFT_MOUSE_EVENT) {
            mineBlockData.setOpened(x, y);
        // 右键单击
        } else if (clickStatus == RIGHT_MOUSE_EVENT) {
            mineBlockData.setHasFlag(x, y);
        }

        minePanel.render(mineBlockData);

        // 判断是否输
        if (gameParamaters.getLoseGame()) {
            gameControl.loseGame();
        }

        // 判断是否赢
        mineBlockData.hasWin();
        if (gameParamaters.getWinGame()) {
            gameControl.winGame();
        }

    }

    private class MineMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            Point position = event.getPoint();

            int x = position.y / gameParamaters.getBlockHeight();
            int y = position.x / gameParamaters.getBlockWidth();

            // 左右键长按
            if (event.getModifiersEx() == (MouseEvent.BUTTON1_DOWN_MASK + MouseEvent.BUTTON3_DOWN_MASK)) {
                clickEvent(LEFT_RIGHT_MOUSE_EVENT, x, y);
            }
            // 左键单击
            else if (event.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                clickEvent(LEFT_MOUSE_EVENT, x, y);
            // 右键单击
            } else if (event.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                clickEvent(RIGHT_MOUSE_EVENT, x, y);
            }
        }
    }

    /**
     * 展示面板中所有未插旗的雷
     */
    void showAllMine() {
        if (gameParamaters.getLoseGame()) {
            mineBlockData.openAllMine();
            minePanel.render(mineBlockData);
        }
    }

    /**
     * 重置面板
     */
    void revertMineBlockData() {
        mineBlockData = new MineBlockData();
        minePanel.render(mineBlockData);
    }
}
