package tool;


/**
 * 游戏参数(单例)
 *
 * 使用方法：
 * 1. 获取参数
 *  单个砖块宽度  BLOCK_WIDTH
 *  单个砖块高度  BLOCK_HEIGHT
 *
 *  菜单栏高度   MENU_BAR_HEIGHT
 *  状态栏高度   STATUS_BAR_HEIGHT
 *
 *  所有组件及界面宽度   getFrameWidth()
 *  界面高度             getFrameHeight()
 *  砖块数组的高度       getMineHeight()
 *
 *  砖块数组行数         getMineRow()
 *  砖块数组列数         getMineCloumn()
 *
 *  砖块数组中雷的数量   getMineNumber()
 *
 * 2. 外部接口
 *  获取参数单例         getGameParamaters()
 *  设置游戏难度          setGameLevel(int gameLevel)
 *
 */
@SuppressWarnings("ALL")
public class GameParamaters {
    private static final GameParamaters GAME_PARAMATERS = new GameParamaters();

    private static final int LABEL_FONT_SIZE = 16;

    private static final int BLOCK_WIDTH = 32;
    private static final int BLOCK_HEIGHT = 32;

    private static final int MENU_BAR_HEIGHT = 20;
    private static final int STATUS_BAR_HEIGHT = 60;

    private boolean loseGame;
    private boolean winGame;

    /** 界面宽度(实际是由砖块数组的宽度决定, 且所有控件宽度一致) */
    private int frameWidth;
    /** 界面高度 */
    private int frameHeight;

    /** 游戏难度; 1-3 分别代表简单,中等和困难 */
    private int gameLevel;
    /** 砖块数组的行 */
    private int mineRow;
    /** 砖块数组的列 */
    private int mineCloumn;
    /** 砖块数组中雷的个数 */
    private int mineNumber;

    private GameParamaters() {
        loseGame = false;
        winGame = false;
        gameLevel = 1;
        setParamaters();
    }

    public static GameParamaters getGameParamaters() {
        return GAME_PARAMATERS;
    }

    /**
     * 根据游戏难度修改游戏参数
     */
    private void setParamaters() {
        switch (gameLevel) {
            case 1:
                switchEasyMode();
                break;
            case 2:
                switchMiddleMode();
                break;
            default:
                switchHardMode();
        }
    }

    /**
     * 在三种游戏难度下参数的变化
     *  先设置砖块数组的行、列以及雷的个数，再调用setFrameSizeParamaters方法修改其余组件的高度和宽度
     */
    private void switchEasyMode() {
        mineRow = 9;
        mineCloumn = 9;
        mineNumber = 10;
        setFrameSizeParamaters();
    }

    private void switchMiddleMode() {
        mineRow = 16;
        mineCloumn = 16;
        mineNumber = 40;
        setFrameSizeParamaters();
    }

    private void switchHardMode() {
        mineRow = 16;
        mineCloumn = 30;
        mineNumber = 99;
        setFrameSizeParamaters();
    }

    /**
     * 设置界面大小相关的参数
     */
    private void setFrameSizeParamaters() {
        setAllWidth();
        setFrameHeight();
    }

    private void setAllWidth() {
        frameWidth = mineCloumn * BLOCK_WIDTH;
    }

    private void setFrameHeight() {
        frameHeight = MENU_BAR_HEIGHT + STATUS_BAR_HEIGHT + getMineHeight();
    }

    /**
     * 公共接口
     */
    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getMineRow() {
        return mineRow;
    }

    public int getMineCloumn() {
        return mineCloumn;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    public int getMineHeight() {
        return mineRow * BLOCK_HEIGHT;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
        setParamaters();
    }

    public int getStatusBarY() {
        return MENU_BAR_HEIGHT;
    }

    public int getBlockY() {
        return MENU_BAR_HEIGHT + STATUS_BAR_HEIGHT;
    }

    public int getBlockWidth() {
        return BLOCK_WIDTH;
    }

    public int getBlockHeight() {
        return BLOCK_HEIGHT;
    }

    public int getMenuBarHeight() {
        return MENU_BAR_HEIGHT;
    }

    public int getStatusBarHeight() {
        return STATUS_BAR_HEIGHT;
    }

    public boolean getLoseGame() {
        return loseGame;
    }

    public void setLoseGame(boolean loseGame) {
        this.loseGame = loseGame;
    }

    public boolean getWinGame() {
        return winGame;
    }

    public void setWinGame(boolean winGame) {
        this.winGame = winGame;
    }

    public int getTimeLabelWidth() {
        return getFrameWidth() / 5;
    }

    public int getLabelFontSize() {
        return LABEL_FONT_SIZE;
    }
}
