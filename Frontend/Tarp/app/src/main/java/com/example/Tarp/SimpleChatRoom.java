package com.example.Tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class SimpleChatRoom extends AppCompatActivity{

    private WebSocketClient mWebSocketClient;


    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_simple_chat_room);

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        String userName = currentUser.getString("currentUserName","");

        Button send, back;
        EditText sendMessage;
        TextView chatLog;
        back = (Button)findViewById(R.id.backButton);
        send = (Button) findViewById(R.id.sendMessage);
        chatLog = (TextView) findViewById(R.id.chatLog);
        chatLog.setMovementMethod(new ScrollingMovementMethod());        //Made the textview scrollable -Greg
        sendMessage = (EditText) findViewById(R.id.enterMessage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

                Draft[] drafts = {
                        new Draft_6455()
                };

                /**
                 * If running this on an android device, make sure it is on the same network as your
                 * computer, and change the ip address to that of your computer.
                 * If running on the emulator, you can use localhost.
                 */
                String w = "ws://10.0.2.2:8080/chat/" + userName;

                //Local URL: ws://10.0.2.2:8080/chat/
                // Server URL: ws://coms-309-036.cs.iastate.edu:8080/chat/
                try {
                    Log.d("Socket:", "Trying socket");
                    mWebSocketClient = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            String s = chatLog.getText().toString();
                            chatLog.setText(s + "" + message + "\n");
                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                mWebSocketClient.connect();



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mWebSocketClient.send(sendMessage.getText().toString());
                    sendMessage.setText("");
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }

            }
        });

    }


    /*        Test Stuff
    private void ConnectWebSocket(){

        URI uri;


        t1 = findViewById(R.id.)

        try{

            uri = new URI("ws://echo.WebSocket.org");
        }catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }
        mWebSocketClient = new WebSocketClient(uri){

            @Override
            public void onOpen(ServerHandshake serverHandshake){
                Log.i("WebSocket", "Opened");
            }

            @Override
            public void onMessage(String msg){
                Log.i("WebSocket", "MSG Received");
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote){
                Log.i("WebSocket", "Closed" + reason);
            }

            @Override
            public void onError(Exception e){
                Log.i("WebSocket", "Error: " + e.getMessage());
            }
        };
        mWebSocketClient.connect();

    }
     */

}
