package com.kundy.cranberry.systemdesign.designpattern.observer;

/**
 * @author kundy
 * @date 2019/6/1 12:36 PM
 */
public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        // 其实这一步相当于把currentDisplay（订阅者）注册到weatherData（主题）中，当然我觉得通过weather.register 这种方式会好点
        CurrentConditionDisplay currentDisplay = new CurrentConditionDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        // 触发主题变化
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }

}
