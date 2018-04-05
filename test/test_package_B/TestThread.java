package test_package_B;

import com.hansbug.debug.DebugHelper;

public class TestThread extends Thread {
    private String thread_name = null;
    private int count = 0;
    private int timespan = 1000;
    
    public TestThread(String thread_name, int count, int timespan) {
        this.thread_name = thread_name;
        this.count = count;
        this.timespan = timespan;
    }
    
    @Override
    public void run() {
        DebugHelper.debugPrintln(1, String.format("[Thread - \"%s\"] start", this.thread_name));
        try {
            for (int i = 0; i < count; i++) {
                DebugHelper.debugPrintln(2, String.format("[Thread - \"%s\"] execute - %s and wait for %s ms", this.thread_name, i, this.timespan));
                System.out.println(String.format("[%s] %s", this.thread_name, i));
                long time = System.currentTimeMillis();
                Thread.sleep(this.timespan);
                long timedelta = System.currentTimeMillis() - time;
                DebugHelper.debugPrintln(3, String.format("[Thread - \"%s\"] came back after %s ms", this.thread_name, timedelta));
            }
        } catch (InterruptedException e) {
            DebugHelper.debugPrintln(1, String.format("[Thread - \"%s\"] interrupted", this.thread_name));
        }
        DebugHelper.debugPrintln(1, String.format("[Thread - \"%s\"] end", this.thread_name));
    }
}
