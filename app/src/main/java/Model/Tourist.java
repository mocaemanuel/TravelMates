package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tourist extends User implements Serializable {
    public String firstName;
    public String lastName;
    public String email;
    public int age;
    public String passwd;
    public ArrayList<String> hobbies;
    public ArrayList<String> areaOfInterest;

    public Tourist(String fName, String lName, String mail, int newAge, String newPasswd){
        this.firstName = fName;
        this.lastName = lName;
        this.email = mail;
        this.age = newAge;
        this.passwd = newPasswd;
    }

    public Tourist(){}

    @Override
    public void setHobbies(ArrayList hobbies){
        this.hobbies = hobbies;
    }

    @Override
    public String getType(){
        return "Tourist";
    }

    @Override
    public ArrayList getHobbies(){
        return this.hobbies;
    }

    @Override
    public void setAreaOfInterest(ArrayList areaOfInterest){
        this.areaOfInterest = areaOfInterest;
    }

    @Override
    public ArrayList getAreaOfInterest(){
        return this.areaOfInterest;
    }

    @Override
    public String toString(){
        String result=null;
        result+="First name: "+this.firstName
                +"\n Last name: "+this.lastName
                +"\n Email: "+this.email
                +"\n Age: "+ Integer.toString(age);

        result+="\n Hobbies: \n";
        for(int i=0;i<hobbies.size();i++)
        {
            result+=hobbies.get(i);
        }
        result+="\n Areas of interest: \n";
        for(int j=0;j<this.areaOfInterest.size();j++)
        {
            result+=areaOfInterest.get(j);
        }
        result+="\n";

        return result;
    }

}
