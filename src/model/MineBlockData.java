package model;

import tool.GameParamaters;
import tool.GameTool;

/**
 * 雷区数据
 */

public class MineBlockData {
    private GameParamaters gameParamaters;
    private MineBlock[][] mineBlocks;

    private Runnable removeMineNumber;
    private Runnable addMineNumber;

    /**被旗子标记的雷的数目*/
    private int mineWithFlag;
    /**当前已标记的旗子总数*/
    private int flagNumbers;
    /**未被打开或未被旗子标记的砖块数目*/
    private int needOpenOrFlag;

    public MineBlockData() {
        gameParamaters = GameParamaters.getGameParamaters();
        initMineBlockData();
    }

    /**
     * 设置用于修改剩余雷数的回调接口
     * @param removeMineNumber 用于减少剩余雷数
     * @param addMineNumber 用于增加剩余雷数
     */
    public void setRunnable(Runnable removeMineNumber, Runnable addMineNumber) {
        this.removeMineNumber = removeMineNumber;
        this.addMineNumber = addMineNumber;
    }

    /**
     * 判断是否已经赢得游戏(找出所有的雷且打开所有砖块)
     */
    public void hasWin() {
        gameParamaters.setWinGame((mineWithFlag == flagNumbers)
                && (mineWithFlag == gameParamaters.getMineNumber()) && (needOpenOrFlag == 0));
    }

    /**
     * 判断给定坐标合法性
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     * @return 当前坐标是否合法(true/false)
     */
    private boolean isInBlockArea(int x, int y) {
        return isRowLeaglegal(x) && isCloumnLegal(y);
    }

    /**
     * 判断给定横坐标合法性
     * @param x 砖块横坐标
     * @return 当前横坐标是否合法(true/false)
     */
    private boolean isRowLeaglegal(int x) {
        return x >= 0 && x < gameParamaters.getMineRow();
    }

    /**
     * 判断给定纵坐标合法性
     * @param y 砖块纵坐标
     * @return 当前纵坐标是否合法(true/false)
     */
    private boolean isCloumnLegal(int y) {
        return y >= 0 && y < gameParamaters.getMineCloumn();
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
        if (!isInBlockArea(x, y)) {
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        }
        return mineBlocks[x][y].isMine;
    }

    /**
     * 查看当前砖块是否已被开启
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     * @return  当前砖块是否被开启(true/false)
     */
    public boolean getHasBeeOpened(int x, int y) {
        if (!isInBlockArea(x, y)) {
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        }
        return mineBlocks[x][y].hasBeenOpened;
    }

    /**
     * 获取当前砖块周围含雷的砖块数目(0-8)
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     * @return  当前砖块周围有几颗雷
     */
    public int getMineAroundNumber(int x, int y) {
        if (!isInBlockArea(x, y)) {
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        }
        return mineBlocks[x][y].mineAroundNumber;
    }

    /**
     * 查看当前砖块是否被插旗
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     * @return  当前砖块是否被插旗(true/false)
     */
    public boolean getHasFlag(int x, int y) {
        if (!isInBlockArea(x, y)) {
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        }
        return mineBlocks[x][y].hasFlag;
    }

    /**
     * 打开指定砖块
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     */
    private void openBlock(int x, int y) {
        if (!mineBlocks[x][y].hasBeenOpened && !mineBlocks[x][y].hasFlag) {
            mineBlocks[x][y].hasBeenOpened = true;
            --needOpenOrFlag;
        }
    }


