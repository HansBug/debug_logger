import com.hansbug.arguments.Arguments;
import com.hansbug.arguments.exceptions.ArgumentsException;
import com.hansbug.arguments.exceptions.InvalidArgumentInfo;
import com.hansbug.debug.DebugHelper;
import com.hansbug.debug.exceptions.DebugHelperException;
import test_package_A.TestClassA;
import test_package_B.TestThread;

import java.util.Map;

abstract class Main {
    public static void main(String[] args) {
        try {
            DebugHelper.setSettingsFromArguments(args);
//            testDebugHelper(args);
            testThread(args);
        } catch (Exception e) {
            StackTraceElement trace = e.getStackTrace()[1];
            System.out.println(String.format("[%s : %s] [%s] %s", trace.getFileName(), trace.getLineNumber(), e.getClass().getName(), e.getMessage()));
            System.exit(1);
        }
        
    }
    
    /**
     * Arguments 测试
     *
     * @param args 系统命令行数组
     * @throws InvalidArgumentInfo 非法Argument信息
     */
    private static void testArguments(String[] args) throws InvalidArgumentInfo {
        Arguments a = new Arguments();
        a.addArgs("D", "debug", true, "1");
        a.addArgs(null, "debug_show_location", false);
        for (Map.Entry<String, String> entry : a.parseArguments(args).entrySet()) {
            System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
        }
    }
    
    private static void testDebugHelper(String[] args) {
//        DebugHelper.setSettingsFromArguments(args);
        DebugHelper.debugPrintln(1, "ksdhjf");
        
        TestClassA t1 = new TestClassA();
        System.out.println(t1.toString());
        
        TestClassA t2 = new TestClassA(2, 7);
        System.out.println(t2.toString());
    }
    
    private static void testThread(String[] args) throws InterruptedException {
        TestThread tt1 = new TestThread("thread_1", 15, 503);
        TestThread tt2 = new TestThread("thread_2", 6, 397);
        tt1.start();
        tt2.start();
        tt2.join();
        tt1.wait();
//        Thread.sleep(3000);
//        tt1.notify();
        
//        System.exit(1);
    }
}
