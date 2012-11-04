package com.nd.sv.executor;

import com.jamesmurty.utils.XMLBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: darrenm
 * Date: 10/23/12
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLBuilderTest {

    public static void main(String[] args) throws Exception {

        System.out.println(XMLBuilder.create("HiCommandServerMessage").a("APIInfo","5.5").e("Request").e("GetData").asString());
    }

}
