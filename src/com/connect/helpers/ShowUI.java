package com.connect.helpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.rpc.ServiceException;

import tableoperfacade.client.WSInvokeException;
import _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy;
import _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxyServiceLocator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eltima.components.ui.DatePicker;

public class ShowUI implements ActionListener{
	
	private  JPanel topPane;
	
	private  JLabel startDateLabel = new JLabel("��ʼʱ��");
	
	private  DatePicker startDatepick;
	
	private  DatePicker endDatepick;
	
	private  JLabel endDateLabel = new JLabel("����ʱ��");
	
	
	private  JPanel contentPane;
	
    private  DefaultTableModel model;
    
    private  JTable table;
    
    private  JScrollPane scrollPane;
    
   
    
    
    private  JButton addBtn ;
    private  JButton deleteBtn ;
    private  JButton empExeclBtn ;
    private  JButton impExeclBtn;
    
    private String dbName;//���ݿ���
	
	private String dbType;//���ݿ�����
	
	private String ipAdress;//ip��ַ  ���˿ں�
	
	private String loginName;//��¼��
	
	private String password;//����
    
    private static Map<String,List<Map<String,String>>> mapEmpDatas=null;//<tablename,<field,value>>
    
    public ShowUI(Container c,String dbname,String dbType,String ipAdress, String loginName,String password){
    	
    	super();
    	initTableHead(c);
    	this.dbName=dbname;
    	this.dbType=dbType;
    	this.ipAdress=ipAdress;
    	this.loginName=loginName;
    	this.password=password;
    	System.out.print(""+this.dbName+"\r\n");
    }
    
    
	
	public  void initTableHead(Container container){

		Vector vData = new Vector();
		Vector vName = new Vector();
		
		vName.add("�������");
		vName.add("��ֵ");
		vName.add("�ӱ����");
		vName.add("��ֵ");
		
		
		model = new DefaultTableModel(vData, vName);
		table = new JTable();
		table.setModel(model);//���ñ�ͷ
		setTableHeadStyle(table);//���ñ�ͷ��ʽ
		

		
		
		
		//������addMouListener
		
		table.setName("table");
		

		//������  ʱ��ѡ��
		topPane=new JPanel();
		container.add(topPane,BorderLayout.NORTH);
		
		startDateLabel.setBounds(5, 5, 100, 25);
		topPane.add(startDateLabel);
        startDatepick=getDatePicker();
        startDatepick.setBounds(105,5,100,25);
        topPane.add(startDatepick);
        
        endDateLabel.setBounds(110, 5, 100, 25);
        topPane.add(endDateLabel);
        endDatepick=getDatePicker();
        startDatepick.setBounds(130,5,100,25);
        topPane.add(endDatepick);
		//�м�����
		scrollPane = new JScrollPane(table);
		container.add(scrollPane);
		//��ť����
		contentPane = new JPanel();
		container.add(contentPane,BorderLayout.SOUTH);
        addBtn = new JButton("��ӹ�ϵ��");
        deleteBtn = new JButton("ɾ����ϵ��");
        empExeclBtn = new JButton("������");
        impExeclBtn = new JButton("�����");
		
        contentPane.add(addBtn);
        contentPane.add(deleteBtn);
        contentPane.add(empExeclBtn);
        contentPane.add(impExeclBtn);
        //��Ӽ���
        addBtn.addActionListener(this);

        //ɾ��ѡ�����
        deleteBtn.addActionListener(this);
        //��������EXCEL
        empExeclBtn.addActionListener(this);
        
        //�����
        impExeclBtn.addActionListener(this);
        
		
	}
	/*
	 * 
	 */
	public  boolean empExcel(String param){
		//filename=sunrise+ʱ��+����
		boolean b=false;
		String fileURL="";
		JFileChooser chooser = new JFileChooser();
    	chooser.setFileSelectionMode(1);// �趨ֻ��ѡ���ļ��� 
    	int state = chooser.showSaveDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������  
    	if (state == 1) {  
            return b;  
        } else {  
            File f = chooser.getSelectedFile();// fΪѡ�񵽵�Ŀ¼  
            fileURL=f.getAbsolutePath();
        }  
    	String fileName=this.dbName+"_"+param;
		b=POIUtils.empExcelModel(fileName,fileURL, mapEmpDatas);
		return b;
	}
	
