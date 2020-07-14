package com.tom.mq.rocketmq;

import java.io.File;
import java.util.ArrayList;

/**
 * @File: fileaa
 * @Description:
 * @Author: tom
 * @Create: 2020-07-02 10:02
 **/
public class fileaa {
    public static void main(String[] args) {
        String path = "E:/work/IM/code/im_pc/sciter/sciter_c-/CS-TemplateMultiPlatform/my_sciter_demo02/res/assets/images/emoji";
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
            }
        }
        String temp = "[";
        for (String s : files) {
            int index = s.lastIndexOf("emoji\\");
            if(index > 0) {
                String str = s.substring(index+6, s.length());
                temp += "'" + str + "',";
            }
        }
        temp += "]";
        System.out.println(temp);
    }
}
