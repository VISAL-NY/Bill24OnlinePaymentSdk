package com.bill24.onlinepaymentsdk.socketIO;

import android.util.Log;


import com.bill24.onlinepaymentsdk.socketIO.conts.CONTS;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;

public class SocketManager {
    private static Socket socket;
    public  static  void initSocketIO(){
        if(socket==null){
            try {
                IO.Options options=new IO.Options();
                options.transports=new String[]{WebSocket.NAME};
                socket=IO.socket(CONTS.SOCKET_SERVER,options);

                socket.connect();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Socket getSocket(){
        if (socket==null){
            initSocketIO();
        }
        return socket;
    }
    public static void connectJoinRoom(String roomName){
        if(socket==null){
            socket=SocketManager.getSocket();
        }
        socket.on(EVentName.CONNECT, args -> {
            Log.d("bbbbbbb", "call: From Client Cannect to Server");
            socket.emit(EVentName.JOIN_ROOM,CONTS.PREFIX_ROOM_NAME+"-"+roomName);
        });
    }
    public static void disConnect(){
        Log.d("vvvvvv", "disConnect: Disconnect from server");
        if(socket!=null){
            socket.disconnect();
        }

    }







}
