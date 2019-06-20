package tool;

import control.MineControl;
import view.BasicFrame;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏资源路径(图片大小32*32)
 */
public class GameTool {
    private static final int MIN_MINE_NUMBER = 0;
    private static final int MAX_MINE_NUMBER = 8;

    public static final String BLOCK_IMAGE_URL = "resources/block.png";
    public static final String FLAG_IMAGE_URL = "resources/flag.png";
    public static final String MINE_IMAGE_URL = "resources/mine.png";

    private static MineControl mineControl;

    private GameTool() {}

    /**
     * 返回对应数字(0-8)的图片
     */
    public static String getNumberImageURL(int mineAroundNumber) {
        if (mineAroundNumber < MIN_MINE_NUMBER || mineAroundNumber > MAX_MINE_NUMBER) {
            throw new IllegalArgumentException("No such a mineAroundNumber image!");
        }
        return "resources/" + mineAroundNumber + ".png";
    }

    /**
     * 在指定位置绘制图片
     */
    public static void drawImage(Graphics2D g, int x, int y, String imageURL) {
        ImageIcon imageIcon = new ImageIcon(imageURL);
        Image image = imageIcon.getImage();

        g.drawImage(image, x, y, null);
    }

    /**
     * 开始游戏
     */
    public static void startGame() {
        // 初始化总面板
        EventQueue.invokeLater(() -> {
            BasicFrame basicFrame = new BasicFrame();
            mineControl = new MineControl(basicFrame);
        });
    }

    /**
     * 打开砖块但砖块中有雷(输游戏)
     */
    public static void loseGame() {
        mineControl.showAllMine();

        int res = JOptionPane.showConfirmDialog(null, "重新开始?", "你输了！！！", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            GameParamaters.getGameProperties().setLoseGame(false);
            GameParamaters.getGameProperties().setWinGame(false);
            startGame();
        } else if (res == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * 打开所有砖块,找出所有雷(赢游戏)
     */
    public static void winGame() {

        int res = JOptionPane.showConfirmDialog(null, "再玩一次?", "你赢了！！！", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            GameParamaters.getGameProperties().setLoseGame(false);
            GameParamaters.getGameProperties().setWinGame(false);
            startGame();
        } else if (res == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}

