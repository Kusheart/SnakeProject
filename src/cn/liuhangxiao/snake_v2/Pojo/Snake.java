package cn.liuhangxiao.snake_v2.Pojo;

import cn.liuhangxiao.snake_v2.Listener.SnakeListener;
import cn.liuhangxiao.snake_v2.Utils.OverUtil;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 实体类——蛇
 * @author 刘航霄
 */
public class Snake {
    /**
     * 方向——上
     */
    public static final int up = 1;

    /**
     * 方向——下
     */
    public static final int down = -1;

    /**
     * 方向——左
     */
    public static final int left = 2;

    /**
     * 方向——右
     */
    public static final int right = -2;

    /**
     * 蛇的列表
     */
    private final LinkedList<Point> body = new LinkedList<Point>();

    /**
     * 上一次的移动方向
     */
    private int old_Direction;

    /**
     * 下一个方向(有效方向)
     */
    private int new_Direction;

    /**
     * 临时存放蛇头的坐标
     */
    private Point snake_head;

    /**
     * 临时存放蛇尾的坐标
     */
    private Point snake_tail;

    /**
     * 移动速度
     */
    private int speed;
    private int speed_main;

    /**
     * 蛇的状态(主要判断是否活着)
     */
    private boolean live;

    /**
     * 是否暂停
     */
    private boolean pause;

    /**
     * 设置蛇的监听器
     */
    private Set<SnakeListener> listeners = new HashSet<SnakeListener>();

    /**
     * 设置蛇头的颜色
     */
    /* 定义默认的蛇头颜色 */
    public static final Color Default_head_color = new Color(0xcc0033);
    /* 蛇头的颜色 */
    private Color headColor = Default_head_color;

    /**
     * 设置蛇身体的颜色
     */
    /* 设置蛇身体的默认颜色 */
    public static final Color Default_body_color = new Color(0xcc33aa);
    /* 设置蛇身体的颜色 */
    private Color bodyColor = Default_body_color;

    /**
     * 移动一步的方法<br>
     * 会忽略相反的方向
     */
    public void move(){
        //忽略相反方向
        if(old_Direction + new_Direction !=0){
            old_Direction = new_Direction;
        }
        /* 把蛇尾巴拿出来重新设置坐标作为新蛇头 */
        /* getLocation 将返回一个新的Point */
        /* tail把尾巴坐标保存下来, 吃到食物时再加上 */
        snake_tail = (snake_head = takeTail()).getLocation();
        //根据蛇头的坐标再 上下左右移动
        snake_head.setLocation(getHead());
        //根据方向让蛇移动
        switch (old_Direction){
            case up:
                snake_head.y--;
                //触边则从另一边出现
                if(snake_head.y < 0){
                    snake_head.y = OverUtil.view_cols - 1;
                }
                break;
            case down:
                snake_head.y++;
                //触边则从另一边出现
                if(snake_head.y > OverUtil.view_cols - 1){
                    snake_head.y = 0;
                }
                break;
            case left:
                snake_head.x--;
                //触边则从另一边出现
                if(snake_head.x < 0){
                    snake_head.x = OverUtil.view_rows - 1;
                }
                break;
            case right:
                snake_head.x++;
                //触边则从另一边出现
                if(snake_head.x > OverUtil.view_rows - 1){
                    snake_head.x = 0;
                }
                break;
            default:
                break;
        }
        //将头添加到蛇
        body.addFirst(snake_head);
    }

