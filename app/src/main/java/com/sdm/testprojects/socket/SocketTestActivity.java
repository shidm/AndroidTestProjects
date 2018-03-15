package com.sdm.testprojects.socket;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdm.testprojects.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shidongming on 18-2-8.
 */

public class SocketTestActivity extends AppCompatActivity implements View.OnClickListener {

    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;

    // Socket变量
    private Socket socket;

    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;

    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;


    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    private TextView showmsg;
    private EditText writeMsg;
    private Button connect, disconnect, sendMsg, recive;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_socket);

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Socket测试");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        showmsg = findViewById(R.id.hellow);
        writeMsg = findViewById(R.id.editText);
        connect = findViewById(R.id.connect);
        disconnect = findViewById(R.id.disconnect);
        sendMsg = findViewById(R.id.sendmsg);
        recive = findViewById(R.id.receive);

        connect.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        recive.setOnClickListener(this);

        // 初始化线程池
        mThreadPool = Executors.newCachedThreadPool();


        // 实例化主线程,用于更新接收过来的消息
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        showmsg.setText(response);
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
// 利用线程池直接开启一个线程 & 执行该线程
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            // 创建Socket对象 & 指定服务端的IP 及 端口号
                            socket = new Socket("172.17.136.204", 8089);

                            // 判断客户端和服务器是否连接成功
                            System.out.println(socket.isConnected());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            case R.id.disconnect:
                try {
                    // 断开 客户端发送到服务器 的连接，即关闭输出流对象OutputStream
                    outputStream.close();

                    // 断开 服务器发送到客户端 的连接，即关闭输入流读取器对象BufferedReader
                    br.close();

                    // 最终关闭整个Socket连接
                    socket.close();

                    // 判断客户端和服务器是否已经断开连接
                    System.out.println(socket.isConnected());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sendmsg:
                // 利用线程池直接开启一个线程 & 执行该线程
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            // 步骤1：从Socket 获得输出流对象OutputStream
                            // 该对象作用：发送数据
                            outputStream = socket.getOutputStream();

                            // 步骤2：写入需要发送的数据到输出流对象中
                            outputStream.write((writeMsg.getText().toString() + "\n").getBytes("utf-8"));
                            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                            // 步骤3：发送数据到服务端
                            outputStream.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            case R.id.receive:
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            // 步骤1：创建输入流对象InputStream
                            is = socket.getInputStream();

                            // 步骤2：创建输入流读取器对象 并传入输入流对象
                            // 该对象作用：获取服务器返回的数据
                            isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);

                            // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                            response = br.readLine();

                            // 步骤4:通知主线程,将接收的消息显示到界面
                            Message msg = Message.obtain();
                            msg.what = 0;
                            mMainHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            default:
                break;
        }
    }
}
