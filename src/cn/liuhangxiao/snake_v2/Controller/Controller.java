package cn.liuhangxiao.snake_v2.Controller;

import cn.liuhangxiao.snake_v2.Listener.GameListener;
import cn.liuhangxiao.snake_v2.Listener.SnakeListener;
import cn.liuhangxiao.snake_v2.Pojo.Food;
import cn.liuhangxiao.snake_v2.Pojo.Ground;
import cn.liuhangxiao.snake_v2.Pojo.Snake;
import cn.liuhangxiao.snake_v2.Utils.OverUtil;
import cn.liuhangxiao.snake_v2.View.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * 控制器<br>
 * 控制地形(Ground), 蛇(Snake), 食物(Food)<br>
 * 负责游戏的逻辑<BR>
 * 处理按键事件<BR>
 * @author 刘航霄
 */
public class Controller extends KeyAdapter implements SnakeListener {

    /* 静态变量 */
    /** 地形 */
    private final Ground ground;
    /** 蛇 */
    private final Snake snake;
    /** 食物 */
    private final Food food;
    /** 显示 */
    private final GamePanel gamePanel;
    /** 提示信息 */
    private JLabel gameInfoLabel;
    /** 游戏状态 */
    private boolean playing;
    /** 游戏地图 */
    private int map;
    /** 游戏分数 */
    private int score;
    /** 通关标记 */
    private boolean success;
    /** 目标分数 */
    private int target = OverUtil.target;
    /** 游戏模式 */
    private int gameMode = OverUtil.gameMode;

    /** 是否需要增加目标分数 */
    private boolean skip = true;

    /** 控制器监听器 */
    private Set<GameListener> listeners = new HashSet<>();

    /**
     * 处理按键事件<br>
     * 接受按键，根据按键不同，发出不同指令<br>
     * W/up:改变蛇移动方向向上<br>
     * S/down:改变蛇方向向下<br>
     * A/left:蛇向左移动<br>
     * D/right:蛇向右移动<br>
     * 空格:暂停/继续<br>
     * E/PAGE_UP:加快蛇移速<br>
     * Q/PAGE_DOWN:减慢蛇移速<br>
     * R:重新开始游戏<br>
     */
    @Override
    public void keyPressed(KeyEvent event){
        if(event.getKeyCode() != KeyEvent.VK_R && !playing){
            return;
        }
        /* 根据按键不同，让蛇改变不同方向 */
        switch (event.getKeyCode()){
            /* 向上移动——W/up */
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //改变蛇方向——上
                snake.changeDirection(Snake.up);
                break;
            /* 向下移动——S/down */
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //改变蛇方向——下
                snake.changeDirection(Snake.down);
                break;
            /* 向左移动——A/left */
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                //改变蛇方向——左
                snake.changeDirection(Snake.left);
                break;
            /* 向右移动——D/right */
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                //改变蛇方向——右
                snake.changeDirection(Snake.right);
                break;
            /* 空格（暂停） */
            case KeyEvent.VK_SPACE:
                //游戏暂停
                snake.changePause();
                for (GameListener l : listeners) {
                    if (snake.isPause()) {
                        l.gamePause();
                    } else {
                        l.gameContinue();
                    }
                }
                break;
            /* E/PAGE_UP 加速 */
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_E:
                //蛇加速
                snake.speedUp();
                break;
            /* Q/PAGE_DOWN 减速 */
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_Q:
                //蛇减速
                snake.speedDown();
                break;
            /* R 重新开始游戏 */
            case KeyEvent.VK_R:
                if(!isPlaying()){
                    newGame();
                    //重丢一个食物
                    food.setLocation(ground.getFreePoint());
                    pauseGame();
                }
                break;
            default:
                break;
        }

        /* 重新显示 */
        if(gamePanel != null){
            gamePanel.redisplay(ground, snake, food);
        }
        /* 更新画面提示 */
        if(gameInfoLabel != null){
            gameInfoLabel.setText(getNewInfo());
        }
    }

    /**
     * 处理蛇的移动事件
     */
    @Override
    public void snakeMoved() {
        /* 判断是否吃到食物 */
        if(food != null && food.isSnakeEatFood(snake)){
            /* 蛇吃到食物时，增加身体，再重丢一个食物 */
            snake.eatFood();
            food.setLocation(ground == null ? food.getNew() : ground.getFreePoint());
        }else if(ground != null && ground.isSnakeEatRock(snake)){
            /* 如果吃到的是石头，就结束游戏 */
            stopGame();
        }
        /* 如果吃到自己，就结束游戏 */
        if(snake.isEatBody()){
            stopGame();
        }
        //重绘
        if(gamePanel != null){
            gamePanel.redisplay(ground, snake, food);
        }
        /* 更新画面提示 */
        if(gameInfoLabel != null){
            gameInfoLabel.setText(getNewInfo());
        }
    }

    /**
     * 接受Snake,Food,Ground对象的构造器
     */
    public Controller(Snake snake, Food food, Ground ground, GamePanel gamePanel) {
        this.snake = snake;
        this.food = food;
        this.ground = ground;
        this.gamePanel = gamePanel;
        /* 先丢一个食物 */
        if (ground != null && food != null) {
            food.setLocation(ground.getFreePoint());
        }
        /* 注册监听器 */
        this.snake.addSnakeListener(this);
    }

