package sample.Logic;

public class CapDeposit extends Deposit {

    public CapDeposit() {};
    public CapDeposit(String client,int amount,Time time,double percent,double income)
    {
        super(client, amount, time, percent,income);
        this.cap = true;
    }



    public CapDeposit(int amount,Time time,double percent)
    {
        super(amount,time,percent);
        this.cap = true;
    }

    public int getMonths()
    {
        switch (time)
        {
            case month3:return 3;
            case month1:return 1;
            case year5:return 5*12;
            case year3:return 3*12;
            case year2:return 2*12;
            case year1:return 12;
            case month6:return 6;
        }
        return 0;
    }
    public double getIncome() {
        return (amount * Math.pow(1+(percent/100)/12, getMonths())  - amount);
    }
}
