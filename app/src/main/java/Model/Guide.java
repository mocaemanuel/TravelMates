package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Guide extends User implements Serializable {

    public String firstName;
    public String lastName;
    public String email;
    public String residence;
    public int age;
    public String passwd;
    public String startDateAvailable;
    public String endDateAvailable;

    private ArrayList<String> hobbies;
    private ArrayList<String> areasOfInterest;

    public Guide(String fN, String lN, String e, int a, String p)
    {
        this.firstName=fN;
        this.lastName=lN;
        this.email=e;
        this.age=a;
        this.passwd=p;
    }

    public Guide(){}

    public void setStartDateAvailable(String newStartDate){
        this.startDateAvailable = newStartDate;
    }

    public void setEndDateAvailable(String newEndDate){
        this.endDateAvailable = newEndDate;
    }

    @Override
    public String getType(){
        return "Guide";
    }

    @Override
    public void setHobbies(ArrayList hobbies)
    {
        this.hobbies=hobbies;

    }

    @Override
    public ArrayList getHobbies()
    {
        return hobbies;

    }

    @Override
    public void setAreaOfInterest(ArrayList areaOfInterest)
    {
        this.areasOfInterest=areaOfInterest;

    }

    @Override
    public ArrayList getAreaOfInterest()
    {
        return areasOfInterest;

    }

    public String getStartDateAvailable()
    {
        return this.startDateAvailable;
    }
    public String getEndDateAvailable()
    {
        return this.endDateAvailable;
    }

    public int getAge()
    {
        return this.age;
    }

    public String getCounty()
    {
        return this.getCounty();
    }

    public String getResidence()
    {
        return this.getResidence();
    }

    @Override
    public String toString()
    {
        String result=null;
        result+="First name: "+this.firstName
                +"\n Last name: "+this.lastName
                +"\n Email: "+this.email
                +"\n Residence: "+ this.residence
                +"\n Age: "+ Integer.toString(age)
                +"\n Start Date Available: "+this.startDateAvailable
                +"\n End Date Available: "+this.endDateAvailable;

        result+="\n Hobbies: \n";
        for(int i=0;i<hobbies.size();i++)
        {
            result+=hobbies.get(i);
        }
        result+="\n Areas of interest: \n";
        for(int j=0;j<areasOfInterest.size();j++)
        {
            result+=areasOfInterest.get(j);
        }
        result+="\n";

        return result;

    }



}
