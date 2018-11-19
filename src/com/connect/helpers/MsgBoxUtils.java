package com.connect.helpers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MsgBoxUtils {
	
	
	public static void showMessageDialog(Object obj,String param){
//		JOptionPane.showMessageDialog(null, "������ʾ");  
		JOptionPane.showMessageDialog(null, param);  
	}
	
	public static void showMessageDialog(String title,String content){
//		JOptionPane.showMessageDialog(getPanel(), "��ʾ��Ϣ", "����",JOptionPane.WARNING_MESSAGE); 
		JOptionPane.showMessageDialog(null, content, title,JOptionPane.WARNING_MESSAGE);
	}

	public static void showMessageDialog1(String errorTitle,String errorContent){
		JOptionPane.showMessageDialog(null, errorContent, errorTitle,JOptionPane.ERROR_MESSAGE); 
		
	}
	/*
	 * i=0/1    
	 */
	public static int showConfirmDialog(String title,String content){
		
		int n = JOptionPane.showConfirmDialog(null, content, title,JOptionPane.YES_NO_OPTION);
		return n;
		
	}

	/*
	 * ����ѡ���
	 */
	public static int showConfirmDialog(Object[] options,String title,String content){
//		Object[] options ={ "�ð���", "ȥһ�ߣ�" };    
		int m = JOptionPane.showOptionDialog(null, content, title,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);  
		return m;
	}
	
	/*
	 * ����object ���Object ����һ����һ��String ���ͣ���Ӧ���û�������
	 */
	public static Object showConfirmDialog(Object[] options,String title,String content,String content1){
		Object[] obj2 ={ "����", "����", "ƹ����" };    
		String s = (String) JOptionPane.showInputDialog(null,content, title, JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), obj2, content1);   
		return s;
	}
	
	
	public static void showInputDialog(String content,String title,ImageIcon icon,String inputStr){
		JOptionPane.showInputDialog(null,content,title,JOptionPane.PLAIN_MESSAGE,icon,null,inputStr);
	}
	
	
}
