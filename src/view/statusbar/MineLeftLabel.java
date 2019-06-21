package view.statusbar;

import tool.GameParamaters;

import javax.swing.*;
import java.awt.*;

/**
 * 剩余雷数的标签
 */
public class MineLeftLabel extends JLabel {
    private GameParamaters gameParamaters;

    private int mineLeft;

    MineLeftLabel() {
        gameParamaters = GameParamaters.getGameParamaters();
        initMineLeftLabel();
        resetMineLeft();
    }

    /**
     * 初始化该标签的样式、字体等格式
     */
    private void initMineLeftLabel() {

        setFont(new java.awt.Font("Dialog", Font.BOLD, gameParamaters.getLabelFontSize()));
        setForeground(Color.RED);
        setHorizontalAlignment(SwingConstants.CENTER);

        setBackground(Color.white);
        setOpaque(true);
    }

    /**
     * 重置剩余雷数
     */
    void resetMineLeft() {
        mineLeft = gameParamaters.getMineNumber();
        setText("剩余雷数: " + toString());
    }

    /**
     * 增加剩余雷数
     */
    void addMineLeft() {
        ++mineLeft;
        setText("剩余雷数: " + toString());
    }

    /**
     * 减少剩余雷数
     */
    void removeMineLeft() {
        --mineLeft;
        setText("剩余雷数: " + toString());
    }

    /**
     * 重写toString，返回还需排雷数
     * @return 剩余雷数标签当前需要显示的剩余雷数
     */
    @Override
    public String toString() {
        return String.format("%02d", mineLeft);
    }
}
