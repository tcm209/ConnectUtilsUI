package _1._0._0._127.ormrpc.services.WSTableOperFacade;

public class WSTableOperFacadeSrvProxyProxy implements _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy {
  private String _endpoint = null;
  private _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy wSTableOperFacadeSrvProxy = null;
  
  public WSTableOperFacadeSrvProxyProxy() {
    _initWSTableOperFacadeSrvProxyProxy();
  }
  
  public WSTableOperFacadeSrvProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSTableOperFacadeSrvProxyProxy();
  }
  
  private void _initWSTableOperFacadeSrvProxyProxy() {
    try {
      wSTableOperFacadeSrvProxy = (new _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxyServiceLocator()).getWSTableOperFacade();
      if (wSTableOperFacadeSrvProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSTableOperFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSTableOperFacadeSrvProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSTableOperFacadeSrvProxy != null)
      ((javax.xml.rpc.Stub)wSTableOperFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy getWSTableOperFacadeSrvProxy() {
    if (wSTableOperFacadeSrvProxy == null)
      _initWSTableOperFacadeSrvProxyProxy();
    return wSTableOperFacadeSrvProxy;
  }
  
  public java.lang.String getColumnCount(java.lang.String jsonData) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException{
    if (wSTableOperFacadeSrvProxy == null)
      _initWSTableOperFacadeSrvProxyProxy();
    return wSTableOperFacadeSrvProxy.getColumnCount(jsonData);
  }
  
  public java.lang.String importTable(java.lang.String jsonData, java.lang.String dateFrom, java.lang.String dateTo) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException{
    if (wSTableOperFacadeSrvProxy == null)
      _initWSTableOperFacadeSrvProxyProxy();
    return wSTableOperFacadeSrvProxy.importTable(jsonData, dateFrom, dateTo);
  }
  
  public java.lang.String exportTable(java.lang.String jsonData, java.lang.String dateFrom, java.lang.String dateTo) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException{
    if (wSTableOperFacadeSrvProxy == null)
      _initWSTableOperFacadeSrvProxyProxy();
    return wSTableOperFacadeSrvProxy.exportTable(jsonData, dateFrom, dateTo);
  }
  
  
}