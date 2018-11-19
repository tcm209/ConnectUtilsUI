package com.connect.helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/*
 * 模拟返回数据
 */
public class WebServiceTestUtils {
	
	private static List inList=new ArrayList();//存放子表查询条件
	
	/*
	 * 模拟接口数据
	 */
	public static Map<String,List<Map<String,String>>> getEASTestDatas(Map<Integer,Map<String,String>> inMap){
		
		Map<String,List<Map<String,String>>> datas=new HashMap<String,List<Map<String,String>>>();
		for(Map<String,String> mapTable:inMap.values()){
			inList.clear();//清除list内的数据
			String tableName=mapTable.get("parentTab");
			String tableVal=mapTable.get("parentValue");//关联字段
			String[] columns=getColumns(tableName,"oracle");
			String startDate=mapTable.get("dateFrom");
			String endDate=mapTable.get("dateTo");
			int dataSize=0;
			if(!"".equals(tableVal)){//主值为空 则不存在关联表
				List tabDatas=getDatas(tableName,tableVal,null,columns,startDate,endDate,false);
				dataSize=tabDatas.size();
				if(dataSize!=0){
					datas.put(tableName, tabDatas);
				}
				
				//子表查询
				switchInStr(inList);
				String in = StringUtils.join(inList.toArray(), ",");
				String entryTableName=mapTable.get("childTab");
				String entryVal=mapTable.get("childValue");
				String[] columnsEntry=getColumns(entryTableName,"oracle");
				
				List entryDatas=getDatas(entryTableName,in,entryVal,columnsEntry,null,null,true);
				dataSize=entryDatas.size();
				if(dataSize!=0){
					datas.put(entryTableName, entryDatas);
				}
				
			}else{
				List tabDatas=getDatas(tableName,tableVal,null,columns,startDate,endDate,false);
				dataSize=tabDatas.size();
				if(dataSize!=0){
					datas.put(tableName, tabDatas);
				}
				
			}
			
		}
		
		
		
		return datas;
		
	}
	
	/*
	 * 测试导入
	 */
	public static void exeInsertSql(Map<String,List<Map<String,String>>> map){
		List allSql=new ArrayList();
		
		for(Map.Entry<String, List<Map<String,String>>> m:map.entrySet()){
			
			
			String tableName=m.getKey();//表名
			
			List datas=m.getValue();//表对应的数据
			int size=datas.size();
			for(int i=0;i<size;i++){//循环行 
				Map<String,String> valueMap=(Map<String, String>) datas.get(i);
				StringBuffer sb=new StringBuffer();
				StringBuffer valuesSql=new StringBuffer();
				StringBuffer datasSql=new StringBuffer();
				sb.append("INSERT INTO ");
				sb.append(tableName);
				
				valuesSql.append("(");
				datasSql.append(" Values(");
				int columnSize=valueMap.size();
				int j=0;
				for(Map.Entry<String, String> val:valueMap.entrySet()){
					j++;
					Object columnName=val.getKey();
					Object columnVal=val.getValue();
					if(j==columnSize){
						valuesSql.append(""+columnName+"");
						datasSql.append("'"+columnVal+"'");
					}else{
						valuesSql.append(""+columnName+",");
						datasSql.append("'"+columnVal+"',");
					}
					
					
				}
				valuesSql.append(")");
				datasSql.append(")");
				sb.append(valuesSql);
				sb.append(datasSql);
				sb.append("\r\n");
				//拼接sql语句
				writeTxtFile(sb.toString());
				allSql.add(sb);
			}
			
		}
		System.out.print("数据数量【"+allSql+"】\r\n");
	}
	
	
	/*
	 * 读取数据表数据
	 */
	private static List<Map<String,String>> getDatas(String param,String param1,String param2,String[] columns,String fromDate,String toDate,boolean isEntry){
		
		int coulumnSize=columns.length;
		
		StringBuffer sb=new StringBuffer();
		sb.append("select ");
		
		for(int i=0;i<coulumnSize;i++){
			if(i==coulumnSize-1){
				sb.append(""+columns[i]+"");
			}else{
				sb.append(""+columns[i]+",");
			}
		}
		sb.append(" from "+param+"");
		if(isEntry){//子表不存在创建时间
			sb.append(" where "+param2+" in ("+param1+")");//'1','2'
		}else{
			sb.append(" where TO_CHAR(FCreateTime,'yyyy-MM-dd') between '"+fromDate+"' and '"+toDate+"'");
		}
		
		
		
		List list=new ArrayList();
		ResultSet rs=SQLUtils.find(sb.toString());
		try {
			
			while(rs.next()){//读取行  
				Map<String,String> addData=new HashMap<String,String>();
				for(int i=0;i<coulumnSize;i++){//读取列
					rs.getString(columns[i]);
					addData.put(columns[i], rs.getString(columns[i]));
					if(!isEntry){
						if(param1.equalsIgnoreCase(columns[i])){//判断关联表关联字段  
							inList.add(rs.getString(columns[i]));
						}
					}
					
				}
				list.add(addData);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	/*
	 * 获取列
	 */
	private static String[] getColumns(String tableName,String dbType){
		String sql="";
		if(dbType.equals("oracle")){
			sql="SELECT tabColType.COLUMN_NAME FROM user_tab_columns tabColType WHERE tabColType.TABLE_NAME =upper('"+tableName+"')";
		}else if(dbType.equals("SQLSERVER")){
			sql="select b.name  COLUMN_NAME  from sysobjects a inner join syscolumns b on a.id=b.id and a.xtype='U' where a.name='"+tableName+"'";
		}
		
		ResultSet rs=SQLUtils.find1(sql);
		String[] columns=null;
		
		try {
			rs.last();
			int size=rs.getRow();
			columns=new String[size];
			rs.beforeFirst();
			int index=0;
			while(rs.next()){
				columns[index]=rs.getString("COLUMN_NAME");
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return columns;
		
	}
	/*
	 * 
	 */
	public static int getColumnSize(String tableName,String dbType){
		int size=0;
		String sql="";
		if(dbType.equals("oracle")){
			sql="SELECT tabColType.COLUMN_NAME FROM user_tab_columns tabColType WHERE tabColType.TABLE_NAME =upper('"+tableName+"')";
		}else if(dbType.equals("SQLSERVER")){
			sql="select b.name  COLUMN_NAME  from sysobjects a inner join syscolumns b on a.id=b.id and a.xtype='U' where a.name='"+tableName+"'";
		}
		
		ResultSet rs=SQLUtils.find1(sql);
		try {
			rs.last();
			size=rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
	
	
	/*
	 * 获取where  in  字符串格式
	 */
	private static void switchInStr(List<String> list){
		for(int i=0; i<list.size(); i++){
			String tmpStr=list.get(i);
			tmpStr="'"+tmpStr+"'";
			list.set(i, tmpStr);
		}
	}
	
	/*
	 * 输出日志
	 */
	public static void writeTxtFile(String content){
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("E:/log.txt",true);
			fos.write(content.getBytes()); 
			fos.close();
	      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//true表示在文件末尾追加 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
