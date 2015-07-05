package com.westernstory.api.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by fedor on 15/6/25.
 */
public class JPush {

    private static final String APP_KEY = "0044ea31f3ea2c3a3c71c6f2";
    private static final String APP_SECRET = "816a9bc76753283190be0546";

    public static final String TYPE_MSG = "MSG";

    private static Logger logger = LoggerFactory.getLogger(JPush.class);


    public static void push(String alert, String type, Long id) {

        final JPushClient jpushClient = new JPushClient(APP_SECRET, APP_KEY, 3);
//        PushPayload payload = PushPayload.alertAll(alert);

        final PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
//                .setNotification(Notification.alert(alert))
                .setMessage(Message.newBuilder()
                        .setMsgContent(alert)
                        .addExtra("id", id)
                        .addExtra("type", type)
                        .build())
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PushResult result = jpushClient.sendPush(payload);
                    System.out.println(result);
                } catch (Exception e) {
                    logger.error("Connection error, should retry later", e);
                }
            }
        }).start();
    }
}
