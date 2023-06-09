package top.staymiku.openaiPlugin;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import top.staymiku.openaiPlugin.bot.events;
import top.staymiku.openaiPlugin.openai.net;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import top.staymiku.openaiPlugin.openai.immerse;

public final class Plugin extends JavaPlugin {
    public static final Plugin INSTANCE = new Plugin();

    private Plugin() {
        super(new JvmPluginDescriptionBuilder("top.staymiku.openaiPlugin.plugin", "1.0-SNAPSHOT").build());
    }

    @Override
    public void onEnable() {
        getLogger().info("OpenAi Chat Bot loaded!");
        GlobalEventChannel.INSTANCE.registerListenerHost(new events());
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./openaiHost")));
            String[] host = reader.readLine().split(":");
            net.init(host[0], Integer.parseInt(host[1]));
            if (!immerse.configureConnection()) {
                getLogger().error("can not load immerse configuration!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().error("can not load host config!");
        }
    }
}