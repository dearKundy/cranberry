package com.kundy.cranberry.systemdesign.designpattern.observer;

/**
 * @author kundy
 * @date 2019/6/1 12:30 PM
 */
public class StatisticsDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    /*
     * 为了取消注册做准备
     */
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        display();
    }

}
