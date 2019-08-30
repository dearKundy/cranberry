package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * @author kundy
 * @date 2019/6/2 10:01 AM
 */
public class StereoOnCommand implements Command{

    private Stereo stereo;

    public StereoOnCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
    }

    @Override
    public void undo() {
        stereo.off();
    }

}
