package com.connect.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class POIUtils {

	
//	public static void main(String[] args){
//		HSSFWorkbook wb=new HSSFWorkbook();
//		for(int i=0;i<3;i++){
//			HSSFSheet sheet=wb.createSheet("测试"+i);
//			String[] columnNames={"A","B","C"};
//			String[][] datas={
//					{"1","1","1"},
//					{"2","2","2"},
//			};
//			int[] columnNameWidths={5,5,5};
//			exportExcelModel("ceshi",sheet,wb,columnNames,datas,columnNameWidths,3);
//		}
//		
//		//输出到文件夹
//		String filename="sunrise12"+"date";
//		try {
//			
//			FileOutputStream fout = new FileOutputStream("e:\\"+filename+".xls");
//			wb.write(fout);
//			String str = "导出" + filename + "成功！";
//			System.out.println(str);
//			fout.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			String str1 = "导出" + filename + "失败！";
//			System.out.println(str1);
//		}
//				
//	}
	
	
	/*
	 * 导出eas数据至EXCEL
	 */
	public static boolean empExcelModel(String fileName,String fileURL,Map<String,List<Map<String,String>>> easDatas){
		LogUtils.writeLog("========================写入EXCEL  开始=======================\r\n");
		boolean b=false;
		HSSFWorkbook wb=new HSSFWorkbook();
		
		for(Map.Entry<String, List<Map<String,String>>> entry:easDatas.entrySet()){
			String tableName=entry.getKey();//表名
			HSSFSheet sheet=wb.createSheet(tableName);
			
			
			List<Map<String,String>> tableDataList=entry.getValue();
			int rowSize=tableDataList.size();
			if(rowSize!=0){
				if(!b){
					LogUtils.writeLog("========================存在要写入的EXCEL=======================\r\n");
					b=true;
				}
				
				//设置表头
				Map<String,String> tableHeadDataMap=tableDataList.get(0);//field value
				int coulumnSize=tableHeadDataMap.size();//列数量
				String[] columnNames=new String[coulumnSize];
				int[] columnNameWidths=new int[coulumnSize];
				int fieldIndex=0;//列数量
				
				for(String field:tableHeadDataMap.keySet()){
					columnNames[fieldIndex]=field;
					columnNameWidths[fieldIndex]=field.length();
					fieldIndex++;
				}
				
				List dataArr=new ArrayList();
				
				//数据
				for(int i=0;i<rowSize;i++){
					Map<String,String> tableDataMap=tableDataList.get(i);//field  ==》value
					dataArr.add(tableDataMap);
				}
				exportExcelModel(tableName,sheet,wb,columnNames,dataArr,columnNameWidths,coulumnSize);
			}
			
			//填充数据 
			
			
			
		}
		//输出到文件夹
		if(b){
			try {
				
				FileOutputStream fout = new FileOutputStream(fileURL+"\\"+fileName+".xls");
				wb.write(fout);
				String str = "导出" + fileName + "成功！";
				
				LogUtils.writeLog(str+"\r\n");
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
				String str1 = "导出" + fileName + "失败！";
				LogUtils.writeLog("导出失败"+str1+"exception:"+e.getStackTrace()+"\r\n");
			}
		}
		LogUtils.writeLog("====================写入EXCEL结束===========================================\r\n");
		return b;
	}
	/*
	 * 填充sheet数据
	 */
	public static void exportExcelModel(String sheetName,HSSFSheet sheet,HSSFWorkbook wb,String[] fields,List<Map<String,Object>> datas,int[] fieldNameWidth,int columnNumber){
		//设置表头列宽
		for(int i=0;i<columnNumber; i++){
			sheet.setColumnWidth(i, fieldNameWidth[i]*400);
			
		}
		
		//创建第一行
		//创建第一行
		HSSFRow row=sheet.createRow(0);
		row.setHeightInPoints(37);//标题高度
		//列名单元格样式
		CellStyle cellStyle=wb.createCellStyle();
		setFirstRowStyle(wb,cellStyle);
		
		for(int i=0; i<columnNumber; i++){
			HSSFCell cellHead=row.createCell(i);
			
			cellHead.setCellValue(fields[i]);
			cellHead.setCellStyle(cellStyle);
		}
		CellStyle dataCellStyle =setDataCellStyle(wb);
		//填充数据
		for(int i=0;i<datas.size();i++){
			
			row=sheet.createRow(i+1);
			// 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
			
			HSSFCell datacell = null;
			for (int j = 0; j < columnNumber; j++) 
			{
				Object obj=datas.get(i).get(fields[j]);
				datacell = row.createCell(j);
				if(obj==null){
					datacell.setCellValue("");
				}else{
					datacell.setCellValue(obj.toString());
				}
				
				datacell.setCellStyle(dataCellStyle);

			}
		}
		
		
		
	}
	public static void setFirstRowStyle(HSSFWorkbook wb,CellStyle cellStyle){
		cellStyle.setWrapText(true);//设置自动换行
		
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setLocked(true);//设置锁
		//边框颜色
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		//设置边框
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		HSSFFont headFont=wb.createFont();
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short)10);
		cellStyle.setFont(headFont);
	}
	/*
	 * 设置表头样式
	 */
	public static CellStyle setHeadCellStyle(HSSFWorkbook wb){
		CellStyle cellStyle=wb.createCellStyle();
		cellStyle.setLocked(true);//设置为只读
		
		cellStyle.setWrapText(true);//设置自动换行
		cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		return cellStyle;
		
	}
	
	
	
	

	/*
	 * 设置数据填充列样式
	 */
	public static CellStyle setDataCellStyle(HSSFWorkbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setLocked(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

		// 设置边框
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	
	/*
	 * 解析EXCEL文件内数据
	 * map<表名,(数据)list<Map<field,Value>>
	 */
	public static Map<String,List<Map<String,String>>> readSheetsData(String fileUrl,int isAdd){
		LogUtils.writeLog("读取EXCEL 开始=================================\r\n");
		Map<String,List<Map<String,String>>> maps=new HashMap<String,List<Map<String,String>>>();
		try {
			
			//线程每读两张表  进行调用EAS 批量新增动作   降低时间    读的同时 进行新增   
			FileInputStream imp=new FileInputStream(new File(fileUrl));
			HSSFWorkbook wb=new HSSFWorkbook(imp);
			HSSFSheet sheet=null;
			for(int i=0;i<wb.getNumberOfSheets(); i++){//获取sheet
				sheet=wb.getSheetAt(i);
				String tableName=sheet.getSheetName();
				
				HSSFRow headRow=sheet.getRow(0);
				int columnNumber=headRow.getLastCellNum();
				
				Object[] headCells=new Object[columnNumber];
				for(int m=0; m<columnNumber; m++){//读取Excel列
					HSSFCell cellHead=headRow.getCell(m);
					headCells[m]=cellHead;
				}
				List list=new ArrayList();
				for(int j=1; j<sheet.getPhysicalNumberOfRows(); j++){//获取row
					
					HSSFRow row=sheet.getRow(j);
					if(row!=null){
						Map datas=new HashMap();
						for(int k=0; k<columnNumber;k++){//获取cellg  etPhysicalNumberOfCells()返回的结果是不包含空列的行数
							HSSFCell cell=row.getCell(k);
							datas.put(headCells[k].toString(), cell.toString());//存放列名对应的数据

						}
						list.add(datas);
						
						if(datas.size()==0){
							LogUtils.writeLog("读取EXCEL 数据数据集为空=================================\r\n");
//							
						}
					}else{
						LogUtils.writeLog("读取EXCEL 数据数据集为空=================================\r\n");
					}
					
					
				}
				String key=tableName+","+columnNumber+","+isAdd;
				maps.put(key, list);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtils.writeLog("读取EXCEL结束=================================\r\n");
		return maps;
		
	}
	

	
	
	

}
