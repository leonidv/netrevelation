package com.vygovskiy.routes

import java.net.*
import com.vygovskiy.routes.Node

/**
 *
 * @author leonidv
 */
class Zones {

    static String ipToString(byte[] ip) {
        InetAddress.getByAddress(ip).getHostAddress()
    }

    private stayForZone = ["A":1, "B":2, "C":3]

    /**
     * Return zone for node. For example: node.ip = "1.2.3.4", zone = "A",
     * return "1.0.0.0".
     */
    private byte[] getZone(String zoneClass, String ip) {
        def byte[] bytesIp = InetAddress.getByName(ip).address
        def byte[] zone = [0,0,0,0]

        for (int i = 0; i < stayForZone[zoneClass]; i++) {
            zone[i] = bytesIp[i]
        }
        return zone
    }

    def byte[] getZoneA(String ip) {
        getZone("A",ip)
    }

    def byte[] getZoneB(String ip) {
        getZone("B",ip)
    }

    def byte[] getZoneC(String ip) {
        getZone("C",ip)
    }

    /**
     *  Return all reachable nodes in zone C.
     */
    def List<Node> getZonesNode(InputStream inputStream) {
        List<Node> list = new ArrayList<Node>()
        
        inputStream.eachLine{ line ->
            if (!isHostDescription(line)) {
                return
            }
            
            list << Node.fromNmap(line)
        }
        return list
    }

    def isHostDescription(line) {
        line ==~ /^Host.*?\.*$/
    }

}

