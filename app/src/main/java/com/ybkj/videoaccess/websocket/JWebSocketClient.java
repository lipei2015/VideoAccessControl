package com.ybkj.videoaccess.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("JWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.i("JWebSocketClient", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("JWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.i("JWebSocketClient", "onError()");
    }
}
