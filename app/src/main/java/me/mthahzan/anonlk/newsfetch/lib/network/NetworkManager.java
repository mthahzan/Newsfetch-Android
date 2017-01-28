package me.mthahzan.anonlk.newsfetch.lib.network;

/**
 * Created by mthahzan on 1/28/17.
 * Netowrk request handler
 */
public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
    }
}