    /**
     * 双击已展开方块时，在用旗子标明附近雷数时自动展开周围8个非雷格子
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     */
    public void previewBlockAround(int x, int y) {
        if (!isInBlockArea(x, y)) {
            throw new IllegalArgumentException("wrong array position! It is (" + x + ", " + y + ") now!");
        }

        int previewNumber = 0;
        int rightNumber = 0;
        // 计数(用于判断插旗是否正确)
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (isInBlockArea(i, j)) {
                    // rightNumber--插在雷上的旗子   previewNum--总共插旗数
                    if (mineBlocks[i][j].hasFlag) {
                        if (mineBlocks[i][j].isMine) {
                            ++rightNumber;
                        }
                        ++previewNumber;
                    }
                }
            }
        }

        // 插旗正确(周围插旗数等于雷数且正好插在雷上)则展开
        if (rightNumber == mineBlocks[x][y].mineAroundNumber && rightNumber == previewNumber) {
            for (int i = x - 1; i <= x + 1; ++i) {
                for (int j = y - 1; j <= y + 1; ++j) {
                    if (isInBlockArea(i, j)) {
                        openBlock(i, j);
                    }
                }
            }
        }
    }

    /**
     * 将特定砖块打开
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     */
    public void setOpened(int x, int y) {
        // 如果点到旗，应无反应
        if (mineBlocks[x][y].hasFlag) {
            return;
        }

        // 如果点击到雷，结束游戏
        if (mineBlocks[x][y].isMine) {
            gameParamaters.setLoseGame(true);
            return;
        }

        openBlock(x, y);

        // 单击空白砖块
        if (mineBlocks[x][y].mineAroundNumber == 0) {
            // 上
            if (isInBlockArea(x - 1, y) && !mineBlocks[x - 1][y].hasBeenOpened && !mineBlocks[x - 1][y].isMine) {
                setOpened(x - 1, y);
            }
            // 下
            if (isInBlockArea(x + 1, y) && !mineBlocks[x + 1][y].hasBeenOpened && !mineBlocks[x + 1][y].isMine) {
                setOpened(x + 1, y);
            }
            // 左
            if (isInBlockArea(x, y - 1) && !mineBlocks[x][y - 1].hasBeenOpened && !mineBlocks[x][y - 1].isMine) {
                setOpened(x, y - 1);
            }
            // 右
            if (isInBlockArea(x, y + 1) && !mineBlocks[x][y + 1].hasBeenOpened && !mineBlocks[x][y + 1].isMine) {
                setOpened(x, y + 1);
            }
            // 左上
            if (isInBlockArea(x - 1, y - 1) && !mineBlocks[x - 1][y - 1].hasBeenOpened && !mineBlocks[x - 1][y - 1].isMine) {
                setOpened(x - 1, y - 1);
            }
            // 右上
            if (isInBlockArea(x - 1, y + 1) && !mineBlocks[x - 1][y + 1].hasBeenOpened && !mineBlocks[x - 1][y + 1].isMine) {
                setOpened(x - 1, y + 1);
            }
            // 左下
            if (isInBlockArea(x + 1, y - 1) && !mineBlocks[x + 1][y - 1].hasBeenOpened && !mineBlocks[x + 1][y - 1].isMine) {
                setOpened(x + 1, y - 1);
            }
            // 右下
            if (isInBlockArea(x + 1, y + 1) && !mineBlocks[x + 1][y + 1].hasBeenOpened && !mineBlocks[x + 1][y + 1].isMine) {
                setOpened(x + 1, y + 1);
            }
        }

    }

    /**
     * 打开所有雷
     */
    public void openAllMine() {
        for (int i = 0; i < gameParamaters.getMineRow(); ++i) {
            for (int j = 0; j < gameParamaters.getMineCloumn(); ++j) {
                if (mineBlocks[i][j].isMine) {
                    openBlock(i, j);
                }
            }
        }
    }

    /**
     * 若特定砖块未打开，则改变它的插旗状态
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     */
    public void setHasFlag(int x, int y) {
        if (!mineBlocks[x][y].hasBeenOpened) {
            changeBlockFlag(x, y);
        }
    }

    /**
     * 插旗/拔旗
     * @param x 砖块横坐标
     * @param y 砖块纵坐标
     */
    private void changeBlockFlag(int x, int y) {
        int number;

        mineBlocks[x][y].hasFlag = !mineBlocks[x][y].hasFlag;

        if (mineBlocks[x][y].hasFlag) {
            number = 1;
        } else {
            number = -1;
        }

        flagNumbers += number;
        needOpenOrFlag -= number;

        // 如果旗插在雷上，判断是否已经可以根据已知信息推出
        if (mineBlocks[x][y].isMine) {
            mineWithFlag += number;
            if (number == 1) {
                addFlagMine();
            } else {
                removeFlagMine();
            }
        }
    }

    /**
     * 使用removeMineNumer接口，实现在GameCtrol中让剩余雷数自减
     */
    private void addFlagMine() {
        removeMineNumber.run();
    }

    /**
     * 使用addMineNumber接口，实现在拔旗时在GameControl中让剩余雷数自增
     */
    private void removeFlagMine() {
        addMineNumber.run();
    }

    /**
     * 初始化雷区数据
     */
    private void initMineBlockData() {
        flagNumbers = 0;
        mineWithFlag = 0;
        needOpenOrFlag = gameParamaters.getMineRow() * gameParamaters.getMineCloumn();

        // 初始化雷区
        mineBlocks = new MineBlock[gameParamaters.getMineRow()][gameParamaters.getMineCloumn()];
        for (int i = 0; i < gameParamaters.getMineRow(); ++i) {
            for (int j = 0; j < gameParamaters.getMineCloumn(); ++j) {
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

        blockNumber = gameParamaters.getMineRow() * gameParamaters.getMineCloumn();

        // 放置n个雷(由游戏难度决定个数)
        for (int i = 0; i < gameParamaters.getMineNumber(); ++i) {
            mineBlocks[i / gameParamaters.getMineCloumn()][i % gameParamaters.getMineCloumn()].isMine = true;
        }

        // 将n个雷随机放置(每次随机将一个砖块放在第i个位置)
        for (int i = 0; i < blockNumber; ++i) {
            int randomNumber = (int) (Math.random() * (blockNumber - i)) + i;

            int x = randomNumber / gameParamaters.getMineCloumn();
            int y = randomNumber % gameParamaters.getMineCloumn();

            swapMineBlock(i / gameParamaters.getMineCloumn(), i % gameParamaters.getMineCloumn(), x, y);
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
        for (int i = 0; i < gameParamaters.getMineRow(); ++i) {
            for (int j = 0; j < gameParamaters.getMineCloumn(); ++j) {
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

    /**
     * 单个砖块
     */
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
