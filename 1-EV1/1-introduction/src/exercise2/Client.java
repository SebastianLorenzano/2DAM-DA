package exercise2;


import java.util.HashSet;

public class Client extends Person // Doesn't ask for external access to isClientOf
{
    private String number;
    private HashSet<Company> isClientof = new HashSet<>();

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String value) throws IllegalArgumentException
    {
        String regex = "^((\\+|00)\\d{2,3})?\\d{9}$";
        if (value != null && value.matches(regex))
            number = value;
        else throw new IllegalArgumentException(Utils.CELLNUMBER_NOT_VALID);
    }


}
