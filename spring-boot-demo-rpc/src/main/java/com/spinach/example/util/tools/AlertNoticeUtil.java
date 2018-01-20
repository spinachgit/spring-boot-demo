package com.spinach.example.util.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *   此工具类用法：实例化出对象，调用 void show() 方法.
 *   AlertNoticeUtil tool = new AlertNoticeUtil(); 
 *   tool.show()
 * </p>
 * @author wanghuihui
 * @date 2017-9-8下午2:10:19
 */
public class AlertNoticeUtil {
	private Logger logger = LoggerFactory.getLogger(AlertNoticeUtil.class); 
	/**
	 * 上一次坐标,拖动窗口时用
	 */
	private Point oldPoint;
	/**
	 * 提示框
	 */
	private TipWindow tipWindow = null;
	/**
	 * 图像组件
	 */
	private ImageIcon img = null;
	/**
	 * 背景图片标签
	 */
	private JLabel imgJLabel = null;
	/**
	 *  标题面板
	 */
	private JPanel headJPanel = null;
	private JLabel titleLabel = null; // 栏目名称
	private JLabel close = null; 		// 关闭按钮
	/**
	 * 内容页面相关
	 */
	private IconEnum contentGroup = null;
	private String contentTime = null;
	private String contentUser = null;
	private JPanel centerJPanel = null;
	private JPanel content = null;
	private JScrollPane jContent = null;
	private List<Map<String, String>> words = null;
	//private JButton selectedBtn;
	private JPanel selectedBtn;
	
	/**
	 * 控制按钮相关
	 */
	private JComboBox comboBox ;
	private DefaultComboBoxModel model ;
	private String selectedValue;
	private JButton btnStop ;
	private JButton btnClear;
	private JButton btnClearAll;
	
	/**
	 * 按钮颜色
	 */
	private Color btnColor = new Color(66,135,230);
	private Color contentColor = new Color(52,56,70);
	private Color bgColor = new Color(68,72,86);
	
