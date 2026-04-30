package CS2050Reschke.M04.Iteration02;

public class DroneHangar {
    public static void main(String[] args) {
        Hangar hangar = new Hangar();
        HangarConsole program = new HangarConsole(hangar);
        program.run();
    }
}



