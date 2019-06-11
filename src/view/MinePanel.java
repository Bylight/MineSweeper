package view;

import model.MineBlockData;
import tool.GameParamaters;
import tool.GameResources;

import javax.swing.*;
import java.awt.*;

/**
 * 雷区面板
 */
public class MinePanel extends JPanel {
    private GameParamaters myGameParamaters;

    private int height;
    private int width;
    private MineBlockData mineBlockData;

    MinePanel() {
        super(true);
        myGameParamaters = GameParamaters.getGameProperties();
        initMinePanel();
    }

    private void initMinePanel() {
        setSize(myGameParamaters.getMineHeight(), myGameParamaters.getFrameWidth());
    }

    /**
     * 重画
     * @param mineBlockData 雷区相关数据
     */
    public void render(MineBlockData mineBlockData) {
        this.mineBlockData = mineBlockData;
        System.out.println("方法调用了 ！！！");
        repaint();
    }

    /**
     * 绘制雷区
     * @param g 雷区面板的上下文
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        try {

            for (int i = 0; i < myGameParamaters.getMineRow(); ++i) {
                for (int j = 0; j < myGameParamaters.getMineCloumn(); ++j) {

                    // 如果当前砖块未被打开且没有插旗(只显示砖块)
                    if (!mineBlockData.getHasBeeOpened(i, j) && !mineBlockData.getHasFlag(i, j)) {
                        GameResources.drawImage(g2d, j * myGameParamaters.getBlockWidth(), i * myGameParamaters.getBlockHeight()
                                , GameResources.BLOCK_IMAGE_URL);
                    // 如果当前砖块未被打开但有插旗(显示旗子)
                    } else if (!mineBlockData.getHasBeeOpened(i, j) && mineBlockData.getHasFlag(i, j)) {
                        GameResources.drawImage(g2d, j * myGameParamaters.getBlockWidth(), i * myGameParamaters.getBlockHeight()
                                , GameResources.FLAG_IMAGE_URL);
                    // 如果当前砖块被打开且是雷(显示雷，游戏结束)
                    } else if (mineBlockData.getHasBeeOpened(i, j) && mineBlockData.getBlockIsMine(i, j)) {
                        GameResources.drawImage(g2d, j * myGameParamaters.getBlockWidth(), i * myGameParamaters.getBlockHeight()
                                , GameResources.MINE_IMAGE_URL);
                    // 如果当前砖块被打开却不是雷(显示周围雷的个数)
                    } else if (mineBlockData.getHasBeeOpened(i, j) && mineBlockData.getMineAroundNumber(i, j) >= 0) {
                        GameResources.drawImage(g2d, j * myGameParamaters.getBlockWidth(), i * myGameParamaters.getBlockHeight()
                                , GameResources.getNumberImageURL(mineBlockData.getMineAroundNumber(i, j)));
                    }

                }
            }

        } catch (NullPointerException e) {
            System.out.println("初始化中，请稍等");
        }
    }
}
