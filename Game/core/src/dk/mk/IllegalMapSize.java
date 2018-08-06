package dk.mk;

public class IllegalMapSize extends RuntimeException {

    public IllegalMapSize() {
        System.out.println("THE MAP SIZE IS NOT % 10. WIDTH: " + GameInfo.WINDOW_WIDTH + " HEIGHT: " + GameInfo.WINDOW_HEIGHT + ".");
    }
}
