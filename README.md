# debug_logger

`debug_logger` is a powerful debug helper based on the debug informations in the standard output. It can easily manage the output of the debug information, which will help us a lot when debugging.

## How to start

### In JetBrains IDEA

### In Eclipse

### Initialize
Firstly, we should initialize the config of the `DebugHelper` at the beginning of the entry point, like this:
```java
import com.hansbug.debug.DebugHelper;

public abstract class Main {
    public static void main(String[] args) {
        DebugHelper.setSettingsFromArguments(args);
    }
}
```
After this, we can use the `DebugHelper`.

### Let's debug
The usage of `DebugHelper` in the program is very easy. You can add this into your program anywhere you like to output the debug information.
```java
DebugHelper.debugPringln(2, "My debug information");
```
When you use the `--debug 2`(or more than 2) in the command line, this will create a line of output(**in the standard output**).
```
[DEBUG-2][xxxx.java:233 TestClass.testMethod] My debug information
```
`xxxx.java:233 TestClass.testMethod` represents the position where you put the `debugPrintln`, in this case it means _the 233rd line_ of the `xxxx.java`, in the method `testMethod` of the class `TestClass`.

When you use the `--debug 1`, there will be nothing output because we the **debug level** of this piece of debug information is `2`, while what we need is only `1`.
 
### Command line configuration

* **-D \<level\>, --debug \<level\>** define the debug level. The maximum of the debug level is `5` and the minimum is `1`.
* **--debug_package_name \<package_name\>** define the limit of the package name(full name, just like `com.hansbug.debug`)
* **--debug_file_name \<file_name\>** define the limit of the file name(short file name, just like `Main.java`, `DebugHelper.java`)
* **--debug_class_name \<class_name\>** define the limit of the class name(short class name, **including inner classes**, like `TestClassA`, `TestInnerClassA`(instead of `TestClassA.TestInnerClassA`))
* **--debug_method_name \<method_name\>** define the limit of the method name(just like `toString`, `<init>`(constructor method))
* **--debug_include_children** define whether record the log in subroutines that called by the method in the limit above.
* **--debug_show_thread** define whether show the name of threads which execute the `debugPrintln`.

#### ATTENTION

* If `--debug`(or `-D`) not detected, debug mode is DISABLED and file logger will not be processed.
* If debug level is invalid, there will be exception thrown out.
* **All the limit above is regular-expression-supported!**
* **Remember to initialize the configurations at the beginning!!!**

#### Example  
You can try to run the `Main.main` in the `test` directory to see and try the example.

The command line arguments should be added before running the demo.

In the `Main.main` in the `test` directory, when we run it without any arguments, its output should like this:
```
x: 1, y: -1
x: 3, y: 6
[thread_2] 0
[thread_1] 0
[thread_2] 1
[thread_1] 1
[thread_2] 2
[thread_1] 2
[thread_2] 3
[thread_2] 4
[thread_1] 3
[thread_1] 4
[thread_1] 5
[thread_1] 6
[thread_1] 7
[thread_1] 8
```

if the arguments `--debug 1` is added, its standard output will be:
```
[DEBUG-1][Main.java:62 Main.testDebugHelper] ksdhjf
x: 1, y: -1
x: 3, y: 6
[DEBUG-1][TestThread.java:18 TestThread.run] [Thread - "thread_1"] start
[DEBUG-1][TestThread.java:18 TestThread.run] [Thread - "thread_2"] start
[thread_2] 0
[thread_1] 0
[thread_2] 1
[thread_1] 1
[thread_2] 2
[thread_1] 2
[thread_2] 3
[thread_2] 4
[thread_1] 3
[DEBUG-1][TestThread.java:31 TestThread.run] [Thread - "thread_2"] end
[thread_1] 4
[thread_1] 5
[thread_1] 6
[thread_1] 7
[thread_1] 8
[DEBUG-1][TestThread.java:31 TestThread.run] [Thread - "thread_1"] end
```


