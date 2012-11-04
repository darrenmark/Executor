package com.nd.sv.executor.hds;


/**
 * Created with IntelliJ IDEA.
 * User: narens
 * Date: 10/21/12
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class HDSXMLRequestCreator
{

    private String strHiCmdVersion = HDSConstants.HITACHI_BASE_DEVICE_MGR_VERSION;

    /**
     *
     * @param strHiCmdVersion - DeviceManger version
     */

    public HDSXMLRequestCreator(String strHiCmdVersion)
    {
        this.strHiCmdVersion = strHiCmdVersion;
    }

    public String getStrHiCmdVersion()
    {
        return strHiCmdVersion;
    }

    public void setStrHiCmdVersion(String strHiCmdVersion)
    {
        this.strHiCmdVersion = strHiCmdVersion;
    }

    public String getXMLRequestHeader()
    {
        String strXMLRequestHeader = "<?xml version=\"1.0\"?>\n" +
                "<HiCommandServerMessage>\n" +
                "<APIInfo version=\"" + strHiCmdVersion + "\"/>\n" +
                "<Request>";
        return strXMLRequestHeader;
    }

    public String getXMLRequestFooter()
    {
        String strXMLRequestFooter =  "</Request>\n" + "</HiCommandServerMessage>";
        return strXMLRequestFooter;
    }


    public String getStorageMgrHeader()
    {

        String strStorageMgrHeader = "<StorageManager>\n" + "<Get target=\"StorageArray\">";
        return strStorageMgrHeader;
    }

    public String getStorageMgrFooter()
    {

        String strStorageMgrFooter = "</Get>\n" + "</StorageManager>";
        return strStorageMgrFooter;
    }


    public String getServerMgrHeader()
    {

        String strServerMgrHeader = "<ServerAdmin>";
        return strServerMgrHeader;
    }

    public String getServerMgrFooter()
    {

        String strServerMgrFooter = "</ServerAdmin>";
        return strServerMgrFooter;
    }

    public String addHeaderAndFooter(String strXMLRequest,String strType)
    {
        StringBuffer objRequest = new StringBuffer();
        objRequest.append(getXMLRequestHeader());
        if(strType.equals(HDSConstants.HITACHI_STORAGE_MANAGER))
        {
            objRequest.append(getStorageMgrHeader()) ;
        }
        else
        {
            objRequest.append(getServerMgrHeader()) ;
        }
        objRequest.append(strXMLRequest);
        if(strType.equals(HDSConstants.HITACHI_STORAGE_MANAGER))
        {
            objRequest.append(getStorageMgrFooter()) ;
        }
        else
        {
            objRequest.append(getServerMgrFooter()) ;
        }
        objRequest.append(getXMLRequestFooter());
        return objRequest.toString();

    }

    public String getStorageArrayRequest(String strXMLRequest)
    {
        String strStorageArrayRequest = addHeaderAndFooter(strXMLRequest,HDSConstants.HITACHI_STORAGE_MANAGER);
        return strStorageArrayRequest;

    }

    public String getServerInfoRequestString(String strXMLRequest)
    {
        String strServerInfoRequestString = addHeaderAndFooter(strXMLRequest,HDSConstants.HITACHI_SERVER_MANAGER);
        return strServerInfoRequestString;
    }

    public String getAllStorageArrayRequestString()
    {
        StringBuffer objStorageArrayRequest = new StringBuffer();
        objStorageArrayRequest.append(getXMLRequestHeader());
        objStorageArrayRequest.append(getStorageMgrHeader());
        objStorageArrayRequest.append(getStorageMgrFooter());
        objStorageArrayRequest.append(getXMLRequestFooter());
        return objStorageArrayRequest.toString();
    }







}
