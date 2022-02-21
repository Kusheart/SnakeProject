package cn.liuhangxiao.snake_v2.View;

import cn.liuhangxiao.snake_v2.Utils.OverUtil;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Objects;

/**
 * 游戏设置界面
 */
public class OptionPanel extends JPanel {
    /*
     *定义静态变量
     */
//    private static final long serialVersionUID = 1L;
    //按钮——开始游戏
    private final JButton newGameButton = new JButton();
    //按钮——结束游戏
    private final JButton stopGameButton = new JButton();
    //按钮——暂停游戏
    private final JButton pauseButton = new JButton();
    //文本框——游戏状态
    private final JTextField gameStartText = new JTextField();
    //文本框——分数
    private final JTextField gameScoreText = new JTextField();
    //文本框——目标
    private final JTextField gameScoreEndText = new JTextField();
    //logo图标
    private ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(OptionPanel.class
            .getResource("/img/school_logo.png")));
    //初始化JFrame
    private JFrame frame;

    /**
     * 创建画板
     */
    public OptionPanel() {
        super();
        //设置画板大小
        setSize(450, 185);
        //配置布局管理器，默认为null
        setLayout(null);
        //创建渐进式边框，边框类别为渐进式
        setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //聚焦状态
        setFocusable(false);

        /*游戏状态的文本框*/
        gameStartText.setBounds(10, 10, 286, 80);
        gameStartText.setHorizontalAlignment(JTextField.CENTER);
        gameStartText.setFont(new Font("黑体", Font.PLAIN, 40));
        gameStartText.setBackground(Color.black);
        gameStartText.setFocusable(false);//聚焦状态
        add(gameStartText);

        JLabel ScoreText = new JLabel();
        ScoreText.setFont(new Font("楷体", Font.PLAIN, 14));
        ScoreText.setBounds(10, 70, 134, 60);
        ScoreText.setText("当前分数:");
        add(ScoreText);

        /*游戏分数的文本框*/
        gameScoreText.setBounds(10, 110, 138, 60);
        gameScoreText.setHorizontalAlignment(JTextField.CENTER);
        gameScoreText.setFont(new Font("黑体", Font.PLAIN, 40));
        gameScoreText.setBackground(Color.black);
        gameScoreText.setForeground(OverUtil.Success_color);
        gameScoreText.setFocusable(false);
        add(gameScoreText);

        JLabel scoreEndText = new JLabel();
        scoreEndText.setFont(new Font("楷体", Font.PLAIN, 14));
        scoreEndText.setBounds(158, 70, 138, 60);
        scoreEndText.setText("目标分数:");
        add(scoreEndText);

        /*游戏目标分数的文本框*/
        gameScoreEndText.setBounds(158, 110, 138, 60);
        gameScoreEndText.setHorizontalAlignment(JTextField.CENTER);
        gameScoreEndText.setFont(new Font("黑体", Font.PLAIN, 40));
        gameScoreEndText.setBackground(Color.black);
        gameScoreEndText.setForeground(OverUtil.Danger_color);
        gameScoreEndText.setFocusable(false);
        add(gameScoreEndText);

        /*创建竖线，将游戏设置与游戏进程按钮分割开*/
        final JSeparator separator_2 = new JSeparator();
        //设置分割线方向
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(302, 10, 140, 165);
        add(separator_2);

        /*
         * 创建分区——游戏进程控制
         * 包括开始、结束、暂停游戏
         */
        final JSeparator separator_start = new JSeparator();
        separator_start.setBounds(10, 70, 125, 95);
        separator_2.add(separator_start);

        /*结束游戏按钮设置*/
        stopGameButton.setText("结束游戏");
        stopGameButton.setBounds(11, 10, 101, 23);
        stopGameButton.setFont(new Font("楷体", Font.PLAIN, 12));
        stopGameButton.setFocusable(false);
        separator_start.add(stopGameButton);

        /*暂停&继续按钮设置*/
        pauseButton.setText("暂停/继续");
        pauseButton.setBounds(10, 40, 101, 23);
        pauseButton.setFont(new Font("楷体", Font.PLAIN, 12));
        pauseButton.setFocusable(false);
        separator_start.add(pauseButton);

        /*开始游戏按钮设置*/
        newGameButton.setText("开始新游戏");
        newGameButton.setBounds(11, 70, 101, 23);
        newGameButton.setFont(new Font("楷体", Font.PLAIN, 12));
        newGameButton.setFocusable(false);
        separator_start.add(newGameButton);

        /*设置游戏LOGO*/
        final JLabel label_logo = new JLabel(logoIcon);
        label_logo.setBounds(10, 0, 125, 70);
        separator_2.add(label_logo);
    }

    /**
     * 开始按钮<br>
     * 默认的get方法
     */
    public JButton getNewGameButton() {
        return newGameButton;
    }

    /**
     * 结束按钮<br>
     * 默认的get方法
     */
    public JButton getStopGameButton() {
        return stopGameButton;
    }

    /**
     * 暂停按钮<br>
     * 默认的get方法
     */
    public JButton getPauseButton() {
        return pauseButton;
    }

    /**
     * 游戏状态<br>
     * 默认的get方法
     */
    public JTextField getGameStartText() {
        return gameStartText;
    }

    /**
     * 游戏分数<br>
     * 默认的get方法
     */
    public JTextField getGameScoreText() {
        return gameScoreText;
    }

    /**
     * 目标分数<br>
     * 默认的get方法
     */
    public JTextField getGameScoreEndText() {
        return gameScoreEndText;
    }
}
