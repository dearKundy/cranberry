package com.kundy.cranberry.javabasis.rmi.basicversion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author kundy
 * @date 2019/10/12 6:54 PM
 */
public class RmiClient {

    public static void main(String[] args) {
        try {
            // 获取registry的存根 默认端口1099
            Registry registry = LocateRegistry.getRegistry("localhost");
            // 从服务器registry中获得远程对象的存根
            RemoteHelloWord helloWord = (RemoteHelloWord) registry.lookup("hiService");
            String ret = helloWord.sayHello();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
