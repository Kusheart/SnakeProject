package cn.liuhangxiao.snake_v2.Listener;

/**
 * 蛇类监听接口
 */
public interface SnakeListener {
    /**
     * 蛇移动
     */
    void snakeMoved();

    /**
     * 蛇吃食物
     */
    void snakeEatFood();
}
