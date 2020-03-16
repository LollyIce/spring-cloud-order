package com.order.Test;

import java.io.*;
import java.net.Socket;

public class GreetingClient {

    public static void main(String [] args)
    {
        String serverName = "192.168.10.113";  //服务端的ip
        int port = 6666;        //服务端的端口
        try
        {
            System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
            Socket client = new Socket(serverName, port);
            System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());  //53015
            InputStream inFromServer = client.getInputStream();
            for (int i=0;i<10;i++)
                out.writeUTF("张硕傻狗");
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("服务器响应： " + in.readUTF());
            client.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
