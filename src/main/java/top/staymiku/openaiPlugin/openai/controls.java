package top.staymiku.openaiPlugin.openai;

import java.util.ArrayList;
import java.util.List;

public class controls {   // String或List都要判断false
    public static boolean create(String user, String name, String ownerName){     //这种返回值为false基本是user错误(华丽地无视了IOException)
        List<String> parameters = new ArrayList<>();
        parameters.add("create");
        parameters.add(user);
        parameters.add(name);
        parameters.add(ownerName);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static boolean clear(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("clear");
        parameters.add(user);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static boolean set_prompt(String user, String prompt){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_prompt");
        parameters.add(user);
        parameters.add(prompt);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static boolean set_default_prompt(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_default_prompt");
        parameters.add(user);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static boolean set_temperature(String user, String temperature){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_temperature");
        parameters.add(user);
        parameters.add(temperature);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static String get_prompt(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("get_prompt");
        parameters.add(user);
        List<String> re = net.connection(parameters, 1);
        return re.get(0);         // 判断 error: 和 false
    }
    public static boolean listen(String user, String question){
        List<String> parameters = new ArrayList<>();
        parameters.add("listen");
        parameters.add(user);
        parameters.add(question);
        List<String> re = net.connection(parameters, 1);
        return re.get(0).equals("true");
    }
    public static List<String> revoke(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("revoke");
        parameters.add(user);
        return net.connection(parameters, 2);
    }
    public static String speak(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("speak");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
    public static String chat(String user, String question){
        List<String> parameters = new ArrayList<>();
        parameters.add("chat");
        parameters.add(user);
        parameters.add(question);
        return net.connection(parameters, 1).get(0);
    }
    public static boolean set_speak(String user, String answer){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_speak");
        parameters.add(user);
        parameters.add(answer);
        return net.connection(parameters, 1).get(0).equals("true");
    }
    public static boolean set_chat(String user, String question, String answer){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_chat");
        parameters.add(user);
        parameters.add(question);
        parameters.add(answer);
        return net.connection(parameters, 1).get(0).equals("true");
    }
    public static String re_chat(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("re_chat");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
    public static String get_name(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("get_name");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
    public static boolean set_name(String user, String name){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_name");
        parameters.add(user);
        parameters.add(name);
        return net.connection(parameters, 1).get(0).equals("true");
    }
    public static String get_owner_name(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("get_owner_name");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
    public static boolean set_owner_name(String user, String owner_name){
        List<String> parameters = new ArrayList<>();
        parameters.add("set_owner_name");
        parameters.add(user);
        parameters.add(owner_name);
        return net.connection(parameters, 1).get(0).equals("true");
    }
    public static String get_used_token(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("get_used_token");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
    public static String get_temperature(String user){
        List<String> parameters = new ArrayList<>();
        parameters.add("get_temperature");
        parameters.add(user);
        return net.connection(parameters, 1).get(0);
    }
}
