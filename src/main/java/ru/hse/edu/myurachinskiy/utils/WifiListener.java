package ru.hse.edu.myurachinskiy.utils;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import java.io.IOException;
import java.util.function.Consumer;

public class WifiListener {
    public WifiListener(Consumer<AccelerometerData> consumer) throws IOException {
        this.consumer = consumer;
        Configuration config = new Configuration();
        config.setPort(AppSettings.SERVER_PORT);
        this.server = new SocketIOServer(config);
        this.server.addEventListener(AppSettings.SOCKET_EVENT_NAME, AccelerometerData.class,
                (socketIOClient, accelerometerData, ackRequest) -> {
            consumer.accept(accelerometerData);
        });
        this.server.start();
    }

    private Consumer<AccelerometerData> consumer;
    private SocketIOServer server;
}
