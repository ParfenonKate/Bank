package sample.Logic;

public class YearDeposit extends Deposit{
    public YearDeposit() {};
    public YearDeposit(String client,int amount,Time time,double percent,double income)
    {
        super(client, amount, time, percent,income);
        this.time = Time.year1;
    }



    public YearDeposit(int amount,Time time,double percent)
    {
        super(amount,time,percent);
        this.time = Time.year1;

    }
    @Override
    public double getIncome() {
        return (amount * percent)/ 100;
    }
}
