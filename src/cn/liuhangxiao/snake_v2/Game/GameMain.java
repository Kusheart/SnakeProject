package cn.liuhangxiao.snake_v2.Game;

import cn.liuhangxiao.snake_v2.Controller.Controller;
import cn.liuhangxiao.snake_v2.Listener.GameListener;
import cn.liuhangxiao.snake_v2.Pojo.Food;
import cn.liuhangxiao.snake_v2.Pojo.Ground;
import cn.liuhangxiao.snake_v2.Pojo.Snake;
import cn.liuhangxiao.snake_v2.Utils.OverUtil;
import cn.liuhangxiao.snake_v2.View.GamePanel;
import cn.liuhangxiao.snake_v2.View.OptionPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * 游戏主界面
 * @author 刘航霄
 */
public class GameMain extends JFrame implements GameListener {
    /**
     * 序列化ID<br>
     * 相当于版本号<br>
     * 当前为1L
     */
    private static final long serialVersionUID = 1L;

    /**
     * 启动游戏<br>
     * 主方法
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e){
            JOptionPane.showConfirmDialog(null,"请升级您的JDK版本至6.0 update 10");
        }
        try {
            GameMain frame = new GameMain(new Controller(new Snake(), new Food(), new Ground(), new GamePanel(), new JLabel()));
            frame.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*============启动游戏的方法===========*/
    /** 静态资源 */
    private final OptionPanel optionPanel;
    private final GamePanel gamePanel;
    private final Snake snake;
    private final Ground ground;
    private final Food food;
    private final Controller controller;

