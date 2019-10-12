package com.kundy.cranberry.javabasis.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 参考文章：https://blog.csdn.net/suifeng3051/article/details/48469523
 * <p>
 * Java RMI 指的是远程方法调用 (Remote Method Invocation)。它是一种机制，能够让在某个 Java 虚拟机上的对象调用另一个 Java 虚拟机中的对象上的方法。
 * <p>
 * 编写一个RMI的步骤：
 * 1. 定义一个远程接口，此接口需要继承Remote。
 * 2. 开发远程接口的实现类。
 * 3. 创建一个server并把远程对象注册到端口。
 * 4. 创建一个client查找远程对象，调用远程方法。
 *
 * @author kundy
 * @date 2019/10/11 10:03 PM
 */
public class RmiServer {

    public static void main(String[] args) {
        try {
            RemoteHelloWord remoteHelloWord = new RemoteHelloWordImpl();

            /*
             * 远程对象必须被导出才能被远程调用者调用，它还会返回一个存根，这个存根将会发送给client端进行调用。
             * 当exportObject()方法被执行后，运行时会在一个新的Server Socket或共享Server Socket上进行监听，来接收对远程对象的远程调用。
             */
            RemoteHelloWord stub = (RemoteHelloWord) UnicastRemoteObject.exportObject(remoteHelloWord, 9999);

            /*
             * Java RMI 提供了registry API 可以允许应用程序把一个名称和远程对象的存根绑定在一起，这样client就可以通过这个绑定的名称很方便的查找到需要调用的远程对象了，
             * 在这里可以把registry看做是一个名称服务。
             */
            LocateRegistry.createRegistry(1099);

            Registry registry = LocateRegistry.getRegistry();

            // 返回的registry存根通过调用bind()方法在registry中把一个字符串名称和远程对象存根绑定在一起。
            registry.bind("helloworld", stub);
            System.out.println("绑定成功！");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}
