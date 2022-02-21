package cn.liuhangxiao.snake_v2.Utils;

import java.awt.*;
import java.util.Properties;

/**
 * 全局工具类<br>
 * 此类中存放其他类用到的一些常量<br>
 */
public class OverUtil {
    //保证线程安全
    private static Properties properties = new Properties();

    /**
     * 定义一个格子的宽度，单位：像素<br>
     * 默认值：30
     */
    public static final int block_width = 30;

    /**
     * 定义一个格子的宽度，单位：像素<br>
     * 默认值：30
     */
    public static final int block_height = 30;

    /**
     * 界面的宽度(界面宽多少格子) 单位：格<br>
     * 默认30
     */
    public static final int view_rows = 30;

    /**
     * 界面的高度(界面高多少格子) 单位：格<br>
     * 默认20
     */
    public static final int view_cols = 20;

    /**
     * 蛇的初始长度<br>
     * 单位：格<br>
     * 默认：3
     */
    public static final int snake_length = 3;

    /**
     * 蛇的速度基本值<br>
     * 蛇的速度由这个值控制<br>
     * 相当于每秒走多少格<br>
     * 默认：5(单位:格/秒)
     */
    public static final int speed_main = 5;

    /**
     * 蛇的初始速度<br>
     * 该值表示多少毫秒走一格<br>
     * 默认：200(单位：毫秒/格)
     */
    public static final int speed = 1000/speed_main;

    public static final String Title_label_text = "按键说明: ";

    public static final String Info_label_text = " 方向控制  : 方向键 && WSAD" +
                                               "\n 暂停&&继续: 回车 " +
                                               "\n 移速 增加 : E && PUGA_UP " +
                                               "\n 移速 减少 : Q && PUGA_DOWN"+
                                               "\n 开始新游戏: R"+
                                               "\n 下一关 ： R";

    /**
     * 默认的提示信息颜色
     */
    public static final Color Info_color = new Color(0xF7BA0B);

    /**
     * 默认的警告信息颜色
     */
    public static final Color Danger_color = new Color(0xff0000);

    /**
     * 默认的显示信息颜色
     */
    public static final Color Success_color = new Color(0x7fff00);

    /**
     * 默认的状态信息颜色
     */
    public static final Color State_color = new Color(0x00bfff);

    /**
     * 通关需要的分数<br>
     * 默认:10
     */
    public static final int target = 10;

    /**
     * 游戏模式<br>
     * 闯关模式：0 <br>
     * 无尽模式：1 <br>
     * 默认：闯关模式
     */
    public static final int gameMode = 0;
}
