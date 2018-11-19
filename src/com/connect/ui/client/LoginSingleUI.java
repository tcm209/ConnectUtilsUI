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
	private  JFrame jf=  new JFrame("EASToolUI����");
	public  Container c=jf.getContentPane();	//���һ��cp����
	
	private  JTextField dbName=new JTextField();//���ݿ�����
	private  JLabel dbNameLab=new JLabel("���ݿ�����");
	
	private  JComboBox dbType=new JComboBox(labels);//���ݿ�����
	private  JLabel dbTypeLab=new JLabel("���ݿ�����");
	
	private  JTextField ipAdress =new JTextField();//ip��ַ  ���˿ں�
	private  JLabel ipAdressLab=new JLabel("ip��ַ");
	
	private  JTextField loginName =new JTextField();//��¼��
	private  JLabel loginNameLab=new JLabel("��¼��");
	
	public  JPasswordField password =new JPasswordField();//����
	private  JLabel passwordLab=new JLabel("����");
	
	private  JButton loginBtn=new JButton("����EAS");
	
	private  JButton exitBtn=new JButton("�˳�");
	
	
	
//	private  JButton empExcelBtn=new JButton("�������˱���Excel");
//	
//	private  JButton impExcelBtn=new JButton("�����������ݱ�");
	
	private static String msg="";
	
	public static void main(String[] args) {

		
		 init();
	}
	
	
	public static void init(){
		 //���ô����λ�ü���С
		
        new LoginSingleUI();
        
	}
	public LoginSingleUI(){
		jf.setBounds(600, 200, 500, 400);
        //����һ���൱�������Ķ���
        c.setLayout(new BorderLayout());//���ֹ�����
        //���ð������Ͻ�X�ź�ر�
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //��ʼ��--��������������ؼ�
        jf.setResizable(false);
        //���ô���ɼ�
        jf.setVisible(true);
        
        
		
		initFields();
	}
	public  void initFields(){
		
		
		
		/*���ⲿ��--North*/
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("EAS�������˱�ϵͳ"));
        c.add(titlePanel, "North");
        
        
        /*���벿��--Center*/
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
		
        
		
		
		//���������������
//		JLabel labelPwd=new JLabel("����");
//		labelPwd.setBounds(20, 110, 50, 18);
//		uiPanel.add(labelPwd);
	}

	
	public void showHiddenFields(JPanel panel){
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String clickName=e.getActionCommand().toString();
		if(clickName.equals("����EAS")){
			String dbName=this.dbName.getText();
			String dbType=this.dbType.getSelectedItem().toString();
			String ipAdress=this.ipAdress.getText();
			String loginid=this.loginName.getText();
			String pwd=this.password.getText();
			
			if("".equals(dbName) || "".equals(dbType) || "".equals(ipAdress) || "".equals(loginid) || "".equals(pwd)){
				JOptionPane.showMessageDialog(this.c, "���������");
				return;
			}
			String msg=connectionEAS(loginid,pwd,ipAdress,dbName,dbType);
			if(msg.equals("ok")){
				//��ʾ��������
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
		}else if(clickName.equals("�����������ݱ�")){
			
			
			
		}else if("�������˱���Excel".equals(clickName)){
			
		}
	}
	/**
	 * 
	 */
	public String connectionEAS(String loginid,String pwd,String ipadress,String dbname,String dbtype) {
		System.setProperty("sun.net.client.defaultConnectTimeout", String .valueOf(10000));// ����λ�����룩  
		String msg="";
		EASLoginProxyServiceLocator loginLocator = new EASLoginProxyServiceLocator();
		String inAdress=ipadress+"/ormrpc/services/EASLogin";
		
		loginLocator.setEASLoginEndpointAddress(inAdress);//����ip��ַ

		EASLoginProxy loginProxy;
		try {
			loginProxy = loginLocator.getEASLogin();
			WSContext context = loginProxy.login(loginid, pwd, "eas",dbname, "L2", 2);
			if(context.getSessionId()==null){
				msg="�û�����������ݿ���������ȷ��";
			}
			if(context.getSessionId()!=null){
				
				msg="ok";
			}
			System.out.println("------ ��½�ɹ���SessionID��"+ context.getSessionId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			msg="��ȷ��������Ϣ�Ƿ���ȷ";
		} catch (RemoteException e) {
			msg="���ʾܾ�����ȷ��������Ϣ";
			// TODO Auto-generated catch block
			
		}
		return msg;

	}
	
	

}
