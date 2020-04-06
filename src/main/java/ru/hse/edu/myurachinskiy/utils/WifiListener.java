package ru.hse.edu.myurachinskiy.utils;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class WifiListener {
    public WifiListener(Consumer<AccelerometerData> consumer) throws IOException {
        this.consumer = consumer;
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(AppSettings.SERVER_PORT);
        this.server = new SocketIOServer(config);

        this.server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("Connection established");
            }
        });

        this.server.addEventListener(AppSettings.SOCKET_EVENT_NAME, ArrayList.class,
                (socketIOClient, array, ackRequest) -> {
                    System.out.println(array);
                    Double x = Double.parseDouble(array.get(0).toString());
                    Double y = Double.parseDouble(array.get(1).toString());
                    Double z = Double.parseDouble(array.get(2).toString());
                    consumer.accept(new AccelerometerData(x,y,z));
        });
        this.server.start();
    }

    private Consumer<AccelerometerData> consumer;
    private SocketIOServer server;
}
