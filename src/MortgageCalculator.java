
import java.text.NumberFormat;
import java.util.Scanner;
public class MortgageCalculator {
    final static byte MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;
    final static int MIN_PRINCIPAL =1_000;
    final static int MAX_PRINCIPAL=1_000_000;
    final static int MIN_INTEREST =1;
    final static int MAX_INTEREST=30;

    final static int MIN_YEARS =1;
    final static int MAX_YEARS=30;

    private int principal;
    private float annualInterest;
    private byte years;

    public MortgageCalculator() {
        askUserForAllInfo();
    }

    public MortgageCalculator(int principal, float annualInterest, byte years) {
        setPrincipal(principal);
        setAnnualInterest(annualInterest);
        setYears(years);
    }

    public static void main(String[] args) {
        System.out.println("\tRunning with default constructor");
        MortgageCalculator mortgageCalculator = new MortgageCalculator();
        mortgageCalculator.printMortgage();
        mortgageCalculator.printPaymentSchedule();
        System.out.println("\tRunning with full constructor");
        MortgageCalculator mortgageCalculator1 = new MortgageCalculator(100_000, 10,(byte)10);
        mortgageCalculator1.printMortgage();
        mortgageCalculator1.printPaymentSchedule();
        System.out.println("\tRunning with full constructor with invalid data");
        MortgageCalculator mortgageCalculator2= new MortgageCalculator(1, 10,(byte)10);
        mortgageCalculator2.printMortgage();
        mortgageCalculator2.printPaymentSchedule();
    }
    public int getPrincipal() {
        return principal;
    }
    public void setPrincipal(int principal) {
        if (principal < MIN_PRINCIPAL || principal > MAX_PRINCIPAL) {
            try {
                throw new InputInvalidException("Invalid Principal in set principle");
            }catch(InputInvalidException e) {
                System.out.println(e.getMessage());
            }
        }else{
            this.principal = principal;
        }
    }
    public float getAnnualInterest() {
        return annualInterest;
    }
    public void setAnnualInterest(float annualInterest) {
        if(annualInterest < MIN_INTEREST || annualInterest > MAX_INTEREST){
            try {
                throw new InputInvalidException("Invalid Interest");
            }catch(InputInvalidException e) {
                System.out.println(e.getMessage());
            }
        }else{
            this.annualInterest = annualInterest;
        }
    }
    public byte getYears() {
        return years;
    }
    public void setYears(byte years) {
        if(years<MIN_YEARS||years>MAX_YEARS){
            try {
                throw new InputInvalidException("Invalid Years");
            }catch(InputInvalidException e) {
                System.out.println(e.getMessage());
            }
        }else{
            this.years = years;
        }
    }


    public void askUserForPrincipal(){
        setPrincipal((int)readNumber("Please enter your principal (1k - 1m):", MIN_PRINCIPAL, MAX_PRINCIPAL));
    }
    public void askUserForAnnualInterest(){
        setAnnualInterest((float)readNumber("Please enter your interest (1-30):", MIN_INTEREST, MAX_INTEREST));
    }
    public void askUserForYears(){
        setYears((byte) readNumber("Please enter the period of your mortgage in years (1, 30): ", MIN_YEARS, MAX_YEARS));
    }

    public void askUserForAllInfo(){
        askUserForPrincipal();
        askUserForAnnualInterest();
        askUserForYears();
    }

    public void printMortgage() {
        double mortgage = this.calculateMortgage();
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }
    public void printPaymentSchedule() {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        for (short month = 1; month <= this.years * MONTHS_IN_YEAR; month++) {
            double balance = this.calculateBalance(month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }
    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a value between " + min + " and " + max);
        }
        return value;
    }
    public double calculateBalance(
            short numberOfPaymentsMade
    ) {
        float monthlyInterest = this.annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = this.years * MONTHS_IN_YEAR;
        System.out.println( "monthly interest"+monthlyInterest);
        System.out.println("numberofpayments"+ numberOfPayments);
        double balance = this.principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return balance;
    }

    public double calculateMortgage() {
        float monthlyInterest = this.annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = this.years * MONTHS_IN_YEAR;
        double mortgage = this.principal
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return mortgage;
    }

    @Override
    public String toString() {
        double mortgage = this.calculateMortgage();
        return "MortgageCalculator{" +
                "principal=" + principal +
                ", annualInterest=" + annualInterest +
                ", years=" + years +
                ", mortgage=" + mortgage +
                '}';
    }
}