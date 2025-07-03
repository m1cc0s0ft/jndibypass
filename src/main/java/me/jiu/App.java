package me.jiu;

import me.jiu.server.LdapServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Config.applyCmdArgs(args);
        LdapServer.start();
    }
}