    /**
     * 接受Snake,Food,Ground对象的构造器<br>
     * 多接受一个显示提示信息的JLabel
     *
     */
    public Controller(Snake snake, Food food, Ground ground,
                      GamePanel gamePanel, JLabel gameInfoLabel) {

        this(snake, food, ground, gamePanel);
        //游戏提示信息
        this.setGameInfoLabel(gameInfoLabel);

        gameInfoLabel.setText(getNewInfo());
    }

    /**
     * 开始一个新游戏
     */
    public void newGame() {
        if(ground != null){
            switch (map){
                case 2:
                    //清空石头
                    ground.Clear();
                    //重绘石头
                    ground.CreateRocks_1();
                    //如果游戏模式是闯关(gameMode=0)，则修改通关分数
                    if(gameMode==0&&skip){
                        target = target + 3;
                    }
                    food.setLocation(ground.getFreePoint());
                    skip = true;
                    break;
                case 3:
                    ground.Clear();
                    ground.CreateRocks_2();
                    if(gameMode==0&&skip) {
                        target = target + 3;
                    }
                    food.setLocation(ground.getFreePoint());
                    skip = true;
                    break;
                case 4:
                    ground.Clear();
                    ground.CreateRocks_3();
                    if(gameMode==0&&skip) {
                        target = target + 3;
                    }
                    food.setLocation(ground.getFreePoint());
                    skip = true;
                    break;
                case 5:
                    ground.Clear();
                    ground.CreateRocks_4();
                    if(gameMode==0&&skip) {
                        target = target + 3;
                    }
                    //重丢一个食物
                    food.setLocation(ground.getFreePoint());
                    //出生点保护
                    ground.DeleteRocks_spawnpoint();
                    skip = true;
                    break;
                default:
                    ground.init();
                    target = 10;
                    //重丢一个食物
                    food.setLocation(ground.getFreePoint());
                    //出生点保护
                    ground.DeleteRocks_spawnpoint();
                    skip = true;
                    break;
            }
        }
        //游玩状态开启
        playing = true;
        //重置新游戏
        snake.resetNew();
        success=false;
        //重置分数
        score = 0;
        for(GameListener listener : listeners){
            listener.changeScore();
            listener.gameStart();
        }
    }

    /**
     * 结束游戏
     */
    public void stopGame() {
        if (playing) {
            //游玩状态结束
            playing = false;
            //让蛇死亡
            snake.dead();
            for (GameListener listener : listeners){
                //循环监听，进行游戏结束事件
                listener.gameOver();
            }
        }
    }

    /**
     * 暂停游戏
     */
    public void pauseGame() {
        snake.setPause(true);
        for (GameListener listener : listeners) {
            listener.gamePause();
        }
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        snake.setPause(false);
        for (GameListener listener : listeners) {
            listener.gameContinue();
        }
    }

    /**
     * 得到最新提示信息
     * @return 蛇的最新信息
     */
    public String getNewInfo() {
        if(!snake.isLive()){
            // 提示：按 R 开始新游戏
            return " ";
        }else {
            return "提示：" + "速度 " + 1000 / snake.getSpeed() + "格/每秒";
        }
    }

    /**
     * 添加监听器
     */
    public synchronized void addGameListener(GameListener l) {
        if (l != null) {
            this.listeners.add(l);
        }
    }

    /**
     * 游戏进行状态
     * @return playing值
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * 游戏暂停状态
     * @return boolean
     */
    public boolean isPausingGame() {
        return snake.isPause();
    }

    /**
     * 处理蛇吃到食物后触发的事件<br>
     * 可以覆盖这个方法增加功能，如:增加记分
     */
    @Override
    public void snakeEatFood() {
        if(score!=target-1||gameMode==1){
            score++;
            for(GameListener l: listeners){
                l.changeScore();
            }
        }else {
            score++;
            for(GameListener l: listeners){
                l.changeScore();
            }
            if(map != 5){
                map++;
            }
            success = true;
            stopGame();
        }
    }

    /**
     * 默认的get方法<br>
     * 得到蛇的引用
     * @return snake
     */
    public Snake getSnake() {
        return this.snake;
    }

    /**
     * 默认的get方法<br>
     * 得到食物的引用
     * @return food
     */
    public Food getFood() {
        return this.food;
    }

    /**
     * 默认的get方法<br>
     * 得到地形的引用
     * @return ground
     */
    public Ground getGround() {
        return this.ground;
    }

    /**
     * 默认的get方法<br>
     * 得到画板的引用
     * @return gamePanel
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * 默认的get方法<br>
     * 得到游戏提示信息的引用
     * @return gameInfoLabel
     */
    public JLabel getGameInfoLabel() {
        return gameInfoLabel;
    }

    /**
     * 设置游戏提示信息
     */
    public void setGameInfoLabel(JLabel gameInfoLabel) {
        this.gameInfoLabel = gameInfoLabel;
        this.gameInfoLabel.setSize(OverUtil.view_rows * OverUtil.block_width, 20);
        this.gameInfoLabel.setFont(new Font("楷体", Font.PLAIN, 12));
        gameInfoLabel.setText(this.getNewInfo());
    }

    /**
     * 默认的set方法<br>
     * 设置地图
     */
    public void setMap(int map) {
        this.map = map;
    }

    public int getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public int getTarget() {
        return target;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getGameMode() {
        return gameMode;
    }
}