if the arguments `--debug 5` is added, its standard output will be:
```
[DEBUG-1][Main.java:62 Main.testDebugHelper] ksdhjf
[DEBUG-2][TestClassA.java:12 TestInnerClassA.<init>] TestInnerClassA initialize, x: 0, y: 0
[DEBUG-2][TestClassA.java:20 TestClassA.<init>] TestClassA initialize, x: 0, y: 0
[DEBUG-2][TestClassA.java:31 TestClassA.toString] TestClassA getString, x: 1, y: -1
x: 1, y: -1
[DEBUG-2][TestClassA.java:12 TestInnerClassA.<init>] TestInnerClassA initialize, x: 2, y: 7
[DEBUG-2][TestClassA.java:20 TestClassA.<init>] TestClassA initialize, x: 2, y: 7
[DEBUG-2][TestClassA.java:31 TestClassA.toString] TestClassA getString, x: 3, y: 6
x: 3, y: 6
[DEBUG-1][TestThread.java:18 TestThread.run] [Thread - "thread_1"] start
[DEBUG-1][TestThread.java:18 TestThread.run] [Thread - "thread_2"] start
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 0 and wait for 503 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 0 and wait for 367 ms
[thread_2] 0
[thread_1] 0
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 367 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 1 and wait for 367 ms
[thread_2] 1
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 1 and wait for 503 ms
[thread_1] 1
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 2 and wait for 367 ms
[thread_2] 2
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 2 and wait for 503 ms
[thread_1] 2
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 3 and wait for 367 ms
[thread_2] 3
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 4 and wait for 367 ms
[thread_2] 4
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 3 and wait for 503 ms
[thread_1] 3
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-1][TestThread.java:31 TestThread.run] [Thread - "thread_2"] end
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 4 and wait for 503 ms
[thread_1] 4
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 5 and wait for 503 ms
[thread_1] 5
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 6 and wait for 503 ms
[thread_1] 6
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 7 and wait for 503 ms
[thread_1] 7
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 8 and wait for 503 ms
[thread_1] 8
[DEBUG-3][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-1][TestThread.java:31 TestThread.run] [Thread - "thread_1"] end
```

if the arguments `--debug 5 --debug_package_name "test_package_A"` is added, it will be:
```
[DEBUG-2][TestClassA.java:12 TestInnerClassA.<init>] TestInnerClassA initialize, x: 0, y: 0
[DEBUG-2][TestClassA.java:20 TestClassA.<init>] TestClassA initialize, x: 0, y: 0
[DEBUG-2][TestClassA.java:31 TestClassA.toString] TestClassA getString, x: 1, y: -1
x: 1, y: -1
[DEBUG-2][TestClassA.java:12 TestInnerClassA.<init>] TestInnerClassA initialize, x: 2, y: 7
[DEBUG-2][TestClassA.java:20 TestClassA.<init>] TestClassA initialize, x: 2, y: 7
[DEBUG-2][TestClassA.java:31 TestClassA.toString] TestClassA getString, x: 3, y: 6
x: 3, y: 6
[thread_2] 0
[thread_1] 0
[thread_2] 1
[thread_1] 1
[thread_2] 2
[thread_1] 2
[thread_2] 3
[thread_2] 4
[thread_1] 3
[thread_1] 4
[thread_1] 5
[thread_1] 6
[thread_1] 7
[thread_1] 8
```

if the arguments `--debug 5 --debug_package_name "test_package_B" --debug_show_thread` is added, it will be:
```
x: 1, y: -1
x: 3, y: 6
[DEBUG-1][Thread-1][TestThread.java:18 TestThread.run] [Thread - "thread_2"] start
[DEBUG-1][Thread-0][TestThread.java:18 TestThread.run] [Thread - "thread_1"] start
[DEBUG-2][Thread-1][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 0 and wait for 367 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 0 and wait for 503 ms
[thread_2] 0
[thread_1] 0
[DEBUG-3][Thread-1][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-2][Thread-1][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 1 and wait for 367 ms
[thread_2] 1
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 1 and wait for 503 ms
[thread_1] 1
[DEBUG-3][Thread-1][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-2][Thread-1][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 2 and wait for 367 ms
[thread_2] 2
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 2 and wait for 503 ms
[thread_1] 2
[DEBUG-3][Thread-1][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 367 ms
[DEBUG-2][Thread-1][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 3 and wait for 367 ms
[thread_2] 3
[DEBUG-3][Thread-1][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 367 ms
[DEBUG-2][Thread-1][TestThread.java:21 TestThread.run] [Thread - "thread_2"] execute - 4 and wait for 367 ms
[thread_2] 4
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 3 and wait for 503 ms
[thread_1] 3
[DEBUG-3][Thread-1][TestThread.java:26 TestThread.run] [Thread - "thread_2"] came back after 368 ms
[DEBUG-1][Thread-1][TestThread.java:31 TestThread.run] [Thread - "thread_2"] end
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 4 and wait for 503 ms
[thread_1] 4
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 5 and wait for 503 ms
[thread_1] 5
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 504 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 6 and wait for 503 ms
[thread_1] 6
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 7 and wait for 503 ms
[thread_1] 7
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 503 ms
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] [Thread - "thread_1"] execute - 8 and wait for 503 ms
[thread_1] 8
[DEBUG-3][Thread-0][TestThread.java:26 TestThread.run] [Thread - "thread_1"] came back after 505 ms
[DEBUG-1][Thread-0][TestThread.java:31 TestThread.run] [Thread - "thread_1"] end
```

## Javadoc 
(not yet created :-( )
