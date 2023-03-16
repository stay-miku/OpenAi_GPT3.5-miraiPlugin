package top.staymiku.openaiPlugin.bot;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.QuoteReply;
import org.jetbrains.annotations.NotNull;
import top.staymiku.openaiPlugin.openai.controls;

import java.util.List;
import java.util.Objects;

public class events extends SimpleListenerHost {

    // 把命令处理独立出来了,可喜可贺!

    public static String help = "主要功能\n(<>内为必填,[]内为选填,所有选项只支持纯文本,除了@bot别的都不允许使用空格):\n" +
            "@bot <文本>:与机器人对话\n" +
            "#创建 <设定名字> [主人名字]: 创建,所有操作都需创建后执行(名字和主人名只对默认设定有效)\n" +
            "#预设 <问题> <回答>: 预设一段对话\n" +
            "以下命令会清除历史对话,小心使用:\n" +
            "#清除: 清除历史对话(失忆~)\n" +
            "#修改设定 <设定提示词>: 修改设定\n" +
            "#重置设定: 重置为默认设定(会清空自定义的设定)\n" +
            "#设置名字 <名字>: 设定名字(会清空自定义的设定)\n" +
            "#设置主人名字 <主人名字>: 设置主人名字(会清空自定义的设定)\n" +
            "#清除主人名字: 清除主人名字(会清空自定义的设定)\n" +
            "#不常用命令帮助: 如名";
    public static String unCommonHelp = "不常用命令:\n" +
            "#设置温度 <0-2之间的数字>: 该值代表问题对回答的约束力,2约束最小\n" +
            "#获取设定: 获得当前设定\n" +
            "#说 <文本>: 同@bot,但是不会立即有回复\n" +
            "#听: 立即获得一个回复\n" +
            "#撤销: 撤销上一句话,不论是谁说的\n" +
            "#预设回答 <回答>: 预设一句话作为回答\n" +
            "#重新回答: 如名\n" +
            "#名字: 获取当前名字\n" +
            "#主人名字: 获取当前主人名字\n" +
            "#已使用token: 获取已使用token数量\n" +
            "#温度: 获取当前温度";
    public static String badParameter = "错误的参数,使用#ai帮助 查看使用方法";
    public static String commandProcess(String command, String user){      // 独立出来的命令处理
        String[] commands = command.split(" ");
        if (commands[0].equals("#创建")){
            if (commands.length == 2){
                if (controls.create(user, commands[1], "")){
                    return  "创建成功";
                }
                else return  "你已经创建过了哦~";
            }
            else if (commands.length == 3){
                if (controls.create(user, commands[1], commands[2])){
                    return  "创建成功";
                }
                else return  "你已经创建过了哦~";
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#预设")){
            if (commands.length == 3){
                if (controls.set_chat(user, commands[1], commands[2])){
                    return  "预设成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#清除")){
            if (commands.length == 1){
                if (controls.clear(user)){
                    return  "清除消息成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#修改设定")){
            if (commands.length == 2){
                if (controls.set_prompt(user, commands[1])){
                    return  "修改成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#重置设定")){
            if (commands.length == 1){
                if (controls.set_default_prompt(user)){
                    return  "重置成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#设置名字")){
            if (commands.length == 2){
                if (controls.set_name(user, commands[1])){
                    return  "设置成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#设置主人名字")){
            if (commands.length == 2){
                if (controls.set_owner_name(user, commands[1])){
                    return  "设置成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#不常用命令帮助")){
            if (commands.length == 1){
                return unCommonHelp;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#ai帮助")){
            if (commands.length == 1){
                return help;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#设置温度")){
            if (commands.length == 2){
                if (tools.isFloat(commands[1]) && Float.parseFloat(commands[1]) <= 2){
                    if (controls.set_temperature(user, commands[1])){
                        return  "设置成功";
                    }
                    else return tools.userError;
                }
                else return  "参数错误,是0-2的数字哦~";
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#获取设定")){
            if (commands.length == 1){
                String re = controls.get_prompt(user);
                return tools.getError(re);
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#说")){
            if (commands.length == 2){
                if (!controls.listen(user, commands[1])) return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#听")){
            if (commands.length == 1){
                String re = controls.speak(user);
                return tools.getError(re);
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#撤销")){
            if (commands.length == 1){
                List<String> re = controls.revoke(user);
                if (re.get(0).equals("false")) return badParameter;
                else if (re.get(0).equals("empty")) return  "历史消息为空!";
                else return "撤回成功,详情:发送类型:" + (Objects.equals(re.get(0), "assistant") ? "ai" : "用户") + " 内容:" + re.get(1);   // 用了个三元
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#预设回答")){
            if (commands.length == 2){
                if (controls.set_speak(user, commands[1])){
                    return  "预设成功";
                }
                else return badParameter;
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#重新回答")){
            if (commands.length == 1){
                String re = controls.re_chat(user);
                return tools.getError(re);
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#名字")){
            if (commands.length == 1){
                return tools.getError(controls.get_name(user));
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#主人名字")){
            if (commands.length == 1){
                return tools.getError(controls.get_owner_name(user));
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#已使用token")){
            if (commands.length == 1){
                return tools.getError(controls.get_used_token(user));
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#温度")){
            if (commands.length == 1){
                return tools.getError(controls.get_temperature(user));
            }
            else {
                return badParameter;
            }
        }
        else if (commands[0].equals("#清除主人名字")){
            if (commands.length == 1){
                if (controls.set_owner_name(user, "")){
                    return  "清除成功";
                }
                else return tools.userError;
            }
            else {
                return badParameter;
            }
        }
        return null;
    }
    @EventHandler
    public void onGroupMessage(@NotNull GroupMessageEvent event){   // 对群内消息的处理
        MessageChain messages = event.getMessage();
        String user = String.valueOf(event.getSender().getId());
        String question = "";
        String command;
        if (messages.get(1) instanceof At && messages.get(1).equals(new At(event.getBot().getId()))){
            for (int i = 2; i < messages.size(); i++){
                if (messages.get(i) instanceof At){
                    question += ((At) messages.get(i)).getDisplay(event.getGroup()).split("@")[1]; // 拼接question (所以@bot 可以不是纯文本内容了)
                }
                else question += messages.get(i).contentToString();
            }
            if (question.equals("")) return;
            question = question.trim();
            if (!question.startsWith("#")) {                     // 允许@bot 接上命令了!
                String answer = controls.chat(user, question);
                QuoteReply reply = new QuoteReply(event.getSource());
                event.getGroup().sendMessage(reply.plus(tools.getError(answer)));
                return;
            }
            command = question;
        }
        else command = messages.contentToString();
        String result = commandProcess(command, user);
        if (result != null){
            tools.send(event, result);
        }
    }
    @EventHandler
    public void onFriendMessage(@NotNull FriendMessageEvent event){     // 增加私聊功能
        String messages = event.getMessage().contentToString();
        String user = String.valueOf(event.getSender().getId());
        if (messages.startsWith("#")){
            String result = commandProcess(messages, user);
            if (result != null){
                tools.send(event, result);
            }
            else tools.send(event, "未知的命令");
        }
        else {
            String answer = controls.chat(user, messages);
            QuoteReply reply = new QuoteReply(event.getSource());
            event.getFriend().sendMessage(reply.plus(tools.getError(answer)));
        }
    }
//    @EventHandler
//    public void onGroupTempMessage(@NotNull GroupTempMessageEvent event){
//        // 还是不做了吧(临时会话聊涩涩什么的看着就感觉容易被封号哇!)
//    }
    @EventHandler
    public void onNewFriendRequest(@NotNull NewFriendRequestEvent event){   // 为了允许私聊所以自动同意加好友请求(以后设一个管理员账号来管理加好友请求也不错)
        event.accept();
    }
}
