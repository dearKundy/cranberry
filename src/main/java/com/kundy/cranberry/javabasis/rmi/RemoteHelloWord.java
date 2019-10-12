package com.kundy.cranberry.javabasis.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口
 * <p>
 * - 远程接口必须继承 java.rmi.Remote 接口。
 * - 为了处理远程方法发生的各种异常，每一个远程方法必须抛出一个java.rmi.RemoteException异常。
 *
 * @author kundy
 * @date 2019/10/11 9:58 PM
 */
public interface RemoteHelloWord extends Remote {

    String sayHello() throws RemoteException;

}
