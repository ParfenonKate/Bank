package sample.Logic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties({"capital", "timeconverted","incomeFinal" ,"days","months"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class Deposit {
    public Deposit() {};
    public enum Time {year1, month1, month3,month6,year2,year3,year5;}

    public int id;//id депозита / его номер
    public String client; //Фио клиента
    public int amount;//Сумма вклада
    public Time time;//Срок вклада
    public double percent;//Процент по вкладу
    public boolean cap;//Есть ли капитализация
    public double income;


    //Конструктор полный для бд
    public Deposit(String client,int amount,Time time,double percent,double income)
    {
        this.client=client;
        this.amount=amount;
        this.time=time;
        this.percent=percent;
        this.cap = cap;
        this.income = income;
    }


    //Конструктор для расчета
    public Deposit(int amount,Time time,double percent)
    {
        this.amount=amount;
        this.time=time;
        this.percent=percent;

    }



//============Геттеры и сеттеры==============
    public void setId(int id) {
        this.id = id;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setCap(boolean cap) {
        this.cap = cap;
    }

    public int getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public int getAmount() {
        return amount;
    }

    public Time getTime() {
        return time;
    }

    public double getPercent() {
        return percent;
    }

    public boolean isCap() {
        return cap;
    }

    public double getIncome() {
        return 0;
    }

}
