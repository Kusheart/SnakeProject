package cn.liuhangxiao.snake_v2.View;

import cn.liuhangxiao.snake_v2.Pojo.Food;
import cn.liuhangxiao.snake_v2.Pojo.Ground;
import cn.liuhangxiao.snake_v2.Pojo.Snake;
import cn.liuhangxiao.snake_v2.Utils.OverUtil;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * 游戏显示界面
 */
public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    //缓存图
    private Image over_image;
    //画笔
    private Graphics over_graphics;
    //默认背景色
    public static final Color Default_background_color = new Color(0xcfcfcf);
    //背景颜色
    private Color backgroundColor = Default_background_color;

    /**
     * 默认的构造器<br>
     * 设置大小和布局
     */
    public GamePanel() {
        /* 设置大小和布局 */
        this.setSize(OverUtil.view_rows * OverUtil.block_width, OverUtil.view_cols
                * OverUtil.block_height);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setFocusable(true);
    }

    /**
     * 重新显示 Ground, shape
     * @param ground
     * @param snake
     * @param food
     */
    public synchronized void redisplay(Ground ground, Snake snake, Food food) {
        /* 重新显示 */
        if(over_graphics == null){
            over_image = createImage(getSize().width, getSize().height);
            if(over_image != null){
                over_graphics = over_image.getGraphics();
            }
        }
        if(over_graphics != null){
            over_graphics.setColor(backgroundColor);
            over_graphics.fillRect(0,0, OverUtil.view_rows * OverUtil.block_width,OverUtil.view_cols * OverUtil.block_height);
            if(ground != null){//判断是否有石头，无则绘制石头
                ground.drawRock(over_graphics);
            }
            //绘制蛇
            snake.drawSnake(over_graphics);
            if(food != null){
                food.drawFood(over_graphics);
            }
            //绘制画板
            this.paint(this.getGraphics());
        }
    }

    public void paint(Graphics graphics){//双缓存绘制图片
        graphics.drawImage(over_image,0,0,this);
    }

    /**
     * 默认的get方法<br>
     * 得到当前的背景颜色
     * @return
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * 默认的set方法<br>
     * 设置当前的背景颜色
     * @param backgroundColor
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
