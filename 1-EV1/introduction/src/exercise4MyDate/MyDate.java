package exercise4MyDate;

public class MyDate
{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;

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

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec()
    {
        return sec;
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

    public boolean IsValid()
    {  // 1, 3, 5, 7, 8, 10, 12
        if (month > 12 || day > 31 || hour > 23 || min > 59 || sec > 59 ||
                month < 1 || day < 1 || hour < 0 || min < 0 || sec < 0)
            return false;
        if (_month == 2 && _day > 28)
        {
            if (IsLeap() && _day > 29)
                return false;

            if (!IsLeap() && _day > 28)
                return false;
        }

        if (_month == 2 || _month == 4 || _month == 6 || _month == 9 || _month == 11 && _day == 31)
            return false;
        return true;
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


