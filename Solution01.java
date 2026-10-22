package Solution01;

import java.util.*;

class Person {
    protected String firstName;


    protected String lastName;


    protected int idNumber;


    public Person(String firstName, String lastName, int identification) {

        this.firstName = firstName;

        this.lastName = lastName;

        this.idNumber = identification;
    }

    public void printPerson() {

        System.out.println("Name: " + lastName + ", " + firstName + "\nID: " + idNumber);
    }


}


class Student extends Person {

    private int[] testScores;

    public Student(String firstName, String lastName, int identification , int[] testScores){
        super(firstName, lastName, identification);

        this.testScores = testScores;
    }

    public char calculate(){
        double media = 0;
        for(int i = 0 ; i < testScores.length ; i++){
            media += testScores[i];
        }
        media = (media/testScores.length);
        if(media > 89 && media < 101)
            return 'O';
        else if(media < 90 && media > 79){
            return 'E';
        }
        else if(media < 80 && media > 69){
            return 'A';
        }
        else if(media < 70 && media > 54)
            return 'P';
        else if(media < 55 && media > 39){
            return 'D';
        }
        else if(media < 40)
            return 'T';
        return 'C';
    }

}

public class Solution01 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String firstName = scan.next();

        String lastName = scan.next();

        int id = scan.nextInt();

        int numScores = scan.nextInt();

        int[] testScores = new int[numScores];

        for (int i = 0; i < numScores; i++) {
            testScores[i] = scan.nextInt();
        }
        scan.close();

        Student s = new Student(firstName, lastName, id, testScores);

        s.printPerson();

        System.out.println("Grade: " + s.calculate());
    }
}
