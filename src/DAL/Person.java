package DAL;

import java.util.Date;

/**
 *
 * @author caothanh
 */
public class Person {

    String firstName, lastName;
    int personId;
    String enrollmentDate;

    public Person() {
    }
    
    
    public Person(int personId,String firstName, String lastName, String enrollmentDate) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollmentDate = enrollmentDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    
    
    
    
    
    
}
