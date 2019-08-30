package com.kundy.cranberry.systemdesign.designpattern.command;

/**
 * 命令接口【可以理解为 Runnable】
 *
 * @author kundy
 * @date 2019/6/1 10:58 PM
 */
public interface Command {

    void execute();

    void undo();

}
