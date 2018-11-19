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
			e.printStackTrace();
			msg="��ȷ��������Ϣ�Ƿ���ȷ";
		} catch (RemoteException e) {
			msg="���ʾܾ�����ȷ��������Ϣ";
			// TODO Auto-generated catch block
			
		}
		return msg;

	}
	

}
