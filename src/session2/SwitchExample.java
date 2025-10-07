package session2;

public class SwitchExample {
    public static void main(String[] args) {
        double ipk = 3.7;
        String grade;

        int ipkInt = (int)(ipk * 10);

        switch (ipkInt) {
            case 40: case 39: case 38: case 37: case 36: case 35:
                grade = "A";
                break;
            case 34: case 33: case 32: case 31: case 30:
                grade = "B";
                break;
            case 29: case 28: case 27: case 26: case 25:
                grade = "C";
                break;
            case 24: case 23: case 22: case 21: case 20:
                grade = "D";
                break;
            default:
                grade = "E";
                break;
        }
        System.out.println("IPK: " + ipk + " Grade: " + grade);
    }
}