	/*
	 * �ļ�ѡ��
	 */
	public static String getFileURL(){
		String filepath="";
		JFileChooser chooser = new JFileChooser(); 
		chooser.setMultiSelectionEnabled(false);//���õ�ѡ
		int rtnValue=chooser.showOpenDialog(null);
		if(rtnValue== JFileChooser.APPROVE_OPTION){
			
			String fileName=chooser.getSelectedFile().getName().toString();
			
			if(!fileName.endsWith(".xls")){
				MsgBoxUtils.showMessageDialog1("����", "�ļ�ѡ���������ȷѡ��");
				return null;
			}else{
				filepath = chooser.getSelectedFile().getAbsolutePath(); //��ȡ�ļ�����·��
			}
		}
		
		return filepath;
		
	}
	
	
	

	/*
	 * ���ñ�ͷ��ʽ
	 */
	public static void setTableHeadStyle(JTable table){
		table.getTableHeader().setBackground(new Color(176, 192, 200));
		Dimension size = table.getTableHeader().getPreferredSize();
		size.height = 32;//�����µı�ͷ�߶�32
		table.getTableHeader().setPreferredSize(size);
		table.setRowHeight(26);

	}
	
	/*
	 * �����Ƿ��Ѵ�����ͬ��������
	 */
	public boolean isExist(JTable table){
		boolean b=false;
		int rowCount=table.getRowCount();
		for(int i=0;i<rowCount;i++){
			String cell1=table.getValueAt(i, 0).toString();
			String cell2=table.getValueAt(i, 1).toString();
			String cell3=table.getValueAt(i, 2).toString();
			String cell4=table.getValueAt(i, 3).toString();
			for(int j=0;j<rowCount;j++){
				if(i!=j){
					String tableName=table.getValueAt(j, 0).toString();
					String tableVal=table.getValueAt(j, 1).toString();
					String entryTableName=table.getValueAt(j, 2).toString();
					String entryVal=table.getValueAt(j, 3).toString();
					if(cell1.equals(tableName) && cell2.equals(tableVal)&&cell3.equals(entryTableName)&& cell4.equals(entryVal)){
						b=true;
						break;
						
					}
				}
				
			}
			
			
		}
		return b;
	}
	
	
	
	/*
	 * ��ʼ��ʱ�� ѡ����
	 */
	public  DatePicker getDatePicker() {
		
		final DatePicker datepick;
        // ��ʽ
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        // ��ǰʱ��
        Date date = new Date();
        // ����
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);

        int[] hilightDays = { 1, 3, 5, 7 };

