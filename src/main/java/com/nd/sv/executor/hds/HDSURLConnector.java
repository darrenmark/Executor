package com.nd.sv.executor.hds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;


/**
 * Created with IntelliJ IDEA.
 * User: narens
 * Date: 10/20/12
 * Time: 7:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class HDSURLConnector
{

    private String strHost;
    private int iPort = 2001;
    private String strUserName;
    private String strPassword;
    private String strServiceName;
    private String strServletName;
    private boolean isSecured = false;
    private HttpURLConnection objHttpURLConn = null;



    private static  String GET_SERVER_INFO = "<Get target=\"ServerInfo\"/>";

    private static  String GET_ALL_ARRAYS = "<StorageArray>\n" + "</StorageArray>\n" ;

    private static  String GET_ALL_ARRAYS_GROUPS ="<StorageArray objectID=\"ARRAY.D800H.87011902\">\n" + "<ArrayGroup>\n" + "</ArrayGroup>\n" + "</StorageArray>\n";




    /**
     *
     * @param strHost          Hostname of the DeviceManager Server
     * @param iPort            port on which the HiCommand™ Device Manager Server is listening
     * @param strUserName      username for authentication against the HiCommand Device Manager ACL
     * @param strPassword      password for authentication against the HiCommand Device Manager ACL
     * @param strServiceName   service against which the our Java™ application will POST an XML request
     * @param isSecured        boolean denoting whether or not the communication will use SSL/TLS
     */

    public HDSURLConnector(String strHost,
                           int iPort,
                           String strUserName,
                           String strPassword,
                           String strServiceName,
                           String strServletName,
                           boolean isSecured)
    {
        this.strHost = strHost;
        this.iPort = iPort;
        this.strUserName = strUserName;
        this.strPassword = strPassword;
        this.strServiceName = strServiceName;
        this.strServletName = strServletName;
        this.isSecured = isSecured;

    }


    public URL getHiCmdServerURL()  throws Exception
    {
        URL objURL = null;
        if (isSecured)
        {
            initForSSL();
            objURL = new URL(HDSConstants.HTTPS_PROTOCOL, strHost, iPort, getServletNameInRightStyle());
        }
        else
        {
            objURL = new URL(HDSConstants.HTTP_PROTOCOL, strHost, iPort, getServletNameInRightStyle());
        }
        return objURL;
    }

    private void initForSSL()
    {
        // set the trustStore
        try
        {
            System.getProperties().put("javax.net.ssl.trustStore", HDSConstants.SERVER_CERTS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // set the protocol handler
        try
        {
            System.getProperties().put("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // set the security provider
        try
        {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String getServletNameInRightStyle()
    {
        String strServletInRightStyle = HDSConstants.HITACHI_SERVLET_FORMAT + strServletName;
        return strServletInRightStyle;
    }

    /**
     *
     * @param objHitachiServerURL
     * @return
     * @throws Exception
     */


    public HttpURLConnection getHttpURLConnection(URL objHitachiServerURL) throws Exception
    {

        try
        {
            // Prepare to post the message to the URL - use an HttpURLConnection  for convenience
            objHttpURLConn = (HttpURLConnection) objHitachiServerURL.openConnection();
            if (objHttpURLConn == null)
            {
                //TODO  Log the message
            }


        }
        catch (Exception e)
        {
            // Try to re-connect logic need to implemented
            // if any exception happens

        }

        return objHttpURLConn;
    }

    /**
     *
     * @param objHttpURLConn
     * @param strXMLRequest
     * @return
     * @throws Exception
     */

    public HttpURLConnection setURLConnectionProperties(HttpURLConnection objHttpURLConn, String strXMLRequest) throws Exception
    {

        // set input OK
        objHttpURLConn.setDoInput(true);

        // set output OK
        objHttpURLConn.setDoOutput(true);

        // set caching off
        objHttpURLConn.setUseCaches(false);

        // Setting Connection Time Out
        objHttpURLConn.setConnectTimeout(HDSConstants.HTTP_CONNECTION_TIME_OUT);

        // Setting ReadTime Out

        objHttpURLConn.setReadTimeout(HDSConstants.HTTP_READ_TIME_OUT);

        // tell the server the content length of the XML message
        // extra steps for POSTing...
        if (strXMLRequest.length() > -1)
        {
            objHttpURLConn.setRequestProperty("Content-Length", Integer.toString(strXMLRequest.length()));
        }

        // tell the server the content type compatible with XML
        objHttpURLConn.setRequestProperty("Content-Type", HDSConstants.CONTENT_TYPE);

        // The "Accept" header must be set. The Accept header specifies the  MIME types that your client
        // can accept. For demonstration, our client can accept anything.
        objHttpURLConn.setRequestProperty("Accept", "*/*");

        // Set the Authorization header. This will be required when a document  requested from HiCommand
        // requires authentication.
        // use the sun BASE64 Encoder for demonstration purposes
        // note that there is a space between Basic and the base64 encoded authorization string

        sun.misc.BASE64Encoder b64encoder = new sun.misc.BASE64Encoder();
        String authorization = b64encoder.encode((strUserName + ":" + strPassword).getBytes());
        objHttpURLConn.setRequestProperty("Authorization", "Basic " + authorization);

        return objHttpURLConn;
    }

    /**
     *
     * @param objHttpURLConn
     * @param strXMLRequest
     * @return
     * @throws Exception
     */


    public String getXMLResponseForPostRequest(HttpURLConnection objHttpURLConn, String strXMLRequest) throws Exception
    {


        PrintWriter objPrintWriter = null;
        try
        {
            objPrintWriter = new PrintWriter(objHttpURLConn.getOutputStream());
            // send the xml message to the server
            objPrintWriter.println(strXMLRequest);
            // follow with a blank line
            objPrintWriter.println("");
            objPrintWriter.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (objPrintWriter != null)
                objPrintWriter.close();
        }

        // open a pipe to read the server response
        BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objHttpURLConn.getInputStream()));
        // Something to read the server response
        String inputLine = "";
        System.out.println("RESPONSE");
        System.out.println("==========");

        // read the server response, one line at a time and display it to  user
        StringBuffer objXMLResponseBuffer = new StringBuffer();
        while ((inputLine = objBufferedReader.readLine()) != null)
        {
            objXMLResponseBuffer.append(inputLine);
            //  System.out.println(inputLine);
        }

        System.out.println("==========");
        // Close the reading connection to the HiCommand Server
        objBufferedReader.close();
        // Close the HTTP URL Connection
        // objHttpURLConn.disconnect();
        return objXMLResponseBuffer.toString();
    }

    public void disconnectURLConnection()
    {
        if(objHttpURLConn !=null)
        {
            // Close the HTTP URL Connection
            objHttpURLConn.disconnect();
        }
    }

    public String executeXMLRequest(HDSURLConnector objHitachiURLConnector,String strXMLRequest) throws Exception
    {
        URL objURL = objHitachiURLConnector.getHiCmdServerURL();
        HttpURLConnection objHttpURLConnection = objHitachiURLConnector.getHttpURLConnection(objURL);
        objHttpURLConnection = objHitachiURLConnector.setURLConnectionProperties(objHttpURLConnection,strXMLRequest);
        String strXMLResponse  = objHitachiURLConnector.getXMLResponseForPostRequest(objHttpURLConnection,strXMLRequest);
        objHitachiURLConnector.disconnectURLConnection();
        return  strXMLResponse;
    }




    public static void main(String[] args)
    {
        try
        {
            HDSURLConnector objHitachiURLConnector = new HDSURLConnector("172.16.1.152", 2001, "system", "manager", "ServerAdmin", "ServerAdmin", false);
            HDSXMLRequestCreator objRequestCreator = new HDSXMLRequestCreator(HDSConstants.HITACHI_BASE_DEVICE_MGR_VERSION);
            String strServerInfo = objRequestCreator.getServerInfoRequestString(GET_SERVER_INFO);
            System.out.println(strServerInfo);
            String strGetServerInfo = objHitachiURLConnector.executeXMLRequest(objHitachiURLConnector, strServerInfo);
            System.out.println(strGetServerInfo);
            String strStorageArrayInfo = objRequestCreator.getStorageArrayRequest(GET_ALL_ARRAYS);
            String strGetALLArray = objHitachiURLConnector.executeXMLRequest(objHitachiURLConnector, strStorageArrayInfo);
            System.out.println(strGetALLArray);
            String strArrayGroupInfo = objRequestCreator.getStorageArrayRequest(GET_ALL_ARRAYS_GROUPS);
            String strGetALLArrayGroups = objHitachiURLConnector.executeXMLRequest(objHitachiURLConnector,strArrayGroupInfo);
            System.out.println(strGetALLArrayGroups);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
