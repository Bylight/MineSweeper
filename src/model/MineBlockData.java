package model;

import tool.GameParamaters;

/**
 * 雷区数据
 */

public class MineBlockData {
    private GameParamaters myGameParamaters;
    private MineBlock[][] mineBlocks;

    public MineBlockData() {
        myGameParamaters = GameParamaters.getGameProperties();
        initMineBlockData();
    }

    public boolean isInBlockArea(int x, int y) {
        return (x >= 0 && x < myGameParamaters.getMineRow()) && (y >= 0 && y < myGameParamaters.getMineCloumn());
    }

    //------------------------------------------------------------------------------------------------------------------
    // get和set方法
    //------------------------------------------------------------------------------------------------------------------

    /**
     * 返回特定砖块是否含雷
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     * @return  当前砖块是否含雷(true/false)
     */
    public boolean getBlockIsMine(int x, int y) {
        if (!isInBlockArea(x, y))
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        return mineBlocks[x][y].isMine;
    }

    public boolean getHasBeeOpened(int x, int y) {
        if (!isInBlockArea(x, y))
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        return mineBlocks[x][y].hasBeenOpened;
    }

    public int getMineAroundNumber(int x, int y) {
        if (!isInBlockArea(x, y))
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        return mineBlocks[x][y].mineAroundNumber;
    }

    public boolean getHasFlag(int x, int y) {
        if (!isInBlockArea(x, y))
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        return mineBlocks[x][y].hasFlag;
    }

    /**
     * 将特定砖块打开
     * @param x
     * @param y
     */
    public void setOpened(int x, int y) {
        if (!mineBlocks[x][y].hasFlag)
            mineBlocks[x][y].hasBeenOpened = true;
    }

    /**
     * 若特定砖块未打开，则改变它的插旗状态
     * @param x
     * @param y
     */
    public void setHasFlag(int x, int y) {
        if (!mineBlocks[x][y].hasBeenOpened)
            mineBlocks[x][y].hasFlag = !mineBlocks[x][y].hasFlag;
    }

    /**
     * 初始化雷区数据
     */
    private void initMineBlockData() {
        // 初始化雷区
        mineBlocks = new MineBlock[myGameParamaters.getMineRow()][myGameParamaters.getMineCloumn()];
        for (int i = 0; i < myGameParamaters.getMineRow(); ++i) {
            for (int j = 0; j < myGameParamaters.getMineCloumn(); ++j) {
                mineBlocks[i][j] = new MineBlock();
            }
        }

        // 布雷算法
        shuffleMine();

        // 更新砖块状态
        updateMineData();
    }

    /**
     * 随机布雷
     */
    private void shuffleMine() {
        int blockNumber;

        blockNumber = myGameParamaters.getMineRow() * myGameParamaters.getMineCloumn();

        // 放置n个雷(由游戏难度决定个数)
        for (int i = 0; i < myGameParamaters.getMineNumber(); ++i) {
            mineBlocks[i / myGameParamaters.getMineCloumn()][i % myGameParamaters.getMineCloumn()].isMine = true;
        }

        // 将n个雷随机放置(每次随机将一个砖块放在第i个位置)
        for (int i = 0; i < blockNumber; ++i) {
            int randomNumber = (int) (Math.random() * (blockNumber - i)) + i;

            int x = randomNumber / myGameParamaters.getMineCloumn();
            int y = randomNumber % myGameParamaters.getMineCloumn();

            swapMineBlock(i / myGameParamaters.getMineCloumn(), i % myGameParamaters.getMineCloumn(), x, y);
        }
    }

    /**
     * 交换两个砖块的含雷情况
     * @param x1 砖块一的横坐标
     * @param y1 砖块一的纵坐标
     * @param x2 砖块二的横坐标
     * @param y2 砖块二的纵坐标
     */
    private void swapMineBlock(int x1, int y1, int x2, int y2) {
        boolean temp;
        temp = getBlockIsMine(x1, y1);
        mineBlocks[x1][y1].isMine = getBlockIsMine(x2, y2);
        mineBlocks[x2][y2].isMine = temp;
    }

    /**
     * 根据雷的情况更新砖块数组
     */
    private void updateMineData() {
        updateAllMineNumber();
    }

    /**
     * 更新所有砖块周围的雷数(mineAroundNumber)
     */
    private void updateAllMineNumber() {
        for (int i = 0; i < myGameParamaters.getMineRow(); ++i) {
            for (int j = 0; j < myGameParamaters.getMineCloumn(); ++j) {
                if (!mineBlocks[i][j].isMine) {
                    updateOneMineNumber(i, j);
                }
            }
        }
    }

    /**
     * 更新特定砖块周围雷数
     * @param x 该砖块的横坐标
     * @param y 该砖块的纵坐标
     */
    private void updateOneMineNumber(int x, int y) {
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (isInBlockArea(i ,j) && mineBlocks[i][j].isMine) {
                    ++mineBlocks[x][y].mineAroundNumber;
                }
            }
        }
    }

    // 雷区砖块模型
    private class MineBlock {
        private int mineAroundNumber;
        private boolean hasFlag;
        private boolean isMine;
        private boolean hasBeenOpened;

        private MineBlock() {
            hasFlag = false;
            isMine = false;
            hasBeenOpened = false;
            mineAroundNumber = 0;
        }

    }
}
