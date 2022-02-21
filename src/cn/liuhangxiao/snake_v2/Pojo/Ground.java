package cn.liuhangxiao.snake_v2.Pojo;

import cn.liuhangxiao.snake_v2.Utils.OverUtil;

import java.awt.*;
import java.util.Random;

/**
 * 实体类——墙壁
 */
public class Ground {
    /** 存放石头位置的二维数组 */
    private boolean rocks[][] = new boolean[OverUtil.view_rows][OverUtil.view_cols];

    /** 存放一格非石头的随机坐标 */
    private Point freePoint = new Point();

    /** 定义蛇类 */
    private Snake snake;

    //默认的石头颜色
    //石头颜色
    /** 石头的颜色 */
    public static final Color Default_rock_color = new Color(0x666666);

    private Color rockColor = Default_rock_color;


    /** 网格颜色 */
    public static final Color Default_grid_color = Color.LIGHT_GRAY;
    private Color gridColor = Default_grid_color;

    /** 随机方法 */
    private Random random = new Random();

    /** 是否画网格开关 */
    private boolean drawGrid = false;

    /**
     * 默认构造器，调用init()方法 和 generateRocks() 方法
     */
    public Ground(){
        init();//封装的方法
    }

    public void init(){
        Clear();
        CreateRocks();
    }
    /**
     * 初始化地面<br>
     * 清空地面的石头
     */
    public void Clear(){
        for(int x = 0; x < OverUtil.view_rows; x++){
            for(int y = 0; y < OverUtil.view_cols; y++){
                //循环将石头数组清空
                rocks[x][y] = false;
            }
        }
    }

    /* ==================关卡设计================= */
    /**
     * 产生石头<br>
     * 覆盖这个方法可以改变石头的位置<br>
     * 默认关卡-太空
     */
    public void CreateRocks(){
        for(int x=0;x<8;x++){
            int a = random.nextInt(OverUtil.view_rows);
            int b = random.nextInt(OverUtil.view_cols);
            rocks[a][b]=true;
        }
    }

    /**
     * 关卡-困境
     */
    public void CreateRocks_1(){
        for(int x = 0; x < OverUtil.view_rows; x++){
            rocks[x][0] = rocks[x][OverUtil.view_cols - 1] = true;
        }
        for(int y = 0; y < OverUtil.view_cols; y++){
            rocks[0][y] = rocks[OverUtil.view_rows - 1][y] = true;
        }
    }

    /**
     * 关卡-广场
     */
    public void CreateRocks_2(){
        //增加边墙
        for(int y = 0; y < 6; y++){
            rocks[0][y] = true;
            rocks[OverUtil.view_rows - 1][y] = true;
            rocks[0][OverUtil.view_cols - 1 - y] = true;
            rocks[OverUtil.view_rows - 1][OverUtil.view_cols - 1 - y] = true;
            rocks[y+9][4] = true;
            rocks[y+9][15] = true;
            rocks[y+15][4] = true;
            rocks[y+15][15] = true;
        }
        //增加两条短竖墙
        for(int y = 6; y < OverUtil.view_cols - 6; y++){
            rocks[6][y] = true;
            rocks[OverUtil.view_rows - 7][y] = true;
        }
    }

    /**
     * 关卡-穿梭
     */
    public void CreateRocks_3(){
        for(int y = 0; y<OverUtil.view_cols;y++){
            rocks[12][y] = true;
        }
        for(int x = 0; x<OverUtil.view_rows;x++){
            rocks[x][12] = true;
        }
    }

    /**
     * 关卡-混乱
     */
    public void CreateRocks_4(){
        //随机产生80个石头
        for(int x=0;x<80;x++){
            int a = random.nextInt(OverUtil.view_rows);
            int b = random.nextInt(OverUtil.view_cols);
            rocks[a][b]=true;
        }
    }

    /**
     * 清除蛇出生点周围的石头<br>
     * 出生点保护，放置死档
     */
    public void DeleteRocks_spawnpoint(){
        for(int i=-2;i<2;i++){
            int a = OverUtil.view_rows/2 - OverUtil.snake_length;
            int b = OverUtil.view_cols/2;
            for(int j=0;j<7;j++){
                rocks[a+j][b+i]=false;
            }
        }
    }

    /**
     * 添加一块石头到指定格子坐标
     * @param x
     * 格子坐标 x
     * @param y
     * 格子坐标 y
     */
    public void addRock(int x,int y){
        rocks[x][y] = true;
    }

    /**
     * 判断蛇是否碰到了石头
     */
    public boolean isSnakeEatRock(Snake snake){
        return rocks[snake.getHead().x][snake.getHead().y];
    }

    /**
     * 随机生成一个不是石头的坐标，用于生成食物
     * @return point
     */
    public Point getFreePoint(){
        do{
            freePoint.x = random.nextInt(OverUtil.view_rows);
            freePoint.y = random.nextInt(OverUtil.view_cols);
        } while (rocks[freePoint.x][freePoint.y]);
        return freePoint;
    }

    /**
     * 设置石头颜色
     */
    public void setRockColor(Color rockColor){
        this.rockColor = rockColor;
    }

    /**
     * 绘制石头，将调用drawRock方法 和 drawGrid方法
     */
    public void drawRock(Graphics g){
        for(int x = 0; x < OverUtil.view_rows; x++){
            for(int y = 0; y < OverUtil.view_cols; y++){
                /* 画石头 */
                if(rocks[x][y]){
                    g.setColor(rockColor);
                    drawRock(g, x * OverUtil.block_width, y * OverUtil.block_height,
                            OverUtil.block_width, OverUtil.block_height);
                }else if(drawGrid){
                    /* 开启网格时，绘制网格 */
                    g.setColor(gridColor);
                    drawGridding(g, x*OverUtil.block_width,
                            y*OverUtil.block_height,
                            OverUtil.block_width,OverUtil.block_height);
                }
            }
        }
    }

    /**
     * 画一块石头, 可以覆盖这个方法改变石头的显示
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
    public void drawRock(Graphics g, int x, int y, int width, int height) {
        //绘制色块
        g.fill3DRect(x, y, width, height, true);
    }

    /**
     * 画网格, 可以覆盖这个方法改变网格的显示
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
    public void drawGridding(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    /**
     * 网格颜色默认的set方法<br>
     * 设置网格颜色
     */
    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    /**
     * 是否画网格,默认的set方法<br>
     * 设置是否画网格
     */
    public void setDrawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
    }
}
