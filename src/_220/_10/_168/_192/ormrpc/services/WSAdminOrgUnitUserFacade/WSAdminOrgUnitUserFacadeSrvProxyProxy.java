package _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade;

public class WSAdminOrgUnitUserFacadeSrvProxyProxy implements _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy {
  private String _endpoint = null;
  private _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy wSAdminOrgUnitUserFacadeSrvProxy = null;
  
  public WSAdminOrgUnitUserFacadeSrvProxyProxy() {
    _initWSAdminOrgUnitUserFacadeSrvProxyProxy();
  }
  
  public WSAdminOrgUnitUserFacadeSrvProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSAdminOrgUnitUserFacadeSrvProxyProxy();
  }
  
  private void _initWSAdminOrgUnitUserFacadeSrvProxyProxy() {
    try {
      wSAdminOrgUnitUserFacadeSrvProxy = (new _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxyServiceLocator()).getWSAdminOrgUnitUserFacade();
      if (wSAdminOrgUnitUserFacadeSrvProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSAdminOrgUnitUserFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSAdminOrgUnitUserFacadeSrvProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSAdminOrgUnitUserFacadeSrvProxy != null)
      ((javax.xml.rpc.Stub)wSAdminOrgUnitUserFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy getWSAdminOrgUnitUserFacadeSrvProxy() {
    if (wSAdminOrgUnitUserFacadeSrvProxy == null)
      _initWSAdminOrgUnitUserFacadeSrvProxyProxy();
    return wSAdminOrgUnitUserFacadeSrvProxy;
  }
  
  public java.lang.String getUserByAdminOrg(java.lang.String solutionInfo) throws java.rmi.RemoteException, adminorgunituserfacade.client.WSInvokeException{
    if (wSAdminOrgUnitUserFacadeSrvProxy == null)
      _initWSAdminOrgUnitUserFacadeSrvProxyProxy();
    return wSAdminOrgUnitUserFacadeSrvProxy.getUserByAdminOrg(solutionInfo);
  }
  
  
}