public class Timer {
    //the time at the beginning
    private long time;

    public void start()
    {
        time = System.currentTimeMillis();
    }
    //duration
    float stop() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis- time;
    }

}
