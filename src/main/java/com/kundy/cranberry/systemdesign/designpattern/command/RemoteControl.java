package com.kundy.cranberry.systemdesign.designpattern.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 【可以理解为线程池的实现-存储任务，消费任务】
 *
 * @author kundy
 * @date 2019/6/2 9:59 AM
 */
public class RemoteControl {

    private List<Command> onCommands = new ArrayList<>();
    // 前一个执行的命令被记录在这里，后进先出
    private Deque<Command> undoCommandStack = new ArrayDeque<>();

    public void addCommand(Command onCommand) {
        onCommands.add(onCommand);
    }

    public void onButtonWasPushed(int slot) {
        Command currentCommand = onCommands.get(slot);
        currentCommand.execute();
        undoCommandStack.push(currentCommand);
    }

    public void undoButtonWasPushed() {
        if (undoCommandStack.size() > 0) {
            undoCommandStack.pop().undo();
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n----- Remote Control -----\n");
        for (int i = 0; i < onCommands.size(); i++) {
            buffer.append("[slot]" + i + "]" + onCommands.get(i).getClass().getName());
        }
        return buffer.toString();
    }

}
