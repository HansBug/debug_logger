## debug_logger使用指南

>debug_logger是Hansbug开发的一款通过输出调试信息来帮助开发者进行调试并进行日志记录的调试工具。该调试工具基于Java语言，通过标准输出显示帮助信息并保存信息日志以方便开发者查阅。

### How to start

#### 初始化

要在java程序中使用这款调试工具，我们需要导入 **debug_logger.jar**包并在入口方法中调用该包中的 `setSettingsFromArguments(args)`方法,一个典型的案例如下：
```
import com.hansbug.debug.DebugHelper;

public abstract class Main {
    public static void main(String[] args) {
        DebugHelper.setSettingsFromArguments(args);
    }
}
```

如此，通过设置运行时的参数，我们可以控制debug_logger
的行为来帮助我们更好地调试。

#### 具体的调用和输出样例

在代码中使用这款调试工具非常简单，我们只要在希望输出信息的地方加入如下的代码, 像这样:
```java
DebugHelper.debugPrintln(2, "My debug information");
```

或者这样:

```java
DebugHelper.debugPrintln(2, String.format(
    "[Thread - "%s"] execute - %s and wait for %s ms", 
    this.thread_name,i,this.timespan));
```

而他的输出类似这样：
```
[DEBUG - 2][xxxx.java:233 TestClass.testMethod] My debug information
```

以及这样：
```
[DEBUG-2][Thread-0][TestThread.java:21 TestThread.run] 
[Thread - "thread_1"] execute - 0 and wait for 503 ms
```

其中 `xxx.java:233`表示该调试命令的调用文件和所在行，你可以根据自己的需求定制化这个工具的输出格式。

#### 调试工具的命令格式

>需要说明的是，这款工具可以通过命令行使用，因此linux的用户会感到非常熟悉，而如果你选择通过IDE使用，当然也没有任何问题，在文档的最后会以Eclipse为例向您介绍如何在IDE中使用这款调试工具。

|命令行参数|用途|
|---|---|
|-D <level\>/ --debug <level\>|所有命令的前提，同时level是 **必须**参数，最小为1，最大为5|
|--debug_package_name <packge_name\>|只输出参数中目标package中的debug信息，例如`"com.hansbug.debug"`|
|--debug_file_name <file_name\>|只输出参数中目标文件中的debug信息，例如`"DebugHelper.java"`|
|--debug_class_name <class_name\>|只输出参数中目标类中的debug信息，例如`"TestClassA"`，`"TestInnerClassA"`，需要注明的是 *innerclass* 也会被单独识别|
|--debug_method_name <method\> |只输出参数中目标方法中的debug信息，例如`"toString"`（如果是构造函数的话，可以直接使用`<init>`）|
|--debug_include_children|当使用带`_name`方法时，此命令控制是否输出子方法中的信息|
|--debug_show_thread|控制是否输出当前`debugPrintln`的调用线程|

#### 注意事项

+ 如果运行时没有`--debug <level>`或者`-D <level>`的话，那么调试和日志模块将不会启动（**默认DISABLED**）
+ 务必在进入主函数时 **初始化**

#### 如何在Eclipse下使用debug_logger命令

##### Step1
在一个项目中使用此调试工具，首先需要引入 **debug_logger.jar**包，一个简单的引入方法是在复制jar包到当前项目下，并右键jar包选择

    build path > add to build path

##### Step2
在需要的输出的地方调用相应的方法，并在运行前在`run configurations`中输入命令。
一个简单的样例如下图：


![](https://github.com/buaa0110/debug_logger/raw/master/image/image.png)

##### Step3
运行


![](https://github.com/buaa0110/debug_logger/raw/master/image/image2.png)

---

备注：

+ 更详细的README和源代码请查看[debug_logger][1]
+ 更多调试工具的调试技巧请参见[博客][2](博客内容为较早版本，仅供参考)

[1]:https://github.com/HansBug/debug_logger
[2]:http://www.cnblogs.com/HansBug/p/8701447.html
