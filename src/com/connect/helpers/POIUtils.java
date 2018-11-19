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
//			HSSFSheet sheet=wb.createSheet("����"+i);
//			String[] columnNames={"A","B","C"};
//			String[][] datas={
//					{"1","1","1"},
//					{"2","2","2"},
//			};
//			int[] columnNameWidths={5,5,5};
//			exportExcelModel("ceshi",sheet,wb,columnNames,datas,columnNameWidths,3);
//		}
//		
//		//������ļ���
//		String filename="sunrise12"+"date";
//		try {
//			
//			FileOutputStream fout = new FileOutputStream("e:\\"+filename+".xls");
//			wb.write(fout);
//			String str = "����" + filename + "�ɹ���";
//			System.out.println(str);
//			fout.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			String str1 = "����" + filename + "ʧ�ܣ�";
//			System.out.println(str1);
//		}
//				
//	}
	
	
	/*
	 * ����eas������EXCEL
	 */
	public static boolean empExcelModel(String fileName,String fileURL,Map<String,List<Map<String,String>>> easDatas){
		LogUtils.writeLog("========================д��EXCEL  ��ʼ=======================\r\n");
		boolean b=false;
		HSSFWorkbook wb=new HSSFWorkbook();
		
		for(Map.Entry<String, List<Map<String,String>>> entry:easDatas.entrySet()){
			String tableName=entry.getKey();//����
			HSSFSheet sheet=wb.createSheet(tableName);
			
			
			List<Map<String,String>> tableDataList=entry.getValue();
			int rowSize=tableDataList.size();
			if(rowSize!=0){
				if(!b){
					LogUtils.writeLog("========================����Ҫд���EXCEL=======================\r\n");
					b=true;
				}
				
				//���ñ�ͷ
				Map<String,String> tableHeadDataMap=tableDataList.get(0);//field value
				int coulumnSize=tableHeadDataMap.size();//������
				String[] columnNames=new String[coulumnSize];
				int[] columnNameWidths=new int[coulumnSize];
				int fieldIndex=0;//������
				
				for(String field:tableHeadDataMap.keySet()){
					columnNames[fieldIndex]=field;
					columnNameWidths[fieldIndex]=field.length();
					fieldIndex++;
				}
				
				List dataArr=new ArrayList();
				
				//����
				for(int i=0;i<rowSize;i++){
					Map<String,String> tableDataMap=tableDataList.get(i);//field  ==��value
					dataArr.add(tableDataMap);
				}
				exportExcelModel(tableName,sheet,wb,columnNames,dataArr,columnNameWidths,coulumnSize);
			}
			
			//������� 
			
			
			
		}
		//������ļ���
		if(b){
			try {
				
				FileOutputStream fout = new FileOutputStream(fileURL+"\\"+fileName+".xls");
				wb.write(fout);
				String str = "����" + fileName + "�ɹ���";
				
				LogUtils.writeLog(str+"\r\n");
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
				String str1 = "����" + fileName + "ʧ�ܣ�";
				LogUtils.writeLog("����ʧ��"+str1+"exception:"+e.getStackTrace()+"\r\n");
			}
		}
		LogUtils.writeLog("====================д��EXCEL����===========================================\r\n");
		return b;
	}
	/*
	 * ���sheet����
	 */
	public static void exportExcelModel(String sheetName,HSSFSheet sheet,HSSFWorkbook wb,String[] fields,List<Map<String,Object>> datas,int[] fieldNameWidth,int columnNumber){
		//���ñ�ͷ�п�
		for(int i=0;i<columnNumber; i++){
			sheet.setColumnWidth(i, fieldNameWidth[i]*400);
			
		}
		
		//������һ��
		//������һ��
		HSSFRow row=sheet.createRow(0);
		row.setHeightInPoints(37);//����߶�
		//������Ԫ����ʽ
		CellStyle cellStyle=wb.createCellStyle();
		setFirstRowStyle(wb,cellStyle);
		
		for(int i=0; i<columnNumber; i++){
			HSSFCell cellHead=row.createCell(i);
			
			cellHead.setCellValue(fields[i]);
			cellHead.setCellStyle(cellStyle);
		}
		CellStyle dataCellStyle =setDataCellStyle(wb);
		//�������
		for(int i=0;i<datas.size();i++){
			
			row=sheet.createRow(i+1);
			// Ϊ�������������ص��µ�Ԫ����ʽ2 �Զ����� ���¾�������Ҳ����
			
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
		cellStyle.setWrapText(true);//�����Զ�����
		
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setLocked(true);//������
		//�߿���ɫ
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		//���ñ߿�
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		HSSFFont headFont=wb.createFont();
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headFont.setFontName("����");
		headFont.setFontHeightInPoints((short)10);
		cellStyle.setFont(headFont);
	}
	/*
	 * ���ñ�ͷ��ʽ
	 */
	public static CellStyle setHeadCellStyle(HSSFWorkbook wb){
		CellStyle cellStyle=wb.createCellStyle();
		cellStyle.setLocked(true);//����Ϊֻ��
		
		cellStyle.setWrapText(true);//�����Զ�����
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
	 * ���������������ʽ
	 */
	public static CellStyle setDataCellStyle(HSSFWorkbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);// �����Զ�����
		cellStyle.setLocked(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ����һ�����¾��и�ʽ
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���Ҿ���

		// ���ñ߿�
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	
	/*
	 * ����EXCEL�ļ�������
	 * map<����,(����)list<Map<field,Value>>
	 */
	public static Map<String,List<Map<String,String>>> readSheetsData(String fileUrl,int isAdd){
		LogUtils.writeLog("��ȡEXCEL ��ʼ=================================\r\n");
		Map<String,List<Map<String,String>>> maps=new HashMap<String,List<Map<String,String>>>();
		try {
			
			//�߳�ÿ�����ű�  ���е���EAS ������������   ����ʱ��    ����ͬʱ ��������   
			FileInputStream imp=new FileInputStream(new File(fileUrl));
			HSSFWorkbook wb=new HSSFWorkbook(imp);
			HSSFSheet sheet=null;
			for(int i=0;i<wb.getNumberOfSheets(); i++){//��ȡsheet
				sheet=wb.getSheetAt(i);
				String tableName=sheet.getSheetName();
				
				HSSFRow headRow=sheet.getRow(0);
				int columnNumber=headRow.getLastCellNum();
				
				Object[] headCells=new Object[columnNumber];
				for(int m=0; m<columnNumber; m++){//��ȡExcel��
					HSSFCell cellHead=headRow.getCell(m);
					headCells[m]=cellHead;
				}
				List list=new ArrayList();
				for(int j=1; j<sheet.getPhysicalNumberOfRows(); j++){//��ȡrow
					
					HSSFRow row=sheet.getRow(j);
					if(row!=null){
						Map datas=new HashMap();
						for(int k=0; k<columnNumber;k++){//��ȡcellg  etPhysicalNumberOfCells()���صĽ���ǲ��������е�����
							HSSFCell cell=row.getCell(k);
							datas.put(headCells[k].toString(), cell.toString());//���������Ӧ������

						}
						list.add(datas);
						
						if(datas.size()==0){
							LogUtils.writeLog("��ȡEXCEL �������ݼ�Ϊ��=================================\r\n");
//							
						}
					}else{
						LogUtils.writeLog("��ȡEXCEL �������ݼ�Ϊ��=================================\r\n");
					}
					
					
				}
				String key=tableName+","+columnNumber+","+isAdd;
				maps.put(key, list);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtils.writeLog("��ȡEXCEL����=================================\r\n");
		return maps;
		
	}
	

	
	
	

}
