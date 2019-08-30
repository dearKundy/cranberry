package com.kundy.cranberry.systemdesign.designpattern.observer;

/**
 * @author kundy
 * @date 2019/6/1 12:28 PM
 */
public class ForecastDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    /*
     * 为了取消注册做准备
     */
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {

    }

    @Override
    public void update(float temp, float humidity, float pressure) {

    }

}