	/**
	 * <p>
	 *  测试方法
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-8下午2:11:54
	 * @param args
	 */
	public static void main(String args[]) {
		AlertNoticeUtil tool = new AlertNoticeUtil();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<3;i++){
			Map<String,String> map = new HashMap<String, String>();
			map.put("word","1.博客:blog.csdn.net博客:blog.csdn.net ");
			map.put("time","15:00");
			list.add(map);
		}
		//tool.show("23个提醒", list,"机组","2017-12-13 15:00","阿辉");
		/*SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
			}
		});*/
	}
	
	/**
	 * <p>
	 *  实例化所有组件
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午3:58:42
	 * @param titlestr
	 */
	public void initAll(String titlestr) {
		// 新建450x350的消息提示框
		tipWindow = new TipWindow(450, 355);
		((JPanel) tipWindow.getContentPane()).setOpaque(false);
		
		// 设置JDialog的整个背景图片
		img = new ImageIcon();
		imgJLabel = new JLabel(img);
		imgJLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		tipWindow.getLayeredPane().add(imgJLabel, new Integer(Integer.MIN_VALUE));

		// 设置提示框的边框,宽度和颜色
		//tipWindow.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.CYAN));
		
		//实例化标题和内容
		initHeadTitle(titlestr);
		initContent();
		
		tipWindow.add(headJPanel, BorderLayout.NORTH);
		tipWindow.add(centerJPanel, BorderLayout.CENTER);
	}
	
	/**
	 * <p>
	 *  实例化标题
	 * </p>
	 * @author wanghuihui
	 * @param titlestr 
	 * @date 2017-9-1下午4:01:27
	 */
	private void initHeadTitle(String titlestr) {
		// 设置各个面板的布局以及面板中控件的边界
		headJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		headJPanel.setOpaque(false);
		headJPanel.setPreferredSize(new Dimension(450, 40));//300
		headJPanel.setBackground(contentColor);
		
		titleLabel = new JLabel(titlestr);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setPreferredSize(new Dimension(400, 40));
		titleLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		titleLabel.setForeground(Color.WHITE);
		
		close = new JLabel();
		close.setIcon(new ImageIcon(AlertNoticeUtil.class.getClassLoader().getResource(IconEnum.ICON_CLOSE.getUrl())));
		close.setPreferredSize(new Dimension(38, 38));
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setCursor(new Cursor(12));
		close.setToolTipText("关闭");
		
		headJPanel.add(titleLabel);
		headJPanel.add(close);
	}
	
	/**
	 * <p>
	 *  实例化内容
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-1下午5:38:45
	 */
	private void initContent() {
		Border lineBorder = new LineBorder(contentColor);
		centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerJPanel.setBackground(bgColor);
		
		JPanel  headPanel = getContentHead();
		centerJPanel.add(headPanel,BorderLayout.NORTH);
		
		content = new JPanel();
		content.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		content.setPreferredSize(new Dimension(300, 25*words.size()));
		content.setBackground(contentColor);
		for(Map<String,String> word : words){
			content.add(getMyJButton(word));
		}
		jContent = new JScrollPane(content);//ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER 
		jContent.setPreferredSize(new Dimension(410, 180));
		//jContent.setBorder(null);
		jContent.setBackground(bgColor);
		centerJPanel.add(jContent,BorderLayout.NORTH);
		
		// 增加空格和线
		JLabel black1 = new JLabel();
		black1.setPreferredSize(new Dimension(410, 10));
		centerJPanel.add(black1);
		// 增加空格
		JLabel black2 = new JLabel();
		black2.setPreferredSize(new Dimension(320, 30));
		centerJPanel.add(black2);
		
		// 增加清除
		btnClear = new JButton("清除");
		btnClear.setForeground(Color.white);
		btnClear.setToolTipText("请选择对应的内容清除！");
		btnClear.setBackground(btnColor);
		btnClear.setPreferredSize(new Dimension(90, 30));
		centerJPanel.add(btnClear,BorderLayout.EAST);
		
		// 增加空格
		JLabel black3 = new JLabel();
		black3.setPreferredSize(new Dimension(410, 5));
		centerJPanel.add(black3);
		// 增加线
		JLabel line = new JLabel();
		line.setPreferredSize(new Dimension(410, 2));
		line.setBorder(lineBorder);
		centerJPanel.add(line);
		JLabel black4 = new JLabel();
		black4.setPreferredSize(new Dimension(410, 5));
		centerJPanel.add(black4);
		
		JPanel bottomPanel = getBottomPanel();
		centerJPanel.add(bottomPanel);
	}
	
	/**
	 * <p>
	 *  内容标题
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午6:32:17
	 * @return
	 */
	private JPanel getContentHead() {
		int height = 40;
		JPanel contentHead = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		contentHead.setBackground(bgColor);
		contentHead.setPreferredSize(new Dimension(410, height));
		
		JLabel icon = new JLabel(contentGroup.getDesc());
		icon.setPreferredSize(new Dimension(100, height));
		icon.setIcon(new ImageIcon(AlertNoticeUtil.class.getClassLoader().getResource(contentGroup.getUrl())));
		icon.setForeground(Color.white);
		icon.setFont(new Font("黑体", Font.PLAIN, 18));
		contentHead.add(icon);
		
		JLabel black = new JLabel();
		black.setPreferredSize(new Dimension(100, height));
		contentHead.add(black);
		
		JLabel name = new JLabel(contentTime+"  "+contentUser);
		//name.setFont(new Font("黑体", Font.PLAIN, 13));
		name.setPreferredSize(new Dimension(208, height));
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		name.setForeground(Color.white);
		contentHead.add(name);
		
		return contentHead;
	}
	
	/**
	 * <p>
	 *  内容底部
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午6:32:23
	 * @return
	 */
	private JPanel getBottomPanel() {
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		bottomPanel.setBackground(bgColor);
		
		JLabel text = new JLabel(" 单击“暂停”在此时间后提醒 ");
		text.setForeground(Color.WHITE);
		
		comboBox = new JComboBox();
		model = new DefaultComboBoxModel();
		comboBox.setPreferredSize(new Dimension(65, 30));
		comboBox.setModel(model);
		comboBox.setForeground(Color.white);
		comboBox.setBackground(btnColor);
		//model.addElement("--选择暂停时间--");
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
				   	 selectedValue = model.getSelectedItem().toString();
				}
			}
		});
		initModelComboBox(30);
		
		btnStop = new JButton("暂停");
		btnStop.setForeground(Color.white);
		btnStop.setToolTipText("选择时间后，暂停！");
		btnStop.setBackground(btnColor);
		btnStop.setPreferredSize(new Dimension(65, 30));
		btnClearAll = new JButton("全部清除");
		btnClearAll.setForeground(Color.white);
		btnClearAll.setBackground(btnColor);
		btnClearAll.setPreferredSize(new Dimension(90, 30));
		
		bottomPanel.add(text);
		bottomPanel.add(comboBox);
		bottomPanel.add(btnStop);
		bottomPanel.add(btnClearAll);
		return bottomPanel;
	}

	/**
	 * <p>
	 *  获得自定义按钮
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午3:59:52
	 * @param word
	 * @return
	 */
	private JPanel getMyJButton(Map<String,String> map) {
		String word =  map.get("word");
		String timeStr = map.get("time");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(410, 25));
		panel.setBackground(contentColor);
		
		JLabel wordPanel = new JLabel("  "+word);
		wordPanel.setPreferredSize(new Dimension(360, 25));
		wordPanel.setForeground(Color.white);
		
		JLabel time = new JLabel(timeStr+"   ");
		time.setPreferredSize(new Dimension(50, 25));
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setForeground(Color.white);
		
		panel.add(wordPanel);
		panel.add(time);
		
		//增加事件监听
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("pressed");
				int count = content.getComponentCount();
				JPanel select = (JPanel)e.getSource();
				JPanel source ;
				for(int i=0;i<count;i++){
					source = (JPanel)content.getComponent(i);
					if (source == select) {
						source.setBackground(new Color(99,104,120));
						selectedBtn = source;
					}else{
						source.setBackground(contentColor);
					}
				}
				content.repaint();
			}
		});
		return panel;
	}
	
	/**
	 * <p>
	 *  实例化选择框内容
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午12:31:52
	 * @param minu : 间隔分钟数
	 */
	private void initModelComboBox(int minu) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR,0);
		end.set(Calendar.MINUTE,0);
		end.set(Calendar.SECOND,0);
		end.add(Calendar.DAY_OF_MONTH,1);
		System.out.println(DateUtil.datesToString(end.getTime()));
		
		int minute = start.get(Calendar.MINUTE);
		int i = (minute/minu+1)*minu; 
		if(i<60){
			start.set(Calendar.MINUTE,i);
		}else{
			start.add(Calendar.HOUR,1);
			start.set(Calendar.MINUTE,0);
		}
		
		Date temp = start.getTime();
		while (true) {
			String result = DateUtil.datesToString(temp, "HH:mm");
			model.addElement(result);
			temp = DateUtil.addMinutes(temp, minu);
			if(temp.getTime() > end.getTime().getTime()){
				break;
			}
		}
	}
	
	/**
	 * <p>
	 *  事件处理方法
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午4:02:11
	 */
	public void handle() {
		// 增加鼠标拖动事件
		titleLabel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point newP = new Point(e.getXOnScreen(), e.getYOnScreen());
				int x = tipWindow.getX() + (newP.x - oldPoint.x);
				int y = tipWindow.getY() + (newP.y - oldPoint.y);
				tipWindow.setLocation(x, y);
				oldPoint = newP;
			}
		});
		// 鼠标按下时初始坐标,供拖动时计算用
		titleLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldPoint = new Point(e.getXOnScreen(), e.getYOnScreen());
			}
		});
		// 右上角关闭按钮事件
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tipWindow.close();
			}
			public void mouseEntered(MouseEvent e) {
				close.setBorder(BorderFactory.createLineBorder(Color.gray));
			}
			public void mouseExited(MouseEvent e) {
				close.setBorder(null);
			}
		});
		
		//暂停按钮事件,有数据库交互
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//model.removeElement(selectedValue);
				System.out.println(selectedValue);
			}
		});
		//清除按钮 事件
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.remove(selectedBtn);
				int count = content.getComponentCount();
				content.setPreferredSize(new Dimension(300, 25*count));
				content.validate();
				content.repaint();
			}
		});
		//清除所有 按钮事件
		btnClearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				content.removeAll();
				int count = content.getComponentCount();
				content.setPreferredSize(new Dimension(300, 25*count));
				content.validate();
				content.repaint();
			}
		});
	}
	
	/**
	 * <p>
	 *  控件展示控制
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-5下午4:04:29
	 * @param titleT
	 * @param word
	 */
	//public void show(String titleT, String list,String contentGroup,String contentTime,String contentUser) {
	public void show(Object titleT, Object list,Object contentGroup,Object contentTime,Object contentUser) {
		if(StrUtil.isNull(titleT) || StrUtil.isNull(list) || StrUtil.isNull(contentGroup) || StrUtil.isNull(contentTime) || StrUtil.isNull(contentUser)){
			logger.error("超级提醒参数为空! titleT: "+titleT + " contentGroup:"+contentGroup+ " contentTime:"+contentTime+ " contentUser:"+contentUser);
			logger.error("超级提醒参数为空! list: "+list);
			return ; 
		}
		if(!(list instanceof List)){
			logger.error("类型出错! list: "+list);
			return;
		}
		
		this.words = (List<Map<String, String>>)list;
		if(contentGroup instanceof IconEnum){
			this.contentGroup = (IconEnum)contentGroup;
		}else{
			logger.error("类型出错! contentGroup不是枚举类型！");
			return;
		}
		this.contentTime = contentTime+"";
		this.contentUser = contentUser+"";		
		//内容
		initAll(titleT+"");
		//动作
		handle();
		tipWindow.setAlwaysOnTop(true);
		tipWindow.setUndecorated(true);
		tipWindow.setResizable(false);
		tipWindow.setVisible(true);
		tipWindow.run();
	}

}

