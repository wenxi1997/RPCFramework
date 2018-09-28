import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseTest {

    public BaseTest() {
    }

    public static void print(Object ... str) {
        print(", ", str);
    }

    public static void print(String str) {
        print("", str);
    }

    public static void printFormat(String s, Object ... os) {
        String out = MessageFormat.format(s, os);
        print(out);
    }

    @SuppressWarnings("all")
    public static void print(String seperator, Object ... str) {
        if(str == null) {
            return;
        }
        List<Object> list = new ArrayList<>(Arrays.asList(str));
        list.removeIf(o -> o == null);
        System.out.println(
                list.stream().reduce(
                        (l, r) -> new StringBuilder(l.toString()).append(seperator).append(r.toString())
                ).orElse("null")
        );
    }

    public static <T> Object getField(T object, String fieldName) {
        Field f = FieldUtils.getDeclaredField(object.getClass(), fieldName, true);
        if(f == null) {
            return null;
        }
        Object o = null;
        try {
            o = f.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void writeToFile(InputStream is, String path) {
        try{
         FileOutputStream fo = new FileOutputStream(path);
         byte[] b = new byte[1024];
         int len = 0;
         while((len = is.read(b, 0, 1024)) != -1){
             fo.write(b);
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(byte[] bs, String path) {
        try{
            FileOutputStream fo = new FileOutputStream(path);
            ByteArrayInputStream bi = new ByteArrayInputStream(bs);
            byte[] b = new byte[1024];
            int len = 0;
            while((len = bi.read(b, 0, 1024)) != -1){
                fo.write(b);
            }
           } catch (Exception e) {
               e.printStackTrace();
           }
    }
}
