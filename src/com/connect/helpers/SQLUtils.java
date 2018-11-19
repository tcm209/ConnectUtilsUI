package com.connect.helpers;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLUtils {
	
	
	private static Connection con;
    private static String url;
    private static String user;
    private static String pwd;
	
	static {
        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");/*如果是MySQL就改为Class.forName("com.mysql.jdbc.Driver");*/
            Class.forName("jdbc:sqlserver://192.168.0.217:1433;DatabaseName=ecology");
            InputStream is = SQLUtils.class.getResourceAsStream("/resources/db.properties");//db.properties 是一个用户配置文件传用户名密码bin 文件下的文件
            Properties prop=new Properties();
            prop.put("remarksReporting", "true");
            prop.load(is);
            url=prop.getProperty("url");
            user=prop.getProperty("user");
            pwd=prop.getProperty("password");
            
            con = DriverManager.getConnection(url, prop);
        }catch (Exception e){
        	e.printStackTrace();
        }
    }
    

 
	public static ResultSet find(String sql){
	        con=getCon();
	        try {
	            Statement smt=con.createStatement();
	            ResultSet rs=smt.executeQuery(sql);
	            return rs;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	 }
	
	public static ResultSet find1(String sql){
		
		con=getCon();
        try {
            Statement smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=smt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	  
	public static ResultSet find(String sql,Object ...pram){//...pram数组
        con=getCon();
        try {
            PreparedStatement smt=con.prepareStatement(sql);
            for (int i=0;i<pram.length;i++){
                smt.setObject(i+1,pram[i]);
            }
            ResultSet rs=smt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	public static Connection getCon(){
        try {
            if(con==null||con.isClosed())
                con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
	
	
	public static void main(String[] args) throws SQLException {

		Connection cn=getCon();
		 Statement smt=cn.createStatement();
		 smt.execute("select * from select * FROM AccountSynchroTab");
		 
		
	}
	

}
