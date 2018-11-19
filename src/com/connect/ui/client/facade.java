package com.connect.ui.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import client.WSContext;
import adminorgunituserfacade.client.WSInvokeException;
import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxy;
import _1._0._0._127.ormrpc.services.EASLogin.EASLoginProxyServiceLocator;
import _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxy;
import _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxyServiceLocator;
import _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy;
import _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxyProxy;
import _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxyServiceLocator;

public class facade {

	public static void main(String[] args) throws ServiceException, WSInvokeException, RemoteException {
		// TODO Auto-generated method stub
		connectionEAS("wucl"," ","http://127.0.0.1:56898","sunrise12","oracle");
		WSCommonServiceFacadeSrvProxyServiceLocator locatro=new WSCommonServiceFacadeSrvProxyServiceLocator();
		locatro.setWSCommonServiceFacadeEndpointAddress("http://127.0.0.1:56898/ormrpc/services/WSCommonServiceFacade");
		WSCommonServiceFacadeSrvProxy proxy=locatro.getWSCommonServiceFacade();
		proxy.getManufactureOrders("asda");
		
		
	}
	public static String connectionEAS(String loginid,String pwd,String ipadress,String dbname,String dbtype) {
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
			e.printStackTrace();
			msg="请确认所填信息是否正确";
		} catch (RemoteException e) {
			msg="访问拒绝，请确定所填信息";
			// TODO Auto-generated catch block
			
		}
		return msg;

	}
	

}
