package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 9:23 AM
 */
public class RemoteControlTest {

    public static void main(String[] args) {
        SimpleRemoteControl remoteControl = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);

        remoteControl.setSlot(lightOnCommand);
        remoteControl.buttonWasPressed();
    }

}
