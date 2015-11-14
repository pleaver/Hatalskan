package hatalskan;

/**
 * Represents a student who has a first name, a last name, 
 * and a CalPoly Username
 * @author Door Guardians
 */
public class Student 
{
    private String id;
    private String firstName;
    private String lastName;
    
    /**
     * Constructor
     * @param id unique student id
     * @param name String array where index 0 is first name 
     * and index 1 is last name
     */
    public Student(String id, String[] name)
    {
        this.id = id;
        firstName = name[0];
        lastName = name[1];
    }
    
    /**
     * Gets the first name of the student
     * @return String first name
     */
    public String getFirstName() 
    {
        return firstName;
    }
    
    /**
     * Gets the last name of the student
     * @return String last name
     */
    public String getLastName() 
    {
        return lastName;
    }
            
    /**
     * Gets the student username
     * @return Returns the first and last names together
     */
    public String getFullName()
    {
        return firstName + " " + lastName;
    }
    
    /**
     * Gets the student id
     * @return Returns unique student CalPoly username
     */
    public String getId()
    {
        return id;
    }
}
