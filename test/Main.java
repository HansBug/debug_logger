import com.hansbug.arguments.Arguments;
import com.hansbug.arguments.exceptions.ArgumentsException;
import com.hansbug.arguments.exceptions.InvalidArgumentInfo;
import com.hansbug.debug.DebugHelper;
import com.hansbug.debug.exceptions.DebugHelperException;
import test_package_A.TestClassA;

import java.util.Map;

abstract class Main {
    public static void main(String[] args) {
        try {
//            testArguments(args);
            testDebugHelper(args);
        } catch (Exception e) {
            System.out.println(String.format("[%s] %s", e.getClass().getName(), e.getMessage()));
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
    
    private static void testDebugHelper(String[] args) throws ArgumentsException, DebugHelperException {
        DebugHelper.setSettingsFromArguments(args);
        
        DebugHelper.debugPrintln(1, "ksdhjf");
    
        TestClassA t1 = new TestClassA();
        System.out.println(t1.toString());
        
        TestClassA t2 = new TestClassA(2, 7);
        System.out.println(t2.toString());
    }
}
