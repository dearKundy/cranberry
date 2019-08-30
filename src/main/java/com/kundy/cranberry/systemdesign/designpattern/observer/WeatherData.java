package com.kundy.cranberry.systemdesign.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 WeatherData 中实现主题接口
 *
 * @author kundy
 * @date 2019/6/1 12:21 PM
 * <p>
 * 核心思想：把所有的订阅者保存在主题中，然后主题发生变化时，调用多有订阅者的指定方法即可。
 */
public class WeatherData implements Subject {

    /*
     * 保存订阅者列表
     */
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    // 注册订阅者
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i > 0) {
            observers.remove(i);
        }
    }

    /**
     * 通知所有订阅者，其实就是调用所有Observer的update方法
     */
    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(temperature, humidity, pressure);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

}
