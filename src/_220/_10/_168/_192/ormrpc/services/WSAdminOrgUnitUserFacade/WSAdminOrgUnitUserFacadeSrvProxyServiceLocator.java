/**
 * WSAdminOrgUnitUserFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade;

public class WSAdminOrgUnitUserFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxyService {

    public WSAdminOrgUnitUserFacadeSrvProxyServiceLocator() {
    }


    public WSAdminOrgUnitUserFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSAdminOrgUnitUserFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSAdminOrgUnitUserFacade
    private java.lang.String WSAdminOrgUnitUserFacade_address = "http://192.168.10.220:6886/ormrpc/services/WSAdminOrgUnitUserFacade";

    public java.lang.String getWSAdminOrgUnitUserFacadeAddress() {
        return WSAdminOrgUnitUserFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSAdminOrgUnitUserFacadeWSDDServiceName = "WSAdminOrgUnitUserFacade";

    public java.lang.String getWSAdminOrgUnitUserFacadeWSDDServiceName() {
        return WSAdminOrgUnitUserFacadeWSDDServiceName;
    }

    public void setWSAdminOrgUnitUserFacadeWSDDServiceName(java.lang.String name) {
        WSAdminOrgUnitUserFacadeWSDDServiceName = name;
    }

    public _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy getWSAdminOrgUnitUserFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSAdminOrgUnitUserFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSAdminOrgUnitUserFacade(endpoint);
    }

    public _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy getWSAdminOrgUnitUserFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSoapBindingStub _stub = new _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSAdminOrgUnitUserFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSAdminOrgUnitUserFacadeEndpointAddress(java.lang.String address) {
        WSAdminOrgUnitUserFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (_220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSoapBindingStub _stub = new _220._10._168._192.ormrpc.services.WSAdminOrgUnitUserFacade.WSAdminOrgUnitUserFacadeSoapBindingStub(new java.net.URL(WSAdminOrgUnitUserFacade_address), this);
                _stub.setPortName(getWSAdminOrgUnitUserFacadeWSDDServiceName());
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
        if ("WSAdminOrgUnitUserFacade".equals(inputPortName)) {
            return getWSAdminOrgUnitUserFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.10.220:6886/ormrpc/services/WSAdminOrgUnitUserFacade", "WSAdminOrgUnitUserFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.10.220:6886/ormrpc/services/WSAdminOrgUnitUserFacade", "WSAdminOrgUnitUserFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSAdminOrgUnitUserFacade".equals(portName)) {
            setWSAdminOrgUnitUserFacadeEndpointAddress(address);
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
