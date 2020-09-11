package es.objects;

enum departments {
    MATH,
    SCIENCE,
    CS,
    LITERATURE
}

public class Department {

    private int id;
    private departments name;

    public Department(String name){

        switch (name){

            case "MATH":
                this.name = departments.MATH;
                this.id = 1;
                break;
            case "SCIENCE":
                this.name = departments.SCIENCE;
                this.id = 2;
                break;
            case "CS":
                this.name = departments.CS;
                this.id = 3;
                break;
            case "LITERATURE":
                this.name = departments.LITERATURE;
                this.id = 4;
                break;

        }

    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name.toString();
    }

}
