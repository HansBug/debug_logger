# debug_logger

`debug_logger` is a powerful debug helper based on the debug informations in the standard output. It can easily manage the output of the debug information, which will help us a lot when debugging.

## Initialize
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

## Let's debug
The usage of `DebugHelper` in the program is very easy. You can add this into your program anywhere you like to output the debug information.
```java
DebugHelper.debugPringln(2, "My debug information");
```
When you use the `--debug 2`(or more than 2) in the command line, this will create a line of output(**in the standard output**).
```
[DEBUG - 2] [xxxx.java : 233] My debug information
```
`xxxx.java : 233` represents the position where you put the `debugPrintln`, in this case it means _the 233rd line_ of the `xxxx.java`

When you use the `--debug 1`, there will be nothing output because we the **debug level** of this piece of debug information is `2`, while what we need is only `1`.
 
## Command line configuration

* **-D \<level\>, --debug \<level\>** define the debug level. The maximum of the debug level is `5` and the minimum is `1`.
* **--debug_package_name \<package_name\>** define the limit of the package name(full name, just like `com.hansbug.debug`)
* **--debug_file_name \<file_name\>** define the limit of the file name(short file name, just like `Main.java`, `DebugHelper.java`)
* **--debug_class_name \<class_name\>** define the limit of the class name(short class name, **including inner classes**, like `TestClassA`, `TestInnerClassA`(instead of `TestClassA.TestInnerClassA`))
* **--debug_method_name \<method_name\>** define the limit of the method name(just like `toString`, `<init>`(constructor method))
* **--debug_include_children** define whether record the log in subroutines that called by the method in the limit above.

#### ATTENTION

* If `--debug`(or `-D`) not detected, debug mode is DISABLED and file logger will not be processed.
* If debug level is invalid, there will be exception thrown out.
* **All the limit above is regular-expression-supported!**
* **Remember to initialize the configurations at the beginning!!!**

#### Example  
You can try to run the `Main.main` in the `test` directory to see and try the example.

The command line arguments should be added before running the demo.

## Javadoc 
(not yet created :-( )
