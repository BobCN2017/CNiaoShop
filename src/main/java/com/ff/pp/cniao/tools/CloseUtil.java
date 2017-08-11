package com.ff.pp.cniao.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by PP on 2017/6/20.
 */

public class CloseUtil {
    public static void close(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