/**
 * <p>
 *  自定义提醒类
 * </p>
 * @author wanghuihui
 * @date 2017-9-5下午4:05:03
 */
class TipWindow extends JDialog {
	private static final long serialVersionUID = 8541659783234673950L;
	private static Dimension dim;
	private int x, y;
	private int width, height;
	private static Insets screenInsets;
	private Color contentColor = new Color(52,56,70);
	
	/**
	 * <p>
	 *  实例化一个固定宽度和高度的窗口
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-8下午2:06:40
	 * @param width
	 * @param height
	 */
	public TipWindow(int width, int height) {
		this.width = width;
		this.height = height;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		x = (int) (dim.getWidth() - width - 3);
		y = (int) (dim.getHeight() - screenInsets.bottom - 3);
		this.setSize(width, height);
		this.setLocation(x, y);
		this.setBackground(contentColor);
	}
	
	/**
	 * <p>
	 *  窗口运动的效果
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-8下午2:07:27
	 */
	public void run() {
		for (int i = 0; i <= height; i += 10) {
			try {
				this.setLocation(x, y - i);
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
		}
		// 此处代码用来实现让消息提示框5秒后自动消失
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		close();*/
	}
	
	/**
	 * <p>
	 *  窗口关闭
	 * </p>
	 * @author wanghuihui
	 * @date 2017-9-8下午2:08:39
	 */
	public void close() {
		x = this.getX();
		y = this.getY();
		int ybottom = (int) dim.getHeight() - screenInsets.bottom;
		for (int i = 0; i <= ybottom - y; i += 10) {
			try {
				setLocation(x, y + i);
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
		}
		dispose();
	}
	
	
}