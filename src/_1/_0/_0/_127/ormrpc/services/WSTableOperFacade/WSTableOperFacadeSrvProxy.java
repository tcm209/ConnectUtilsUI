/**
 * WSTableOperFacadeSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package _1._0._0._127.ormrpc.services.WSTableOperFacade;

public interface WSTableOperFacadeSrvProxy extends java.rmi.Remote {
    public java.lang.String getColumnCount(java.lang.String jsonData) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException;
    public java.lang.String importTable(java.lang.String jsonData, java.lang.String dateFrom, java.lang.String dateTo) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException;
    public java.lang.String exportTable(java.lang.String jsonData, java.lang.String dateFrom, java.lang.String dateTo) throws java.rmi.RemoteException, tableoperfacade.client.WSInvokeException;
}
