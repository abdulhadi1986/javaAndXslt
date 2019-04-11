package chap6;

/**
 * A helper class that stores personal information. XML generation
 * is intentionally left out of this class. This class ensures
 * that its data cannot be null, nor can it contain extra
 * whitespace.
 */
public class PersonalData {
    private String firstName;
    private String lastName;
    private String daytimePhone;
    private String eveningPhone;
    private String email;

    public PersonalData( ) {
        this("", "", "", "", "");
    }

    public PersonalData(String firstName, String lastName,
            String daytimePhone, String eveningPhone, String email) {
        this.firstName = cleanup(firstName);
        this.lastName = cleanup(lastName);
        this.daytimePhone = cleanup(daytimePhone);
        this.eveningPhone = cleanup(eveningPhone);
        this.email = cleanup(email);
    }

    /**
     * <code>eveningPhone</code> is the only optional field.
     *
     * @return true if all required fields are present.
     */
    public boolean isValid( ) {
        return this.firstName.length( ) > 0
                && this.lastName.length( ) > 0
                && this.daytimePhone.length( ) > 0
                && this.email.length( ) > 0;
    }

    public void setFirstName(String firstName) {
        this.firstName = cleanup(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = cleanup(lastName);
    }

    public void setDaytimePhone(String daytimePhone) {
        this.daytimePhone = cleanup(daytimePhone);
    }

    public void setEveningPhone(String eveningPhone) {
        this.eveningPhone = cleanup(eveningPhone);
    }

    public void setEmail(String email) {
        this.email = cleanup(email);
    }

    public String getFirstName( ) { return this.firstName; }
    public String getLastName( ) { return this.lastName; }
    public String getDaytimePhone( ) { return this.daytimePhone; }
    public String getEveningPhone( ) { return this.eveningPhone; }
    public String getEmail( ) { return this.email; }

    /**
     * Cleanup the String parameter by replacing null with an
     * empty String, and by trimming whitespace from non-null Strings.
     */
    private static String cleanup(String str) {
        return (str != null) ? str.trim( ) : "";
    }
}
