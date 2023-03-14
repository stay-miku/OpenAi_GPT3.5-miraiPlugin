package top.staymiku.openaiPlugin.openai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class net {
    public static String host;     // 配置远程地址
    public static int port;
    public static void init(String h, int p){
        host = h;
        port = p;
    }
    // 发送一个int
    public static void sendInt(OutputStream toServer, int i){
        byte[] in = ByteBuffer.allocate(4).putInt(i).array();
        try {
            toServer.write(in);
        } catch (IOException e) {      // 一般不会IOException吧,不会吧不会吧(报错了再说~)
            e.printStackTrace();
        }
    }
    // 发送一个string
    public static void sendString(OutputStream toServer, String str){
        byte[] s = str.getBytes(StandardCharsets.UTF_8);            // java的配置文件太麻烦了,我也不会给这个插件配json处理的库,直接写死好了
        sendInt(toServer, s.length);
        try {
            toServer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 获取一个int
    public static int getInt(InputStream fromServer){
        byte[] i = new byte[4];
        try {
            fromServer.read(i);            // 不传输大文件应该没啥事,单次读取上限应该是128KB,4byte应该能一次读完
            return ByteBuffer.wrap(i).getInt();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    // 获取一个string
    public static String getString(InputStream fromServer){
        int i = getInt(fromServer);
        try {
            byte[] s = new byte[i];        // 返回-1直接寄,应该是IOException吧
            fromServer.read(s);
            return new String(s, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    // 与服务端通信,针对该协议特化版,parameters为client发送给server的所有字符串列表,返回值为server给client发送的字符串列表(因为该协议为client向server发一次信息server给client回一个信息通信就结束了)(python那边为啥不这么写是因为写完python的才知道可以这么写)
    // parameters应为request类型后跟参数(user参数必带)
    public static List<String> connection(List<String> parameters, int getCount){
        try {
            Socket client = new Socket(host, port);
            InputStream fromServer = client.getInputStream();
            OutputStream toServer = client.getOutputStream();
            toServer.write("gpt_hello".getBytes(StandardCharsets.UTF_8));
            byte[] hello = new byte[9];
            fromServer.read(hello);
            for (String str: parameters){
                sendString(toServer, str);
            }
            List<String> re = new ArrayList<>();
            for (int i = 0; i < getCount; i++){
                re.add(getString(fromServer));
            }
            fromServer.close();
            toServer.close();
            client.close();
            return re;
        } catch (IOException e) {
            e.printStackTrace();
            List<String> list = new ArrayList<>();
            list.add("error: connection error");
            return list;
        }
    }
}