    /**
     * 创建JFrame窗口
     */
    public GameMain(Controller c) throws IOException {
        super();
        this.controller = c;
        //设置窗口名
        this.setTitle("贪吃蛇");
        //设置关闭事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //覆盖对象，默认的空
        this.setLayout(null);
        //设置是否可以变化窗口大小,true为是,false为否
        this.setResizable(false);
        //设置图标
        Image mini_logo = ImageIO.read(Objects.requireNonNull(GameMain.class.getResource("/img/logo.png")));
        this.setIconImage(mini_logo);

        //设置右上角关闭事件(主要防止暂停状态退出导致线程无法关闭)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        int left = 10;
        //实例化画板类
        optionPanel = new OptionPanel();

        /* =======控制器内容配置======*/
        //获取画板
        gamePanel = c.getGamePanel();
        //获取蛇对象
        snake = c.getSnake();
        //获取墙对象
        ground = c.getGround();
        //获取食物对象
        food = c.getFood();
        //获取信息列表
        JLabel infoLabel = c.getGameInfoLabel() == null ? new JLabel() : c.getGameInfoLabel();

        /* ---------- 菜单栏 ------------ */
        //添加菜单栏
        JMenuBar menuBar = new JMenuBar();
        //给窗体添加菜单栏
        this.setJMenuBar(menuBar);

        /* 开始菜单栏 */
        JMenu menu_start = new JMenu("开始");
        //设置字体
        menu_start.setFont(new Font("宋体",Font.PLAIN,14));
        //将开始添加到菜单栏
        menuBar.add(menu_start);

        /*添加“开始”菜单的菜单项*/
        //新游戏
        JMenuItem start_menu_button = new JMenuItem("新游戏(R)");
        start_menu_button.setFont(new Font("宋体",Font.PLAIN,14));
        start_menu_button.addActionListener(e -> {
            controller.stopGame();
            try {
                Thread.sleep(200);//休眠0.5秒防止线程冲突
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            controller.newGame();
            controller.pauseGame();
        });
        menu_start.add(start_menu_button);
        //下一关
        JMenuItem skip_menu_button = new JMenuItem("下一关");
        skip_menu_button.setFont(new Font("宋体",Font.PLAIN,14));
        skip_menu_button.addActionListener(e -> {
           if(controller.getMap() < 5){
               controller.stopGame();
               controller.setMap(controller.getMap()+1);
               try {
                   Thread.sleep(200);//休眠0.5秒防止线程冲突
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
               controller.newGame();
               controller.pauseGame();
           }else {
               controller.stopGame();
               try {
                   Thread.sleep(200);//休眠0.5秒防止线程冲突
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
               controller.newGame();
               controller.pauseGame();
           }
        });
        menu_start.add(skip_menu_button);
        //选择关卡
        JMenu select_menu_button = new JMenu("选择关卡");
        select_menu_button.setFont(new Font("宋体",Font.PLAIN,14));
        menu_start.add(select_menu_button);
            /*关卡单独添加*/
            JMenuItem level_1 = new JMenuItem("太空");
            level_1.setFont(new Font("宋体",Font.PLAIN,14));
            level_1.addActionListener(e -> {
                controller.setSkip(false);
                controller.stopGame();
                controller.setMap(1);
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            });
            select_menu_button.add(level_1);

            JMenuItem level_2 = new JMenuItem("困境");
            level_2.setFont(new Font("宋体",Font.PLAIN,14));
            level_2.addActionListener(e -> {
                controller.setSkip(false);
                controller.stopGame();
                controller.setMap(2);
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            });
            select_menu_button.add(level_2);

            JMenuItem level_3 = new JMenuItem("广场");
            level_3.setFont(new Font("宋体",Font.PLAIN,14));
            level_3.addActionListener(e -> {
                controller.setSkip(false);
                controller.stopGame();
                controller.setMap(3);
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            });
            select_menu_button.add(level_3);

            JMenuItem level_4 = new JMenuItem("穿梭");
            level_4.setFont(new Font("宋体",Font.PLAIN,14));
            level_4.addActionListener(e -> {
                controller.setSkip(false);
                controller.stopGame();
                controller.setMap(4);
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            });
            select_menu_button.add(level_4);

            JMenuItem level_5 = new JMenuItem("混乱");
            level_5.setFont(new Font("宋体",Font.PLAIN,14));
            level_5.addActionListener(e -> {
                controller.setSkip(false);
                controller.stopGame();
                controller.setMap(5);
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            });
            select_menu_button.add(level_5);
        //结束游戏
        JMenuItem stop_menu_button = new JMenuItem("结束游戏");
        stop_menu_button.setFont(new Font("宋体",Font.PLAIN,14));
        stop_menu_button.addActionListener(e -> controller.stopGame());
        menu_start.add(stop_menu_button);
        /*依照顺序执行,加入分隔符*/
        menu_start.addSeparator();
        //退出
        JMenuItem exitMenu = new JMenuItem("退出游戏");
        exitMenu.setFont(new Font("宋体",Font.PLAIN,14));
        exitMenu.addActionListener(e -> {
            System.exit(0);//退出
        });
        menu_start.add(exitMenu);

        /* 创建“设置”项菜单栏 */
        JMenu menu_option = new JMenu("设置");
        menu_option.setFont(new Font("宋体",Font.PLAIN,14));
        menuBar.add(menu_option);

        //背景颜色
        JMenuItem background_menu = new JMenuItem("背景颜色");
        background_menu.setFont(new Font("宋体",Font.PLAIN,14));
        background_menu.addActionListener(e -> {
            Color backgroundColor = JColorChooser.showDialog(GameMain.this,
                    "请选择背景颜色",new Color(0xcfcfcf));
            if(backgroundColor != null){
                gamePanel.setBackgroundColor(backgroundColor);
            }
        });
        menu_option.add(background_menu);

        /*蛇颜色设置*/
        JMenu snake_menu = new JMenu("蛇的颜色");
        snake_menu.setFont(new Font("宋体",Font.PLAIN,14));
        menu_option.add(snake_menu);

        //蛇头颜色
        JMenuItem snake_head_menu = new JMenuItem("蛇头设置");
        snake_head_menu.setFont(new Font("宋体",Font.PLAIN,14));
        snake_head_menu.addActionListener(e -> {
            Color headColor = JColorChooser.showDialog(GameMain.this,
                    "请选择蛇头颜色", new Color(0xff4500));
            if(headColor != null){
                snake.setHeadColor(headColor);
            }
        });
        snake_menu.add(snake_head_menu);

        //蛇身颜色
        JMenuItem snake_body_menu = new JMenuItem("蛇身设置");
        snake_body_menu.setFont(new Font("宋体",Font.PLAIN,14));
        snake_body_menu.addActionListener(e -> {
            Color bodyColor = JColorChooser.showDialog(GameMain.this,
                    "请选择蛇身颜色", Color.DARK_GRAY);
            if(bodyColor != null){
                snake.setBodyColor(bodyColor);
            }
        });
        snake_menu.add(snake_body_menu);

        //食物颜色
        JMenuItem food_menu = new JMenuItem("食物颜色");
        food_menu.setFont(new Font("宋体",Font.PLAIN,14));
        food_menu.addActionListener(e -> {
            Color foodColor = JColorChooser.showDialog(GameMain.this,
                    "清选择食物颜色", Color.DARK_GRAY);
            if(foodColor != null){
                food.setFoodColor(foodColor);
            }
        });
        menu_option.add(food_menu);

        /*依照执行顺序，加入分割线*/
        menu_option.addSeparator();

        //网格线颜色
        JMenuItem grid_color_menu = new JMenuItem("网格线颜色");
        grid_color_menu.setFont(new Font("宋体",Font.PLAIN,14));
        grid_color_menu.addActionListener(e -> {
            Color gridColor = JColorChooser.showDialog(
                    GameMain.this, "请选择网格的颜色", Color.LIGHT_GRAY);
            if(gridColor != null){
                ground.setGridColor(gridColor);
            }
        });
        //默认禁用
        grid_color_menu.setEnabled(false);

        //网格线可选项
        JCheckBoxMenuItem grid_menu = new JCheckBoxMenuItem("绘制网格线");
        grid_menu.setFont(new Font("宋体",Font.PLAIN,14));
        grid_menu.addChangeListener(e -> {
            grid_color_menu.setEnabled(grid_menu.isSelected());
            ground.setDrawGrid(grid_menu.isSelected());//绘制默认网格
        });
        menu_option.add(grid_menu);
        //调整网格线按钮顺序
        menu_option.add(grid_color_menu);

        menu_option.addSeparator();
        //恢复默认设置
        JMenuItem reset_menu = new JMenuItem("恢复默认");
        reset_menu.setFont(new Font("宋体",Font.PLAIN,14));
        reset_menu.addActionListener(e -> {
            gamePanel.setBackgroundColor(GamePanel.Default_background_color);
            grid_menu.setState(false);
            grid_color_menu.setEnabled(false);
            ground.setGridColor(Ground.Default_grid_color);
            snake.setHeadColor(Snake.Default_head_color);
            snake.setBodyColor(Snake.Default_body_color);
            food.setFoodColor(Food.Default_head_color);
        });
        menu_option.add(reset_menu);

        /* 创建“游戏模式"项菜单栏 */
        JMenu menu_mode = new JMenu("游戏模式");
        menu_mode.setFont(new Font("宋体",Font.PLAIN,14));
        menuBar.add(menu_mode);

        /* 游戏模式切换 */
        //创建游戏模式按钮
        JRadioButtonMenuItem levelup = new JRadioButtonMenuItem("闯关模式");
        levelup.setFont(new Font("宋体",Font.PLAIN,14));
        levelup.setSelected(true);

        JRadioButtonMenuItem hentai = new JRadioButtonMenuItem("无尽模式");
        hentai.setFont(new Font("宋体",Font.PLAIN,14));
        hentai.setSelected(false);
        //添加事件
        levelup.addActionListener(e -> {
            if(levelup.isSelected()){
                controller.setGameMode(0);
                hentai.setSelected(false);
                controller.setSkip(false);
                controller.stopGame();
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            }else {
                hentai.setSelected(true);
                controller.setGameMode(1);
                controller.setSkip(false);
                controller.stopGame();
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            }
        });
        hentai.addActionListener(e -> {
            if(hentai.isSelected()){
                controller.setGameMode(1);
                levelup.setSelected(false);
                controller.setSkip(false);
                controller.stopGame();
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            }else {
                levelup.setSelected(true);
                controller.setGameMode(0);
                controller.setSkip(false);
                controller.stopGame();
                try {
                    Thread.sleep(200);//休眠0.5秒防止线程冲突
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.newGame();
                controller.pauseGame();
            }
        });

        menu_mode.add(levelup);
        menu_mode.add(hentai);

        /*  窗口失去焦点触发 */
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //暂停游戏
                controller.pauseGame();
                if(optionPanel.getPauseButton().isEnabled()){
                    optionPanel.getPauseButton().setText("继续游戏");
                }
            }
        });
        /* 画板失去焦点触发 */
        gamePanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(controller.isPlaying()) {
                    controller.pauseGame();
                    if (optionPanel.getPauseButton().isEnabled()) {
                        optionPanel.getPauseButton().setText("继续游戏");
                    }
                }
            }
        });

        //开始新游戏的按钮
        optionPanel.getNewGameButton().addActionListener(e -> {
            if(controller.isPlaying()){
                return;
            }
            controller.newGame();
            controller.pauseGame();
        });
        //结束游戏的按钮
        optionPanel.getStopGameButton().addActionListener(e -> controller.stopGame());
        //暂停和结束游戏按钮的初始状态：禁用
        optionPanel.getPauseButton().setEnabled(false);
        optionPanel.getStopGameButton().setEnabled(false);

        //暂停/继续游戏的按钮
        optionPanel.getPauseButton().addActionListener(e -> {
            if (controller.isPausingGame()) {
                controller.continueGame();
            } else {
                controller.pauseGame();
            }
            if (controller.isPausingGame()) {
                optionPanel.getPauseButton().setText("继续游戏");
            } else {
                optionPanel.getPauseButton().setText("暂停游戏");
            }
        });

        //游戏状态框设置
        optionPanel.getGameStartText().setText("R 开始游戏");
        optionPanel.getGameStartText().setForeground(OverUtil.Info_color);

        /* ============画板和信息框设置============== */
        infoLabel.setBounds(10,0, infoLabel.getSize().width - 10, infoLabel.getSize().height);
        //设置游戏主窗体边框
        gamePanel.setBounds(0, infoLabel.getSize().height, gamePanel.getSize().width, gamePanel.getSize().height);

        /* ============按钮面板 ==================== */
        //初始化按钮面板
        JPanel subPanel = new JPanel();
        subPanel.setLayout(null);
        //设置边框
        subPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //设置聚焦状态
        subPanel.setFocusable(false);

        //设置大小
        subPanel.setSize(gamePanel.getSize().width + 1, infoLabel.getSize().height + gamePanel.getSize().height + 1);
        //设置边框大小
        subPanel.setBounds(left, 5, subPanel.getSize().width,subPanel.getSize().height);
        //添加信息框和游戏框
        subPanel.add(infoLabel);
        subPanel.add(gamePanel);
        optionPanel.setBounds(left, subPanel.getSize().height +10, optionPanel.getSize().width, optionPanel.getSize().height);

        /* ==========说明=============== */
        //初始化组件
        JPanel infoPanel = new JPanel();
        //定义边框
        infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        infoPanel.setLayout(null);

        //设置边框
        infoPanel.setBounds(left + optionPanel.getSize().width + 5, subPanel.getSize().height + 10,
                gamePanel.getSize().width - optionPanel.getSize().width - 5 + 1, optionPanel.getSize().height);

        /* 信息标题框 */
        //初始化组件
        final JLabel infoTitleLabel = new JLabel();
        //设置字体
        infoTitleLabel.setFont(new Font("楷体", Font.PLAIN, 14));
        infoTitleLabel.setText(OverUtil.Title_label_text);
        infoTitleLabel.setBounds(10,5,infoPanel.getSize().width - 10, 20);
        /* 信息内容 */
        final JTextArea infoTextArea = new JTextArea();
        infoTextArea.setFont(new Font("楷体", Font.PLAIN, 16));
        infoTextArea.setText(OverUtil.Info_label_text);
        //聚焦状态
        infoTextArea.setFocusable(false);
        infoTextArea.setBackground(this.getBackground());
        infoTextArea.setBounds(10, 25, infoPanel.getSize().width - 20,
                infoPanel.getSize().height - 40);

        /* 添加名片背景 */
        ImageIcon name_loge = new ImageIcon(Objects.requireNonNull(GameMain.class.getResource("/img/name_logo.png")));
        final JLabel logo = new JLabel(name_loge);
        logo.setBounds(12,27,infoPanel.getSize().width - 20,infoPanel.getSize().height - 40);
        infoPanel.add(logo);

        infoPanel.add(infoTitleLabel);
        infoPanel.add(infoTextArea);

        /* 设置窗口大小 */
        this.setSize(subPanel.getSize().width > optionPanel.getSize().width ? gamePanel
                .getSize().width
                + 2 * left + 8
                : optionPanel.getSize().width + 2 * left + 8,
                subPanel.getSize().height + 20  /* 边框 */
                + optionPanel.getSize().height + 50);

        /* 让窗口居中 */
        this.setLocation(this.getToolkit().getScreenSize().width / 2
                - this.getWidth() / 2, this.getToolkit().getScreenSize().height / 2
                - this.getHeight() / 2);

        /* 添加监听器 */
        gamePanel.addKeyListener(controller);
        this.addKeyListener(controller);
        controller.addGameListener(this);

        //添加游戏面板
        this.getContentPane().add(subPanel);
        //添加控制面板
        this.getContentPane().add(optionPanel);
        //添加信息面板
        this.getContentPane().add(infoPanel);
    }

    /**
     * 实现的接口<br>
     * 游戏结束
     */
    @Override
    public void gameOver() {
        optionPanel.getPauseButton().setEnabled(false);
        optionPanel.getStopGameButton().setEnabled(false);
        optionPanel.getNewGameButton().setEnabled(true);
        optionPanel.getPauseButton().setText("暂停/继续");
        if(controller.isSuccess()){
            optionPanel.getGameStartText().setForeground(OverUtil.Success_color);
            optionPanel.getGameStartText().setText("成功过关");
        }else {
            optionPanel.getGameStartText().setText("游戏结束");
            optionPanel.getGameStartText().setForeground(OverUtil.Danger_color);
        }
    }

    /**
     * 实现的接口<br>
     * 开始游戏
     */
    @Override
    public void gameStart() {
        optionPanel.getPauseButton().setEnabled(true);
        optionPanel.getNewGameButton().setEnabled(false);
        optionPanel.getStopGameButton().setEnabled(true);
    }

    /**
     * 实现的接口<br>
     * 游戏继续
     */
    @Override
    public void gameContinue() {
        optionPanel.getGameStartText().setText("游戏中...");
        optionPanel.getGameStartText().setForeground(OverUtil.State_color);
        optionPanel.getPauseButton().setText("暂停游戏");
    }

    /**
     * 实现的接口<br>
     * 暂停游戏
     */
    @Override
    public void gamePause() {
        optionPanel.getGameStartText().setForeground(OverUtil.Info_color);
        optionPanel.getGameStartText().setText("游戏已暂停");
        optionPanel.getPauseButton().setText("继续游戏");
    }

    /**
     * 实现的接口<br>
     * 改变分数
     */
    @Override
    public void changeScore(){
        optionPanel.getGameScoreText().setText(controller.getScore()+"分");
        if(controller.getGameMode()==0){
            optionPanel.getGameScoreEndText().setText(controller.getTarget()+"分");
        }else {
            optionPanel.getGameScoreEndText().setText("无尽");
        }
    }
}
