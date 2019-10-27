package com.kundy.cranberry.javabasis.rmi.zkversion;

import com.kundy.cranberry.javabasis.rmi.basicversion.RemoteHelloWord;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author kundy
 * @date 2019/10/13 9:00 PM
 */
public class ServiceConsumer {

    private static ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);

    private static final String RMI_PROVIDER_BASE_PATH = "/rmiRegistry";

    private static volatile List<String> hostList;

    static {
        hostList = getServiceInfo();
        zkClient.subscribeChildChanges(RMI_PROVIDER_BASE_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                hostList = getServiceInfo();
            }
        });
    }

    //TODO 需要在服务启动的前完成所有远程对象的初始化【订阅服务】
    private static Remote lookup(String serviceName) {
        try {
            //TODO 假如存在多个相同的生产者，可以实现负载均衡
            Registry registry = LocateRegistry.getRegistry(hostList.get(0));
            return registry.lookup(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static List<String> getServiceInfo() {
        List<String> childrenPaths = zkClient.getChildren(RMI_PROVIDER_BASE_PATH);
        for (String childrenPath : childrenPaths) {
            hostList.add(zkClient.readData(RMI_PROVIDER_BASE_PATH + "/" + childrenPath));
        }
        return hostList;
    }

    public static void main(String[] args) throws RemoteException {
        RemoteHelloWord helloWord = (RemoteHelloWord) lookup("hiService");
        System.out.println(helloWord.sayHello());
    }
}
