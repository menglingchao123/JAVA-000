package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        try {
            HelloClassLoader helloClassLoader = new HelloClassLoader();
            Class<?> clazz = helloClassLoader.findClass("Hello");
            if (clazz != null)
                clazz.getMethod("hello").invoke(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author 孟令超
     * @version 1.0
     * @desription 自定义classloader
     * @date 2020/10/18
     * @since jdk1.8
     */
    public static class HelloClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = fileConvert();
            return bytes == null ? null : defineClass(name, bytes, 0, bytes.length);
        }

        /**
         * 获取字节码二进制
         *
         * @return
         */
        private byte[] fileConvert() {
            String filePath = "./Hello.xlass";
            FileInputStream inputStream = null;
            byte[] bytes = null;
            try {
                inputStream = new FileInputStream(filePath);
                int available = inputStream.available();
                //一个字节码大小是一个字节
                bytes = new byte[available];
                inputStream.read(bytes, 0, available);
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (255 - bytes[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bytes;
        }
    }
}
