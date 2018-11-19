/**
 * WSTableOperFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package _1._0._0._127.ormrpc.services.WSTableOperFacade;

public class WSTableOperFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxyService {

    public WSTableOperFacadeSrvProxyServiceLocator() {
    }


    public WSTableOperFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSTableOperFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSTableOperFacade
    private java.lang.String WSTableOperFacade_address = "http://127.0.0.1:56898/ormrpc/services/WSTableOperFacade";

    public java.lang.String getWSTableOperFacadeAddress() {
        return WSTableOperFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSTableOperFacadeWSDDServiceName = "WSTableOperFacade";

    public java.lang.String getWSTableOperFacadeWSDDServiceName() {
        return WSTableOperFacadeWSDDServiceName;
    }

    public void setWSTableOperFacadeWSDDServiceName(java.lang.String name) {
        WSTableOperFacadeWSDDServiceName = name;
    }

    public _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy getWSTableOperFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSTableOperFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSTableOperFacade(endpoint);
    }

    public _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy getWSTableOperFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSoapBindingStub _stub = new _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSTableOperFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSTableOperFacadeEndpointAddress(java.lang.String address) {
        WSTableOperFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (_1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSoapBindingStub _stub = new _1._0._0._127.ormrpc.services.WSTableOperFacade.WSTableOperFacadeSoapBindingStub(new java.net.URL(WSTableOperFacade_address), this);
                _stub.setPortName(getWSTableOperFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSTableOperFacade".equals(inputPortName)) {
            return getWSTableOperFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSTableOperFacade", "WSTableOperFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSTableOperFacade", "WSTableOperFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSTableOperFacade".equals(portName)) {
            setWSTableOperFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
