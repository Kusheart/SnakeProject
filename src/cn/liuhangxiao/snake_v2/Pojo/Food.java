package cn.liuhangxiao.snake_v2.Pojo;

import cn.liuhangxiao.snake_v2.Utils.OverUtil;

import java.awt.*;
import java.util.Random;

/**
 * 实体类——食物<br>
 * 食物有x,y坐标 和 颜色 等属性<br>
 * 通过覆盖drawFood方法 改变食物的显示
 */
public class Food extends Point {

    /**
     * 版本号
     */
    private static final long serialVersionUID = 1L;

    /** 食物默认颜色 */
    public static final Color Default_head_color = new Color(0xcc0033);
    private Color foodColor = Default_head_color;

    /** 随机的方法 */
    private Random random = new Random();

    /**
     * 默认的构造器
     */
    public Food(){
        super();
    }

    /**
     * 产生一个随机的食物坐标
     */
    public Point getNew(){
        Point p = new Point();
        p.x = random.nextInt(OverUtil.view_rows);
        p.y = random.nextInt(OverUtil.view_cols);
        return p;
    }

    /**
     * 初始化坐标 和 指定坐标相同的构造器
     */
    public Food(Point point){
        super(point);
    }

    /**
     * 蛇是否吃到了食物
     *
     */
    public boolean isSnakeEatFood(Snake snake){
        return this.equals(snake.getHead());
    }

    /**
     * 绘制食物
     */
    public void drawFood(Graphics graphics){
        graphics.setColor(foodColor);
        drawFood(graphics,x * OverUtil.block_width, y * OverUtil.block_height, OverUtil.block_width, OverUtil.block_height);

    }

    /**
     * 画食物, 可以覆盖这个方法改变食物的显示
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
    public void drawFood(Graphics g, int x, int y, int width, int height) {
        //绘制色块
        g.fill3DRect(x, y, width, height, true);
    }

    public void setFoodColor(Color foodColor) {
        this.foodColor = foodColor;
    }
}
