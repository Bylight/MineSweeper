package view.statusbar;

import tool.GameParamaters;

import javax.swing.*;
import java.awt.*;

public class TimeLabel extends JLabel {
    private GameParamaters gameParamaters;

    // 游戏用时
    private int hour;
    private int minute;
    private int second;

    TimeLabel() {
        gameParamaters = GameParamaters.getGameParamaters();
        initTimeLable();
        resetTime();
    }

    private void initTimeLable() {

        setFont(new java.awt.Font("Dialog", Font.BOLD, gameParamaters.getLabelFontSize()));
        setForeground(Color.RED);
        setHorizontalAlignment(SwingConstants.CENTER);


        setBackground(Color.white);
        setOpaque(true);
    }

    /**
     * 重置计时器时间
     */
    void resetTime() {
        hour = 0;
        minute = 0;
        second = 0;
        setText(toString());
    }

    /**
     * 计时器时间自增
     */
    void countTime() {
        ++second;
        if (second == 60) {
            second = 0;
            ++minute;
        }
        if (minute == 60) {
            minute = 0;
            ++hour;
        }
        setText(toString());
    }

    /**
     * 重写toString，返回计时器标签当前时间
     * @return 计时器标签当前应显示的时间字符串
     */
    @Override
    public String toString() {
        String hourString = String.format("%02d", hour);
        String minuteString = String.format("%02d", minute);
        String secondString = String.format("%02d", second);
        return (hourString + ":" + minuteString + ":" + secondString);
    }
}
