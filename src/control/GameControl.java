package control;

import tool.GameParamaters;
import view.BasicFrame;
import view.statusbar.StatusPanel;

import javax.swing.*;
import java.awt.*;

public class GameControl {
    private static final GameControl GAME_CONTROL = new GameControl();

    private MineControl mineControl;
    private BasicFrame basicFrame;
    private StatusPanel statusPanel;

    private GameControl() {}

    public static GameControl getGameControl() {
        return GAME_CONTROL;
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        // 初始化总面板
        EventQueue.invokeLater(() -> {
            basicFrame = new BasicFrame();
            mineControl = new MineControl(basicFrame);

            statusPanel = basicFrame.getStatusPanel();
            statusPanel.startTimer();
        });
    }

    /**
     * 打开砖块但砖块中有雷(输游戏)
     */
    void loseGame() {
        mineControl.showAllMine();

        statusPanel.pauseTimer();

        showRestartDialog();
    }

    /**
     * 打开所有砖块,找出所有雷(赢游戏)
     */
    void winGame() {

        statusPanel.pauseTimer();

        showRestartDialog();
    }

    /**
     * 弹出是否重新开始游戏的对话框
     */
    private void showRestartDialog() {
        int res = JOptionPane.showConfirmDialog(null, "再来一把?", null, JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            restartGame();
        } else if (res == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * 重新开始游戏
     */
    private void restartGame() {
        GameParamaters.getGameProperties().setLoseGame(false);
        GameParamaters.getGameProperties().setWinGame(false);
        mineControl.revertMineBlockData();
        statusPanel.restartTimer();
    }

}