        int[] disabledDays = { 4, 6, 5, 9 };
    //���췽������ʼʱ�䣬ʱ����ʾ��ʽ�����壬�ؼ���С��
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setLocation(137, 83);//������ʼλ��
        /*
        //Ҳ����setBounds()ֱ�����ô�С��λ��
        datepick.setBounds(137, 83, 177, 24);
        */
        // ����һ���·�����Ҫ������ʾ������
        datepick.setHightlightdays(hilightDays, Color.red);
        // ����һ���·��в���Ҫ�����ӣ��ʻ�ɫ��ʾ
        datepick.setDisableddays(disabledDays);
        // ���ù���
        datepick.setLocale(Locale.CANADA);
        // ����ʱ�����ɼ�
        datepick.setTimePanleVisible(true);
        return datepick;
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String clickName=e.getActionCommand().toString();
		if(clickName.equals("��ӹ�ϵ��")){
			String[] rowValue={"","","",""};
			model.addRow(rowValue);
			int rowcount=table.getRowCount()+1;
			System.out.println("����"+rowcount+"\r\n");
		}else if(clickName.equals("ɾ����ϵ��")){
			int[] selectRows= table.getSelectedRows();//��ȡ��ǰѡ���������
			int selectSize=selectRows.length;
			if(selectSize!=0){
				for(int i=0;i<selectSize; i++){
//					model.removeRow(selectRows[i]);
					selectRows[i] = table.convertRowIndexToModel(selectRows[i]);
				}
				DefaultTableModel df = (DefaultTableModel) table.getModel(); 
				//����ɾ��  
				for (int i = selectSize; i > 0; i--) {  
				    df.removeRow(table.getSelectedRow());  
				}
				
			}
			if(selectSize==0){
				MsgBoxUtils.showMessageDialog1("����", "��ѡ��Ҫɾ������");
				return;
			}
		}else if(clickName.equals("������")){
			String fromDate=this.startDatepick.getText();
			String toDate=this.endDatepick.getText();
			
			int rowCount=table.getRowCount();//��ȡ������ ���Ϊ0  ��ִ��
			
			if(rowCount==0){
				MsgBoxUtils.showMessageDialog1("����", "���������ݽ��е���");
//				System.out.print("���������ݽ��е���");
				return;
			}
			boolean isExit=isExist(table);
			if(isExit){				//��֤�����Ƿ�����ظ�
				MsgBoxUtils.showMessageDialog1("����", "�б��ڴ�����ͬ�������ݣ���ȷ��");
//				System.out.print("���������ݽ��е���");
				return;
			}
			if(rowCount!=0&&!isExit){
				//�ж��Ƿ������ͬ����
				//�ж������Ƿ�������ݱ�
				int columnCount=table.getColumnCount();//��ȡ��
				List<Map<String,String>> inArr=new ArrayList<Map<String,String>>();//{1{parentTab:tableName,parentValue��tableVal,childTab:entryTableName}}
				
				for(int i=0;i<rowCount; i++){
					Map entryMap=new HashMap();
					if(table.getValueAt(i, 0)!=null && table.getValueAt(i, 1)!=null && table.getValueAt(i, 2)!=null && table.getValueAt(i, 3)!=null){
						String tableName=table.getValueAt(i, 0).toString();
						String tableVal=table.getValueAt(i, 1).toString();
						String entryTableName=table.getValueAt(i, 2).toString();
						String entryVal=table.getValueAt(i, 3).toString();
						
						
						
						if("".equals(tableName)){
							MsgBoxUtils.showMessageDialog1("��Ϣ", "��"+i+"�д��ڴ�������дҪ�����ı���");
							return;
						}
						if("".equals(tableVal)){
							if(!"".equals(entryTableName)||!"".equals(entryVal)){
								MsgBoxUtils.showMessageDialog1("��Ϣ", "��"+i+"�д��ڴ�����ֵΪ��ʱ������Ҫ��д�ӱ��������ֵ");
								return;
							}
						}else{
							if("".equals(entryTableName)||"".equals(entryVal)){
								MsgBoxUtils.showMessageDialog1("��Ϣ", "��"+i+"�д��ڴ�����ֵ��Ϊ��ʱ��������д�ӱ��������ֵ");
								return;
							}
						}
						
						entryMap.put("parentTab", tableName);
						entryMap.put("parentValue", tableVal);
						entryMap.put("childTab", entryTableName);
						entryMap.put("childValue", entryVal);
						
						inArr.add(entryMap);
//						System.out.print("��ǰ��  ��"+i+"��֤"+tableName+"ֵ"+tableVal+"�ӱ�"+entryTableName+"�ӱ�ֵ"+entryVal+"\r\n");
					}
					
				}
				
				//��ȡeas���ص�����
				Map empMap=getEASDataByWebService(inArr,fromDate,toDate);
				String code=empMap.get("code").toString();
				if(code.equals("200")){
					
					String jsonDatas=empMap.get("result").toString();
					com.alibaba.fastjson.JSONObject jsonMap=com.alibaba.fastjson.JSON.parseObject(jsonDatas);
					String msg=jsonMap.get("success").toString();
					if(msg.equals("T")){
						String swi=jsonMap.get("resultData").toString();
						com.alibaba.fastjson.JSONObject datas=com.alibaba.fastjson.JSON.parseObject(swi);
						mapEmpDatas=com.alibaba.fastjson.JSONObject.toJavaObject(datas, Map.class);
						//����������  poiд�뵽excel 
						if(mapEmpDatas==null){
							MsgBoxUtils.showMessageDialog("��ʾ", "����ϵ����Ա�������쳣");
							return;
						}
						int size=mapEmpDatas.size();
						if(size!=0){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							Date filenameDate=null;
							String fileNameStr="";
							try {
								 filenameDate=sdf.parse(fromDate);
								 fileNameStr=sdf.format(filenameDate);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							boolean b=empExcel(fileNameStr);
							if(!b){
								MsgBoxUtils.showMessageDialog("��ʾ", "��������Ϊ�գ����鵼������");
								return;
							}else{
								MsgBoxUtils.showMessageDialog("��ʾ", "�����ɹ�");
								return;
							}
						}
					}else if(msg.equals("F")){
						String error=jsonMap.get("errorMessage").toString();
						LogUtils.writeLog("���ݱ������ݴ��ڴ���"+error+" \r\n");
						MsgBoxUtils.showMessageDialog("��ʾ", "���ϵͳ��־");
						return;
					}
					
				}else if(code.equals("500")){
					String error=empMap.get("result").toString();
					MsgBoxUtils.showMessageDialog1("��ʾ", error);
					return;
				}
				

				
				
			}
		}else if("�����".equals(clickName)){
			
			
			String fileURL=getFileURL();
			if(!"".equals(fileURL)){
				String fromDate=this.startDatepick.getText();
				String toDate=this.endDatepick.getText();
				Map<String,List<Map<String,String>>> maps=POIUtils.readSheetsData(fileURL,1);//��ȡ����excel�ļ�  �Ƿ���˲������ֶ�
				if(maps.size()==0){
					MsgBoxUtils.showMessageDialog("��ʾ", "��ѡ����ȷ�ĵ���ģ��");
					return;
				}
				
//				WebServiceTestUtils.exeInsertSql(maps);//eas�ӿڵ���
				Map impMap=impExcelAction("ORACLE",fromDate,toDate,maps);
				String code=impMap.get("code").toString();
				if(code.equals("200")){//������Ϣ
					String jsonDats=impMap.get("result").toString();
					com.alibaba.fastjson.JSONObject jsonMap=com.alibaba.fastjson.JSON.parseObject(jsonDats);
					String msg=jsonMap.get("success").toString();
					if(msg.equals("T")){
						MsgBoxUtils.showMessageDialog("��ʾ", "����ɹ�");
						return;
					}
					if(msg.equals("F")){
						String resultDatas=jsonMap.get("resultData").toString();
						LogUtils.writeLog("���ֱ����쳣��Ϣ��"+resultDatas+" \r\n");
						MsgBoxUtils.showMessageDialog("��ʾ", "���ֱ����쳣��������־��Ϣ");
						return ;
					}
					
					
				}else if(code.equals("500")){//�����쳣
					String error=impMap.get("result").toString();
					MsgBoxUtils.showMessageDialog1("��ʾ", error);
					return;
				}
				
				
				
				
				
				
			}else{
				return;
			}
			
			
			
		}
	
	}
	
	
	
