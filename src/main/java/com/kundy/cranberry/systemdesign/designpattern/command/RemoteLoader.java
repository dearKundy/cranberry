package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 10:02 AM
 */
public class RemoteLoader {

    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light();
        Stereo stereo = new Stereo();

        LightOnCommand lightOnCommand = new LightOnCommand(light);
        StereoOnCommand stereoOnCommand = new StereoOnCommand(stereo);

        remoteControl.addCommand(lightOnCommand);
        remoteControl.addCommand(stereoOnCommand);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.onButtonWasPushed(1);
        remoteControl.onButtonWasPushed(1);

        // 撤销测试
        remoteControl.undoButtonWasPushed();
        remoteControl.undoButtonWasPushed();
        remoteControl.undoButtonWasPushed();
        remoteControl.undoButtonWasPushed();

    }

}