    /**
     * 内部类<br>
     * 驱动蛇定时移动
     */
    private class SnakeDriver implements Runnable{
        @Override
        public void run(){
            while (live){//优先判断蛇的存活状态
                if(!pause){//判断游戏是否处于暂停
                    move();
                    /* 触发控制器，改变蛇的状态 */
                    for(SnakeListener listener : listeners){
                        listener.snakeMoved();
                    }
                }
                try {
                    Thread.sleep(speed);//利用线程休眠控制速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 吃食物的方法<br>
     * 在尾巴上增加一个节点
     */
    public void eatFood(){
        //将上一次移动拿掉的节点加上
        body.addLast(snake_tail.getLocation());
        //触发蛇的监听的 吃食物事件
        for(SnakeListener listener: listeners){
            listener.snakeEatFood();
        }
    }

    /**
     * 改变方向
     */
    public void changeDirection(int direction){
        this.new_Direction = direction;
    }

    /**
     * 得到蛇头节点
     */
    public Point getHead(){
        //自定义哪个是蛇头(这里设置第一个)
        return body.getFirst();
    }

    /**
     * 拿掉蛇尾巴节点
     */
    public Point takeTail(){
        //去掉蛇尾巴
        return body.removeLast();
    }

    /**
     * 得到蛇的长度
     */
    public int getLength(){
        return body.size();
    }

    /**
     * 让蛇开始运动<br>
     * 开启一个新的线程
     */
    public void begin(){
        new Thread(new SnakeDriver()).start();
    }

    /**
     * 让蛇复活，并开始运动<br>
     * 调用begin()方法
     */
    public void resetNew(){
        init();
        begin();
    }

    public void init(){
        body.clear();//重置蛇
        //初始化位置在中间
        int x = OverUtil.view_rows/2 - OverUtil.snake_length;
        int y = OverUtil.view_cols/2;
        //循环以加入蛇列表
        for(int i = 0;i < OverUtil.snake_length;i++){
            this.body.addFirst(new Point(x++, y));
        }
        //设置默认方向
        old_Direction = new_Direction = right;
        //初始化速度
        speed = OverUtil.speed;
        speed_main = OverUtil.speed_main;
        //初始化生命和暂停状态
        live = true;
        pause = false;
    }

    /**
     * 是否吃到自己的身体<br>
     *
     * @return 蛇头的坐标是否和自己的身体的某一个坐标重合
     */
    public boolean isEatBody(){
        //要把蛇头排除,body.get(0)是蛇头
        for(int i = 1; i< body.size();i++){
            if(getHead().equals(body.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * 绘制蛇<br>
     * 调用绘制头和绘制身体方法
     */
    public void drawSnake(Graphics graphics){
        for(Point point:body){
            //绘制身体
            graphics.setColor(bodyColor);
            drawBody(graphics, point.x * OverUtil.block_width, point.y * OverUtil.block_height,
                    OverUtil.block_width,OverUtil.block_height);
        }
        //绘制蛇头
        graphics.setColor(headColor);
        drawHead(graphics,getHead().x * OverUtil.block_width,getHead().y * OverUtil.block_height,
                OverUtil.block_width, OverUtil.block_height);
    }

    /**
     * 画蛇头, 可以覆盖这个方法改变蛇头的显示
     *
     * @param x
     *            像素坐标 x
     * @param y
     *            像素坐标 y
     * @param width
     *            宽度(单位:像素)
     * @param height
     *            高度(单位:像素)
     */
    public void drawHead(Graphics g, int x, int y, int width, int height) {
        g.fill3DRect(x, y, width, height, true);
    }

    /**
     * 画蛇的一节身体, 可以覆盖这个方法改变蛇的身体节点的显示
     *
     * @param x
     *            像素坐标 x
     * @param y
     *            像素坐标 y
     * @param width
     *            宽度(单位:像素)
     * @param height
     *            高度(单位:像素)
     */
    public void drawBody(Graphics g, int x, int y, int width, int height) {
        g.fill3DRect(x, y, width, height, true);
    }

    /**
     * 设置蛇头颜色<br>
     * 默认的set方法
     */
    public void setHeadColor(Color headColor) {
        this.headColor = headColor;
    }

    /**
     * 设置身体的颜色<br>
     * 默认的set方法
     */
    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }

    /**
     * 添加监听器
     */
    public synchronized void addSnakeListener(SnakeListener listener){
        if(listener == null){
            return;
        }
        this.listeners.add(listener);
    }

    /**
     * 移除监听器
     *
     */
    public synchronized void removeSnakeListener(SnakeListener listener) {
        if (listener == null) {
            return;
        }
        this.listeners.remove(listener);
    }

    /**
     * 加速, 幅度为OverUtils中设置的speed_step<br>
     * 在有效速度范围之内(增加后速度大于0毫秒/格)
     */
    public void speedUp(){
        if(speed > 100){
            speed_main++;
            speed = 1000/speed_main;
        }else if(speed > 75){
            speed_main += 5;
            speed = 1000/speed_main;
        }
    }

    /**
     * 减速,幅度为OverUtils中设置的speed_step<br>
     * 避免速度过慢(减少后速度不低于1000毫秒/格)
     */
    public void speedDown(){
        if(speed < 1000){
            if(speed>99){
                speed_main--;
            }else{
                speed_main -= 5;
            }
            speed = 1000/speed_main;
        }
    }

    /**
     * 得到蛇的移速<br>
     * 默认的get方法
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * 获取蛇的状态
     *
     * @return
     */
    public boolean isLive(){
        return live;
    }

    /**
     * 定义蛇死亡
     */
    public void dead(){
        this.live = false;
    }

    /**
     * 是否是暂停状态
     */
    public boolean isPause(){
        return pause;
    }

    /**
     * 设置暂停状态
     * @param pause
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * 更改暂停状态<br>
     * 若是暂停状态，则继续移动<br>
     * 若在移动，则暂停
     */
    public void changePause(){
        pause = !pause;
    }

}
