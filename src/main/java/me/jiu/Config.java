package me.jiu;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Config {
    @Parameter(names={"-a"}, description="your local ip address ", order=0)
    public static String address = "0.0.0.0";
    @Parameter(names={"-p", "--port"}, description="port to listen", order=1)
    public static int port = 1389;
    @Parameter(names={"-c", "--command"}, description="execute command,like calc,whoami", order=3)
    public static String command;
    @Parameter(names={"-h", "--help"}, description="show the help", help=true, order=4)
    public static boolean help;
    @Parameter(names={"-ms", "--memshell"}, description="注入内存马,请跟上java-chains等工具生成的rO0AB开头内存马(高版本利用链请选择fastjson)", order=5)
    public static String mem;

    public static void applyCmdArgs(String[] args) {
        JCommander jc = JCommander.newBuilder().addObject(new Config()).build();
        jc.parse(args);
        jc.setProgramName("java -jar JNDIBypass.jar");
        if (help) {
            jc.usage();
            System.out.println("For example: ");
            System.out.println("java -jar JNDIBypass.jar -a 192.168.11.1 -p 1389 -c \"touch /tmp/test\"");
            System.out.println("java -jar JNDIBypass.jar -a 192.168.11.1 -p 1389 -ms 1.txt");
            System.exit(0);
        }
    }

    static {
        help = false;
    }
}
