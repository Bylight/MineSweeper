package view;

import tool.GameParamaters;
import view.StatusBar.StatusPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 绘制游戏基础窗口
 */
public class BasicFrame extends JFrame {
    private GameParamaters myGameParamaters;
    private MinePanel minePanel;
    private BasicPanel basicPanel;
    private StatusPanel statusPanel;

    public BasicFrame() {
        super();
        myGameParamaters = GameParamaters.getGameProperties();
        basicPanel = new BasicPanel();
        initFrame();
    }

    private void initFrame() {
        setTitle("MinerSweeper");
        // 注意： JFrame大小并不是实际的可见大小，应在基础的Panel中使用setPreferredSize()
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // 设置内容面板
        setContentPane(basicPanel);
        pack();
        // 设置窗口居中
        setLocationRelativeTo(null);
    }

    public MinePanel getMinePanel() {
        return minePanel;
    }

    /**
     * 内部类： 基础面板
     */
    private class BasicPanel extends JPanel {
        private BasicPanel() {
            // 开启双缓存
            super(true);
            initBasicPanel();
        }

        /**
         * 初始化基础面板
         */
        private void initBasicPanel() {
            setLayout(null);
            setPreferredSize(new Dimension(myGameParamaters.getFrameWidth(), myGameParamaters.getFrameHeight()));
            initMinePanel();
            initStatusPanel();
        }

        /**
         * 初始化状态栏面板
         */
        private void initStatusPanel() {
            statusPanel = new StatusPanel();
            add(statusPanel);
            statusPanel.setBounds(0, myGameParamaters.getStatusBarY(),
                    myGameParamaters.getFrameWidth(), myGameParamaters.getStatusBarHeight());
        }

        /**
         * 初始化雷区面板
         */
        private void initMinePanel() {
            minePanel = new MinePanel();
            add(minePanel);
            minePanel.setBounds(0, myGameParamaters.getBlockY(),
                    myGameParamaters.getFrameWidth(), myGameParamaters.getMineHeight());
        }
    }
}
