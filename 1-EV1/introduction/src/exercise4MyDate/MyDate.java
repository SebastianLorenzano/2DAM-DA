package exercise4MyDate;

public class MyDate
{
    private int year;
    private int month;
    private int day;

    public MyDate() {
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setMonth(int month) throws IllegalArgumentException
    {
        if (month <= 0 || month > 12)
            throw new IllegalArgumentException(Utils.MONTH_NOT_VALID);
        this.month = month;
    }

    public void setDay(int day) throws IllegalArgumentException
    {
        if (!isDayValid(day))
            throw new IllegalArgumentException(Utils.DAY_NOT_VALID);
    }

    private boolean isDayValid(int day)
    {
        if (day > 0 || day <= 31)
            return true;
        if (day == 31 && monthHas31())
            return true;
        if (day == 30 || day == 29 && month != 2)
            return true;
        if (day isYearLeap())
            return true;
        return false;
    }

    private boolean monthHas31()
    {
        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12;
    }

    private boolean isYearLeap()
    {
        return true;
    }
}
