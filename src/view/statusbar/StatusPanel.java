package view.statusbar;

import tool.GameParamaters;

import javax.swing.*;
import java.awt.*;

/**
 * 状态栏面板
 */
public class StatusPanel extends JPanel {
    /** 计数间隔设为1000ms，即1s */
    private static int TIME_INTERVAL = 1000;

    private GameParamaters gameParamaters;

    /** 计时器标签 */
    private TimeLabel timeLabel;

    /** 剩余雷数标签 */
    private MineLeftLabel mineLeftLabel;

    /** 计时器 */
    private Timer timer;

    public StatusPanel() {
        gameParamaters = GameParamaters.getGameParamaters();
        initSattusPanel();
    }

    private void initSattusPanel() {
        setBackground(Color.lightGray);
        setOpaque(true);
        setLayout(new BorderLayout());
        initTimeLabel();
        initMineLeftLabel();
    }

    /**
     * 初始化剩余雷数标签
     */
    private void initMineLeftLabel() {
        mineLeftLabel = new MineLeftLabel();
        add(mineLeftLabel, BorderLayout.EAST);
        mineLeftLabel.setSize(timeLabel.getWidth(), timeLabel.getHeight());
    }

    /**
     * 重置剩余雷数
     */
    public void resetMineLeft() {
        mineLeftLabel.resetMineLeft();
    }

    /**
     * 剩余雷数减一
     */
    public void removeMineLeft() {
        mineLeftLabel.removeMineLeft();
    }

    /**
     * 剩余雷数加一
     */
    public void addMineLeft() {
        mineLeftLabel.addMineLeft();
    }

    /**
     * 初始化计时器标签且开始计时
     */
    private void initTimeLabel() {
        timeLabel = new TimeLabel();
        add(timeLabel, BorderLayout.WEST);
    }

    /**
     * 初始化计时器(在第一次玩游戏时调用)
     */
    private void initTimer() {
        timer = new Timer(TIME_INTERVAL, e -> timeLabel.countTime());
    }

    /**
     * 开始计时
     */
    public void startTimer() {
        initTimer();
        timer.start();
    }

    /**
     * 暂停计时(在游戏结算时调用，例如输或是赢)
     */
    public void pauseTimer() {
        timer.stop();
    }

    /**
     * 重新开始计时(在游戏重新开始时调用)
     */
    public void resetTimer() {
        timeLabel.resetTime();
        timer.restart();
    }

    /**
     * 用于在游戏结束时获取用时
     * @return 当前计时器时间
     */
    public String getTime() {
        return timeLabel.toString();
    }

    /**
     * 用于在游戏结算时获取排雷数
     * @return 目前已经排除的雷数
     */
    public String getCleanedMineNumber() {
        int cleanedMineNumber = gameParamaters.getMineNumber() - Integer.parseInt(mineLeftLabel.toString());
        return String.valueOf(cleanedMineNumber);
    }
}
