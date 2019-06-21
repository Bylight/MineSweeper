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

    private GameParamaters myGamePatamaters;

    /** 计时器标签 */
    private TimeLabel timeLabel;

    /** 计时器 */
    private Timer timer;

    public StatusPanel() {
        myGamePatamaters = GameParamaters.getGameProperties();
        initSattusPanel();
    }

    private void initSattusPanel() {
        setBackground(Color.lightGray);
        setOpaque(true);
        setLayout(new BorderLayout());
        initTimeLabel();
    }

    /**
     * 初始化计时器标签且开始计时
     */
    private void initTimeLabel() {
        timeLabel = new TimeLabel();
        add(timeLabel, BorderLayout.WEST);
        initTimer();
        timer.start();
    }

    /**
     * 初始化计时器(在第一次玩游戏时调用)
     */
    private void initTimer() {
        timer = new Timer(TIME_INTERVAL, e -> timeLabel.countTime());
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
}
