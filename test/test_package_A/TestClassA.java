package test_package_A;

import com.hansbug.debug.DebugHelper;

public class TestClassA {
    private int x;
    private int y;
    
    public class TestInnerClassA {
        private int x, y;
        public TestInnerClassA(int x, int y) {
            DebugHelper.debugPrintln(2, String.format("TestInnerClassA initialize, x: %s, y: %s", x, y));
            this.x = x;
            this.y = y;
        }
    }
    
    public TestClassA(int x, int y) {
        new TestInnerClassA(x, y);
        DebugHelper.debugPrintln(2, String.format("TestClassA initialize, x: %s, y: %s", x, y));
        this.x = x + 1;
        this.y = y - 1;
    }
    
    public TestClassA() {
        this(0, 0);
    }
    
    @Override
    public String toString() {
        DebugHelper.debugPrintln(2, String.format("TestClassA getString, x: %s, y: %s", this.x, this.y));
        return String.format("x: %s, y: %s", this.x, this.y);
    }
}
