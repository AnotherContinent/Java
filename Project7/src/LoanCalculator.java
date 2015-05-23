

public class LoanCalculator implements java.io.Serializable {
  private double interestRate;
  private double years;
  private double amount;
  private double monthlyPayment;
  private double totalPayment;
  private double months;
  private double monthlyRate;

  public LoanCalculator(double interestRate, double years, double amount) {
    this.interestRate = interestRate;
    this.years = years;
    this.amount = amount;

  }

  public double getRate() {
    return interestRate;
  }

  public double getYears() {
    return years;
  }

  public double getAmount() {
    return amount;
  }

  public double getMonthly(){
	  interestRate = interestRate/100;
	  monthlyRate = interestRate/12;
	  months = years*12;
	  monthlyPayment = (amount*monthlyRate) / (1-Math.pow(1+monthlyRate, -months));
	  return monthlyPayment;
  }
  
  public double getTotal(){
	  totalPayment = monthlyPayment *12*years;
	  return totalPayment;
  }
}