	/*
	 * ����sheet��
	 */
	public Map impExcelAction(String dbType,String startDate,String endDate,Map<String,List<Map<String,String>>> maps){
		
		LogUtils.writeLog("=======================����sheet�� ��ʼ========================================  \r\n");
		LogUtils.writeLog("��ȡExcelip��ַ"+this.ipAdress+" \r\n");
		Map msgMap=new HashMap();
		WSTableOperFacadeSrvProxyServiceLocator easFace=new WSTableOperFacadeSrvProxyServiceLocator();
		try {
			easFace.setWSTableOperFacadeEndpointAddress(this.ipAdress+"/ormrpc/services/WSTableOperFacade");
			WSTableOperFacadeSrvProxy easProxy= easFace.getWSTableOperFacade();
			
			
			JSONObject  jsonMap = JSON.parseObject(JSON.toJSONString(maps));
			
			String resultDatas=easProxy.importTable(jsonMap.toJSONString(), startDate, endDate);
			msgMap.put("code", "200");
			msgMap.put("result", resultDatas);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("����EXCEL  sebservice �쳣��"+e+"\r\n");
			
		} catch (WSInvokeException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("����EXCEL  sebservice �쳣��"+e+"\r\n");
			
		} catch (RemoteException e) {
			msgMap.put("code", "500");
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("����EXCEL  sebservice �쳣��"+e+"\r\n");
			
		}
		
		LogUtils.writeLog("======================����sheet�� ����================================ \r\n");
		return msgMap;
		
		
	}
	/*
	 * ��ȡ������� Excel 
	 */
	public Map getEASDataByWebService(List<Map<String,String>> arr,String startDate,String endDate){
		LogUtils.writeLog("=================================��ȡ����webservice ��ʼ============================== \r\n");
		LogUtils.writeLog("��ȡExcelip��ַ"+this.ipAdress+" \r\n");
		
		
		WSTableOperFacadeSrvProxyServiceLocator easFace=new WSTableOperFacadeSrvProxyServiceLocator();
		easFace.setWSTableOperFacadeEndpointAddress(this.ipAdress+"/ormrpc/services/WSTableOperFacade");
		Map msgMap =new HashMap();
		try {
			String rs=JSON.toJSONString(arr);
			WSTableOperFacadeSrvProxy easProxy= easFace.getWSTableOperFacade();
			String jsonDatas=easProxy.exportTable(rs, startDate, endDate);
			msgMap.put("code", "200");
			msgMap.put("result", jsonDatas);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("��������  sebservice �쳣��"+e+"\r\n");
			
		} catch (WSInvokeException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("��������  sebservice �쳣��"+e+"\r\n");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			
			msgMap.put("result", "�����쳣������ϵ����ʦ�鿴������־");
			LogUtils.writeLog("��������  sebservice �쳣��"+e+"\r\n");
		}
		
		LogUtils.writeLog("=================================��ȡ����webservice ����============================== \r\n");
		return msgMap;		
	}
	
	
	
	
}

