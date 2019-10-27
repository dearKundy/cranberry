package com.kundy.cranberry.javabasis.rmi.zkversion;

import com.kundy.cranberry.javabasis.rmi.basicversion.RemoteHelloWordImpl;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 参考文章：https://my.oschina.net/huangyong/blog/345164
 *
 * @author kundy
 * @date 2019/10/13 3:52 PM
 */
public class ServiceProvider {

    private static ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);

    private static final String RMI_PROVIDER_BASE_PATH = "/rmiRegistry";

    public static void publishRmiService(Remote remote, String serviceName) {
        if (publishRmiService(remote, serviceName, "", 0)) {
            createProviderInfoZNode(serviceName, "localhost");
        }
    }

    //TODO 需要在服务启动之前完成所有服务的发布

    /**
     * 发布 rmi 服务
     */
    private static Boolean publishRmiService(Remote remote, String serviceName, String host, int port) {
        try {
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(serviceName, UnicastRemoteObject.exportObject(remote, 9999));
            LOGGER.info("publish service：{} successfully！", serviceName);
            return true;
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 新建保存服务提供者信息 ZNode
     */
    private static void createProviderInfoZNode(String serviceName, String serviceHost) {
        try {
            byte[] data = serviceName.getBytes();
            zkClient.createEphemeralSequential(RMI_PROVIDER_BASE_PATH + "/" + serviceName, serviceHost);
            LOGGER.info("create ZNode {} successfully!", serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        publishRmiService(new RemoteHelloWordImpl(), "hiService");
    }

}
