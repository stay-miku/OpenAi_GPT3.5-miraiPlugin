package top.staymiku.openaiPlugin.bot;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Objects;

public class tools {
    public static String limitError = "api达到限制,请稍后再使用";
    public static String serverError = "api网络错误,请稍后再尝试";
    public static String assistantError = "上一个不是机器人的发言,不可重新回答";
    public static String clientError = "客户端网络错误,请稍后再试";
    public static String userError = "你还没有创建哦~";
    public static String getError(String answer){
        if (Objects.equals(answer, "false")){
            return userError;
        }
        else if (answer.equals("error: limit error")){
            return limitError;
        }
        else if (answer.equals("error: network error")){
            return serverError;
        }
        else if (answer.equals("error: is not assistant")){
            return assistantError;
        }
        else if (answer.equals("error: connection error")){
            return clientError;
        }
        else return answer;
    }
    //用于合并指令(应该用不到了)
    public static String commandConnect(String[] commands, int start, int end){
        if (start >= commands.length){
            return "";
        }
        String re = "";
        for (int i = start; i < Math.min(end, commands.length) ; i++){
            re += commands[i];
            if (i != Math.min(end, commands.length)) re += " ";
        }
        return re;
    }
    // 不考虑负数的判断是否为浮点数
    public static boolean isFloat(String str){
        if (str.toCharArray()[0] == '.') return false;
        int dot_num = 0;
        for (char c: str.toCharArray()){
            if (c == '.') dot_num++;
            else if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) return false;
        }
        return dot_num < 2;
    }
    public static void send(GroupMessageEvent event, String str){
        MessageChain messages = new MessageChainBuilder().append(new At(event.getSender().getId())).append(" " + str).build();
        event.getGroup().sendMessage(messages);
    }
}