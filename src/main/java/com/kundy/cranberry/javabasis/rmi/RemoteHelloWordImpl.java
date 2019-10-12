package com.kundy.cranberry.javabasis.rmi;

import java.rmi.RemoteException;

/**
 * @author kundy
 * @date 2019/10/11 10:02 PM
 */
public class RemoteHelloWordImpl implements RemoteHelloWord {

    @Override
    public String sayHello() throws RemoteException {
        return "Hello Word";
    }

}
