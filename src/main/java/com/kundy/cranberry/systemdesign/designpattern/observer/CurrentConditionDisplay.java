package com.kundy.cranberry.systemdesign.designpattern.observer;

/**
 * 其实我觉得，Observer与DisplayElement两个接口可以直接合并成一个Observer接口，其实就是订阅者，
 * 但出于MVC设计模式，可以增加一个DisplayElement接口
 * Observer接口中的核心方法update，该方法会被主题调用
 *
 * @author kundy
 * @date 2019/6/1 12:26 PM
 */
public class CurrentConditionDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    /**
     * 为了取消注册做准备
     */
    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        // 将当前Observer注册到weatherData主题中
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current condition:" + temperature + "F degrees and " + humidity + "%humidity");
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }

}
