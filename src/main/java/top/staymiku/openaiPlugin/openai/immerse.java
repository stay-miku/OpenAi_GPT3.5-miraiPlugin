package top.staymiku.openaiPlugin.openai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class immerse {
    public static Map<String, Map<String, String>> immerseCache = new HashMap<>();
    public static boolean configureConnection(){
        try{
            Socket client = new Socket(net.host, net.port);
            InputStream fromServer = client.getInputStream();
            OutputStream toServer = client.getOutputStream();

            toServer.write("gpt_hello".getBytes(StandardCharsets.UTF_8));
            byte[] hello = new byte[9];
            fromServer.read(hello);

            net.sendString(toServer, "configure_immerse");
            int count = net.getInt(fromServer);
            for (int i = 0; i < count; i++){
                Map<String, String> single = new HashMap<>();
                String user = net.getString(fromServer);
                String target = net.getString(fromServer);
                String group = net.getString(fromServer);
                single.put("user", user);
                single.put("target", target);
                single.put("group", group);
                immerseCache.put(user, single);
            }

            fromServer.close();
            toServer.close();
            client.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
