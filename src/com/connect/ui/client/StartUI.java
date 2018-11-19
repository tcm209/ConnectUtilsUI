package com.connect.ui.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.rpc.ServiceException;

import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxy;
import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxyServiceLocator;
import client.WSContext;

import com.connect.helpers.SQLUtils;
import com.connect.helpers.ShowUI;

public class StartUI extends JFrame {

	private ActionListener btnClickListener;

	private String dbName;//���ݿ���
	
	private String dbType;//���ݿ�����
	
	private String ipAdress;//ip��ַ  ���˿ں�
	
	private String loginName;//��¼��
	
	private String password;//����

//	private JFrame jf = new JFrame("���Դ���");

	private Container c = this.getContentPane();

	private JMenuBar menuBar =new JMenuBar();
	
//	public static void main(String[] args) {
//
//		ConifgUIUitls loginUI=new ConifgUIUitls();
//		System.out.print("��¼����"+loginUI.msg+"\r\n");
//		if(loginUI.msg.equals("200")){
//			SingleUIUtils.getInstance();
//		}
//		
//	}
	
	



	public StartUI(String dbName, String dbType, String ipAdress, String loginName,String password) {
		super();
		this.dbName = dbName;
		this.dbType = dbType;
		this.ipAdress = ipAdress;
		this.loginName = loginName;
		this.password = password;
	}




	public StartUI() {
		super();
        
    }
    
    public void init() {
    	 this.setBounds(300, 100, 700, 500);
    	 setTitle("EASToolUI����");
         //����һ���൱�������Ķ���
         c.setLayout(new BorderLayout());//���ֹ�����
         //���ð������Ͻ�X�ź�ر�
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //��ʼ��--��������������ؼ�
         this.setResizable(false);
         //���ô���ɼ�
         this.setVisible(true);
       //��ʼ��һ���˵���
         JMenu menu1 = new JMenu("����");
         
         JMenuItem loginItem=new JMenuItem("���µ�¼");
         loginItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.print("ִ�е������");
				StartUI.this.dispose();
				new LoginSingleUI();
			}
        	 
         });
         
         
         menu1.add(loginItem);
         menuBar.add(menu1);
         this.setJMenuBar(menuBar);
    	new ShowUI(c,this.dbName,this.dbType,this.ipAdress,this.loginName,this.password);
//    	clickEAS();
       
    }
	
	public static void showColumnsContent(){
		Connection conn = SQLUtils.getCon();
		
		DatabaseMetaData dbmd;
		try {
			dbmd = conn.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "ZCOF002", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName=resultSet.getString("TABLE_NAME");
		    	System.out.println(tableName+"\r\n");
		    	if(tableName.equals("ZCOF002")){
		    		
		    		ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn),tableName.toUpperCase(), "%");
		    		while(rs.next()){
		    			System.out.println("�ֶ�����"+rs.getString("COLUMN_NAME")+"--�ֶ�ע�ͣ�"+rs.getString("REMARKS")+"--�ֶ��������ͣ�"+rs.getString("TYPE_NAME")+"\r\n");
		    		}
		    		
		    		
		    	}
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE���ݿ�ģʽ������Ϊ��");
		}
		return schema.toUpperCase().toString();
 
	}
	
	public static void clickEAS() {
		EASLoginProxyServiceLocator loginLocator = new EASLoginProxyServiceLocator();
//		loginLocator.setEASLoginEndpointAddress(address);//����ip��ַ

		EASLoginProxy loginProxy;
		try {
			loginProxy = loginLocator.getEASLogin();
			WSContext context = loginProxy.login("wucl", "", "eas","sunrise12", "L2", 2);
			System.out.println("------ ��½�ɹ���SessionID��"+ context.getSessionId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
