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
	
	private  JLabel startDateLabel = new JLabel("开始时间");
	
	private  DatePicker startDatepick;
	
	private  DatePicker endDatepick;
	
	private  JLabel endDateLabel = new JLabel("结束时间");
	
	
	private  JPanel contentPane;
	
    private  DefaultTableModel model;
    
    private  JTable table;
    
    private  JScrollPane scrollPane;
    
   
    
    
    private  JButton addBtn ;
    private  JButton deleteBtn ;
    private  JButton empExeclBtn ;
    private  JButton impExeclBtn;
    
    private String dbName;//数据库名
	
	private String dbType;//数据库类型
	
	private String ipAdress;//ip地址  ：端口号
	
	private String loginName;//登录名
	
	private String password;//密码
    
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
		
		vName.add("主表表名");
		vName.add("主值");
		vName.add("子表表名");
		vName.add("子值");
		
		
		model = new DefaultTableModel(vData, vName);
		table = new JTable();
		table.setModel(model);//设置表头
		setTableHeadStyle(table);//设置表头样式
		

		
		
		
		//鼠标监听addMouListener
		
		table.setName("table");
		

		//上区域  时间选择
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
		//中间区域
		scrollPane = new JScrollPane(table);
		container.add(scrollPane);
		//按钮区域
		contentPane = new JPanel();
		container.add(contentPane,BorderLayout.SOUTH);
        addBtn = new JButton("添加关系表");
        deleteBtn = new JButton("删除关系表");
        empExeclBtn = new JButton("导出表");
        impExeclBtn = new JButton("导入表");
		
        contentPane.add(addBtn);
        contentPane.add(deleteBtn);
        contentPane.add(empExeclBtn);
        contentPane.add(impExeclBtn);
        //添加监听
        addBtn.addActionListener(this);

        //删除选择的行
        deleteBtn.addActionListener(this);
        //导出表至EXCEL
        empExeclBtn.addActionListener(this);
        
        //导入表
        impExeclBtn.addActionListener(this);
        
		
	}
	/*
	 * 
	 */
	public  boolean empExcel(String param){
		//filename=sunrise+时间+表名
		boolean b=false;
		String fileURL="";
		JFileChooser chooser = new JFileChooser();
    	chooser.setFileSelectionMode(1);// 设定只能选择到文件夹 
    	int state = chooser.showSaveDialog(null);// 此句是打开文件选择器界面的触发语句  
    	if (state == 1) {  
            return b;  
        } else {  
            File f = chooser.getSelectedFile();// f为选择到的目录  
            fileURL=f.getAbsolutePath();
        }  
    	String fileName=this.dbName+"_"+param;
		b=POIUtils.empExcelModel(fileName,fileURL, mapEmpDatas);
		return b;
	}
	
	/*
	 * 文件选择
	 */
	public static String getFileURL(){
		String filepath="";
		JFileChooser chooser = new JFileChooser(); 
		chooser.setMultiSelectionEnabled(false);//设置单选
		int rtnValue=chooser.showOpenDialog(null);
		if(rtnValue== JFileChooser.APPROVE_OPTION){
			
			String fileName=chooser.getSelectedFile().getName().toString();
			
			if(!fileName.endsWith(".xls")){
				MsgBoxUtils.showMessageDialog1("错误", "文件选择错误，请正确选择");
				return null;
			}else{
				filepath = chooser.getSelectedFile().getAbsolutePath(); //获取文件绝对路径
			}
		}
		
		return filepath;
		
	}
	
	
	

	/*
	 * 设置表头样式
	 */
	public static void setTableHeadStyle(JTable table){
		table.getTableHeader().setBackground(new Color(176, 192, 200));
		Dimension size = table.getTableHeader().getPreferredSize();
		size.height = 32;//设置新的表头高度32
		table.getTableHeader().setPreferredSize(size);
		table.setRowHeight(26);

	}
	
	/*
	 * 表内是否已存在相同的行数据
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
	 * 初始化时间 选择器
	 */
	public  DatePicker getDatePicker() {
		
		final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);

        int[] hilightDays = { 1, 3, 5, 7 };

        int[] disabledDays = { 4, 6, 5, 9 };
    //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setLocation(137, 83);//设置起始位置
        /*
        //也可用setBounds()直接设置大小与位置
        datepick.setBounds(137, 83, 177, 24);
        */
        // 设置一个月份中需要高亮显示的日子
        datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CANADA);
        // 设置时钟面板可见
        datepick.setTimePanleVisible(true);
        return datepick;
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String clickName=e.getActionCommand().toString();
		if(clickName.equals("添加关系表")){
			String[] rowValue={"","","",""};
			model.addRow(rowValue);
			int rowcount=table.getRowCount()+1;
			System.out.println("行数"+rowcount+"\r\n");
		}else if(clickName.equals("删除关系表")){
			int[] selectRows= table.getSelectedRows();//获取当前选择的所有行
			int selectSize=selectRows.length;
			if(selectSize!=0){
				for(int i=0;i<selectSize; i++){
//					model.removeRow(selectRows[i]);
					selectRows[i] = table.convertRowIndexToModel(selectRows[i]);
				}
				DefaultTableModel df = (DefaultTableModel) table.getModel(); 
				//反向删除  
				for (int i = selectSize; i > 0; i--) {  
				    df.removeRow(table.getSelectedRow());  
				}
				
			}
			if(selectSize==0){
				MsgBoxUtils.showMessageDialog1("错误", "请选择要删除的行");
				return;
			}
		}else if(clickName.equals("导出表")){
			String fromDate=this.startDatepick.getText();
			String toDate=this.endDatepick.getText();
			
			int rowCount=table.getRowCount();//获取总行数 如果为0  不执行
			
			if(rowCount==0){
				MsgBoxUtils.showMessageDialog1("错误", "请新增数据进行导出");
//				System.out.print("请新增数据进行导出");
				return;
			}
			boolean isExit=isExist(table);
			if(isExit){				//验证表内是否存在重复
				MsgBoxUtils.showMessageDialog1("错误", "列表内存在相同的行数据，请确认");
//				System.out.print("请新增数据进行导出");
				return;
			}
			if(rowCount!=0&&!isExit){
				//判断是否存在相同的行
				//判断行内是否存在数据表
				int columnCount=table.getColumnCount();//读取列
				List<Map<String,String>> inArr=new ArrayList<Map<String,String>>();//{1{parentTab:tableName,parentValue：tableVal,childTab:entryTableName}}
				
				for(int i=0;i<rowCount; i++){
					Map entryMap=new HashMap();
					if(table.getValueAt(i, 0)!=null && table.getValueAt(i, 1)!=null && table.getValueAt(i, 2)!=null && table.getValueAt(i, 3)!=null){
						String tableName=table.getValueAt(i, 0).toString();
						String tableVal=table.getValueAt(i, 1).toString();
						String entryTableName=table.getValueAt(i, 2).toString();
						String entryVal=table.getValueAt(i, 3).toString();
						
						
						
						if("".equals(tableName)){
							MsgBoxUtils.showMessageDialog1("消息", "第"+i+"行存在错误，请填写要导出的表名");
							return;
						}
						if("".equals(tableVal)){
							if(!"".equals(entryTableName)||!"".equals(entryVal)){
								MsgBoxUtils.showMessageDialog1("消息", "第"+i+"行存在错误，主值为空时，不需要填写子表表名和子值");
								return;
							}
						}else{
							if("".equals(entryTableName)||"".equals(entryVal)){
								MsgBoxUtils.showMessageDialog1("消息", "第"+i+"行存在错误，主值不为空时，必须填写子表表名和子值");
								return;
							}
						}
						
						entryMap.put("parentTab", tableName);
						entryMap.put("parentValue", tableVal);
						entryMap.put("childTab", entryTableName);
						entryMap.put("childValue", entryVal);
						
						inArr.add(entryMap);
//						System.out.print("当前行  第"+i+"验证"+tableName+"值"+tableVal+"子表"+entryTableName+"子表值"+entryVal+"\r\n");
					}
					
				}
				
				//读取eas返回的数据
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
						//处理返回数据  poi写入到excel 
						if(mapEmpDatas==null){
							MsgBoxUtils.showMessageDialog("提示", "请联系管理员，程序异常");
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
								MsgBoxUtils.showMessageDialog("提示", "导出数据为空，请检查导出条件");
								return;
							}else{
								MsgBoxUtils.showMessageDialog("提示", "导出成功");
								return;
							}
						}
					}else if(msg.equals("F")){
						String error=jsonMap.get("errorMessage").toString();
						LogUtils.writeLog("数据表导出数据存在错误："+error+" \r\n");
						MsgBoxUtils.showMessageDialog("提示", "检查系统日志");
						return;
					}
					
				}else if(code.equals("500")){
					String error=empMap.get("result").toString();
					MsgBoxUtils.showMessageDialog1("提示", error);
					return;
				}
				

				
				
			}
		}else if("导入表".equals(clickName)){
			
			
			String fileURL=getFileURL();
			if(!"".equals(fileURL)){
				String fromDate=this.startDatepick.getText();
				String toDate=this.endDatepick.getText();
				Map<String,List<Map<String,String>>> maps=POIUtils.readSheetsData(fileURL,1);//读取解析excel文件  是否过滤不存在字段
				if(maps.size()==0){
					MsgBoxUtils.showMessageDialog("提示", "请选择正确的导入模板");
					return;
				}
				
//				WebServiceTestUtils.exeInsertSql(maps);//eas接口调用
				Map impMap=impExcelAction("ORACLE",fromDate,toDate,maps);
				String code=impMap.get("code").toString();
				if(code.equals("200")){//返回信息
					String jsonDats=impMap.get("result").toString();
					com.alibaba.fastjson.JSONObject jsonMap=com.alibaba.fastjson.JSON.parseObject(jsonDats);
					String msg=jsonMap.get("success").toString();
					if(msg.equals("T")){
						MsgBoxUtils.showMessageDialog("提示", "导入成功");
						return;
					}
					if(msg.equals("F")){
						String resultDatas=jsonMap.get("resultData").toString();
						LogUtils.writeLog("部分表导入异常信息："+resultDatas+" \r\n");
						MsgBoxUtils.showMessageDialog("提示", "部分表导入异常，请检查日志信息");
						return ;
					}
					
					
				}else if(code.equals("500")){//程序异常
					String error=impMap.get("result").toString();
					MsgBoxUtils.showMessageDialog1("提示", error);
					return;
				}
				
				
				
				
				
				
			}else{
				return;
			}
			
			
			
		}
	
	}
	
	
	
	/*
	 * 导入sheet表
	 */
	public Map impExcelAction(String dbType,String startDate,String endDate,Map<String,List<Map<String,String>>> maps){
		
		LogUtils.writeLog("=======================导入sheet表 开始========================================  \r\n");
		LogUtils.writeLog("读取Excelip地址"+this.ipAdress+" \r\n");
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
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导入EXCEL  sebservice 异常："+e+"\r\n");
			
		} catch (WSInvokeException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导入EXCEL  sebservice 异常："+e+"\r\n");
			
		} catch (RemoteException e) {
			msgMap.put("code", "500");
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导入EXCEL  sebservice 异常："+e+"\r\n");
			
		}
		
		LogUtils.writeLog("======================导入sheet表 结束================================ \r\n");
		return msgMap;
		
		
	}
	/*
	 * 读取数据填充 Excel 
	 */
	public Map getEASDataByWebService(List<Map<String,String>> arr,String startDate,String endDate){
		LogUtils.writeLog("=================================读取连接webservice 开始============================== \r\n");
		LogUtils.writeLog("读取Excelip地址"+this.ipAdress+" \r\n");
		
		
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
			
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导出数据  sebservice 异常："+e+"\r\n");
			
		} catch (WSInvokeException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导出数据  sebservice 异常："+e+"\r\n");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			msgMap.put("code", "500");
			
			msgMap.put("result", "请求异常，请联系工程师查看错误日志");
			LogUtils.writeLog("导出数据  sebservice 异常："+e+"\r\n");
		}
		
		LogUtils.writeLog("=================================读取连接webservice 结束============================== \r\n");
		return msgMap;		
	}
	
	
	
	
}

