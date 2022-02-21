package cn.liuhangxiao.snake_v2.Listener;

/**
 * 游戏监听器
 */
public interface GameListener {
    /**
     * 开始
     */
    void gameStart();

    /**
     * 结束
     */
    void gameOver();

    /**
     * 暂停
     */
    void gamePause();

    /**
     * 继续
     */
    void gameContinue();

    /**
     * 改变分数
     */
    void changeScore();
}
