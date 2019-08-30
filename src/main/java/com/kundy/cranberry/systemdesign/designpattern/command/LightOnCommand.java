package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 9:20 AM
 */
public class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }

}
