package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 9:22 AM
 */
public class SimpleRemoteControl {

    private Command slot;

    public void setSlot(Command slot) {
        this.slot = slot;
    }

    public void buttonWasPressed() {
        slot.execute();
    }

}
