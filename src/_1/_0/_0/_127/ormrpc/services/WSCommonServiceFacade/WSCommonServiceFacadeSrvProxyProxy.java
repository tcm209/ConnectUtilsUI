package _1._0._0._127.ormrpc.services.WSCommonServiceFacade;

public class WSCommonServiceFacadeSrvProxyProxy implements _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxy {
  private String _endpoint = null;
  private _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxy wSCommonServiceFacadeSrvProxy = null;
  
  public WSCommonServiceFacadeSrvProxyProxy() {
    _initWSCommonServiceFacadeSrvProxyProxy();
  }
  
  public WSCommonServiceFacadeSrvProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSCommonServiceFacadeSrvProxyProxy();
  }
  
  private void _initWSCommonServiceFacadeSrvProxyProxy() {
    try {
      wSCommonServiceFacadeSrvProxy = (new _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxyServiceLocator()).getWSCommonServiceFacade();
      if (wSCommonServiceFacadeSrvProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSCommonServiceFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSCommonServiceFacadeSrvProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSCommonServiceFacadeSrvProxy != null)
      ((javax.xml.rpc.Stub)wSCommonServiceFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public _1._0._0._127.ormrpc.services.WSCommonServiceFacade.WSCommonServiceFacadeSrvProxy getWSCommonServiceFacadeSrvProxy() {
    if (wSCommonServiceFacadeSrvProxy == null)
      _initWSCommonServiceFacadeSrvProxyProxy();
    return wSCommonServiceFacadeSrvProxy;
  }
  
  public java.lang.String getManufactureOrders(java.lang.String params) throws java.rmi.RemoteException, CommonServiceFacade.client.WSInvokeException{
    if (wSCommonServiceFacadeSrvProxy == null)
      _initWSCommonServiceFacadeSrvProxyProxy();
    return wSCommonServiceFacadeSrvProxy.getManufactureOrders(params);
  }
  
  public java.lang.String auditManfac() throws java.rmi.RemoteException, CommonServiceFacade.client.WSInvokeException{
    if (wSCommonServiceFacadeSrvProxy == null)
      _initWSCommonServiceFacadeSrvProxyProxy();
    return wSCommonServiceFacadeSrvProxy.auditManfac();
  }
  
  
}