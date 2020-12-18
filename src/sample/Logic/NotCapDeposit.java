package sample.Logic;

public class NotCapDeposit extends Deposit {
    public NotCapDeposit() {};
    public NotCapDeposit(String client,int amount,Time time,double percent,double income)
    {

        super(client, amount, time, percent,income);
        this.cap = false;
    }



    public NotCapDeposit(int amount,Time time,double percent)
    {
        super(amount,time,percent);
        this.cap = false;
    }

    public int getDays(){
        switch (this.time)
        {
            case year1:return 365;
            case year2:return  2 *365;
            case year3:return 3*365;
            case year5:return 5*365;
            case month1:return 30;
            case month3:return 3*30;
            case month6:return 6*30;
        }
        return 0;
    }

    @Override
    public double getIncome() {
        return (amount*percent*getDays()/ 365)/100;
    }
}
