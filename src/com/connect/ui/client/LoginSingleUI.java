package com.connect.ui.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.xml.rpc.ServiceException;

import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxy;
import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxyServiceLocator;
import client.WSContext;

public class LoginSingleUI implements ActionListener{
	
	private  String labels[] = { "ORACLE"}; 
	private  JFrame jf=  new JFrame("EASToolUI工具");
	public  Container c=jf.getContentPane();	//添加一个cp容器
	
	private  JTextField dbName=new JTextField();//数据库名称
	private  JLabel dbNameLab=new JLabel("数据库名称");
	
	private  JComboBox dbType=new JComboBox(labels);//数据库类型
	private  JLabel dbTypeLab=new JLabel("数据库类型");
	
	private  JTextField ipAdress =new JTextField();//ip地址  ：端口号
	private  JLabel ipAdressLab=new JLabel("ip地址");
	
	private  JTextField loginName =new JTextField();//登录名
	private  JLabel loginNameLab=new JLabel("登录名");
	
	public  JPasswordField password =new JPasswordField();//密码
	private  JLabel passwordLab=new JLabel("密码");
	
	private  JButton loginBtn=new JButton("连接EAS");
	
	private  JButton exitBtn=new JButton("退出");
	
	
	
//	private  JButton empExcelBtn=new JButton("导出总账表至Excel");
//	
//	private  JButton impExcelBtn=new JButton("导入总账数据表");
	
	private static String msg="";
	
	public static void main(String[] args) {

		
		 init();
	}
	
	
	public static void init(){
		 //设置窗体的位置及大小
		
        new LoginSingleUI();
        
	}
	public LoginSingleUI(){
		jf.setBounds(600, 200, 500, 400);
        //设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());//布局管理器
        //设置按下右上角X号后关闭
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //初始化--往窗体里放其他控件
        jf.setResizable(false);
        //设置窗体可见
        jf.setVisible(true);
        
        
		
		initFields();
	}
	public  void initFields(){
		
		
		
		/*标题部分--North*/
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("EAS备份总账表系统"));
        c.add(titlePanel, "North");
        
        
        /*输入部分--Center*/
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        dbNameLab.setBounds(50, 20, 120, 20);
        dbName.setBounds(150, 20, 250, 20);
        fieldPanel.add(dbNameLab);
        fieldPanel.add(dbName);
       
        dbTypeLab.setBounds(50, 60, 120, 20);
        dbType.setBounds(150, 60, 250, 20);
        
        fieldPanel.add(dbTypeLab);
        fieldPanel.add(dbType);
        
        ipAdressLab.setBounds(50,100,120,20);
        ipAdress.setBounds(150,100,250,20);
        fieldPanel.add(ipAdressLab);
        fieldPanel.add(ipAdress);
        
        loginNameLab.setBounds(50, 140, 120, 20);
        loginName.setBounds(150,140,250,20);
        fieldPanel.add(loginNameLab);
        fieldPanel.add(loginName);
        
        passwordLab.setBounds(50, 180, 120, 20);
        password.setBounds(150, 180, 250, 20);
        fieldPanel.add(passwordLab);
        fieldPanel.add(password);
        
        loginBtn.setBounds(120, 220, 100, 20);
        exitBtn.setBounds(250, 220, 100, 20);
        loginBtn.addActionListener(this);
        fieldPanel.add(loginBtn);
        fieldPanel.add(exitBtn);
        
        
//        empExcelBtn.setVisible(false);
//        empExcelBtn.setBounds(80, 250, 150, 25);
//        empExcelBtn.addActionListener(this);
//        
//        impExcelBtn.setVisible(true);
//        impExcelBtn.setBounds(250, 250, 150, 25);
//        impExcelBtn.addActionListener(this);
//        fieldPanel.add(impExcelBtn);
//        fieldPanel.add(empExcelBtn);
//        
        
        
        
        
        c.add(fieldPanel, "Center");
		
        
		
		
		//设置左侧密码文字
//		JLabel labelPwd=new JLabel("密码");
//		labelPwd.setBounds(20, 110, 50, 18);
//		uiPanel.add(labelPwd);
	}

	
	public void showHiddenFields(JPanel panel){
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String clickName=e.getActionCommand().toString();
		if(clickName.equals("连接EAS")){
			String dbName=this.dbName.getText();
			String dbType=this.dbType.getSelectedItem().toString();
			String ipAdress=this.ipAdress.getText();
			String loginid=this.loginName.getText();
			String pwd=this.password.getText();
			
			if("".equals(dbName) || "".equals(dbType) || "".equals(ipAdress) || "".equals(loginid) || "".equals(pwd)){
				JOptionPane.showMessageDialog(this.c, "请输入完成");
				return;
			}
			String msg=connectionEAS(loginid,pwd,ipAdress,dbName,dbType);
			if(msg.equals("ok")){
				//显示导出总账
//				impExcelBtn.setVisible(false);
//				empExcelBtn.setVisible(false);
				
//				JOptionPane.showMessageDialog(this.c, msg);
				jf.dispose();
				StartUI s=new StartUI( dbName,dbType,ipAdress,loginid,pwd);
				s.init();
				
			}else{
				JOptionPane.showMessageDialog(this.c, msg);
				return;
			}
//			StartUI s=new StartUI(name.getText().trim(),"123456");
		}else if(clickName.equals("导入总账数据表")){
			
			
			
		}else if("导出总账表至Excel".equals(clickName)){
			
		}
	}
	/**
	 * 
	 */
	public String connectionEAS(String loginid,String pwd,String ipadress,String dbname,String dbtype) {
		System.setProperty("sun.net.client.defaultConnectTimeout", String .valueOf(10000));// （单位：毫秒）  
		String msg="";
		EASLoginProxyServiceLocator loginLocator = new EASLoginProxyServiceLocator();
		String inAdress=ipadress+"/ormrpc/services/EASLogin";
		
		loginLocator.setEASLoginEndpointAddress(inAdress);//设置ip地址

		EASLoginProxy loginProxy;
		try {
			loginProxy = loginLocator.getEASLogin();
			WSContext context = loginProxy.login(loginid, pwd, "eas",dbname, "L2", 2);
			if(context.getSessionId()==null){
				msg="用户密码或者数据库名错误，请确认";
			}
			if(context.getSessionId()!=null){
				
				msg="ok";
			}
			System.out.println("------ 登陆成功，SessionID："+ context.getSessionId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			msg="请确认所填信息是否正确";
		} catch (RemoteException e) {
			msg="访问拒绝，请确定所填信息";
			// TODO Auto-generated catch block
			
		}
		return msg;

	}
	
	

}
