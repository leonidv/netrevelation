/*
 * This script can create shell script that will contain commmand 
 * <code> 
 * tracepath 1.1.1.1 > 1.1.1.1.trace
 * tracepath 1.1.1.2 > 1.1.1.2.trace
 * tracepath 1.1.2.1 > 1.1.2.1.trace
 * </code>
 * After execute you can get needed data on another machine.
 */

import static com.vygovskiy.DataProvider.*

import com.vygovskiy.routes.*
import static com.vygovskiy.routes.Zones.*;

Zones zones = new Zones()

Routes routes = new Routes()
routes << fromFile("traces/vygovskiy.com.trace")

List<String> nets = new ArrayList<String>()
routes.getNodes().each{ node ->
    zone = zones.getZoneC(node.ip)
    int c = zone[2] & 0xff;
    def String checkedZone = "${zone[0] & 0xff}.${zone[1] & 0xff}."
    if (c < 4) {
        checkedZone += "1-7"
    } else {
        checkedZone += "${c-3}-${c+3}"
    }
    checkedZone += ".1-255"
    nets << checkedZone;
}



List<Node> nodes = new ArrayList<Node>()

nets.each { net ->
    println "Check net ${net}, please be patient..."
    nodes += zones.getZonesNode(nmap(net))
}

//nodes += zones.getZonesNode(fromFile("traces/217.65.1.1-255.nmap"))


PrintWriter writer = new File("traces.sh").newPrintWriter()

nodes.each { node ->
    s = "tracepath ${node.ip} > ${node.ip}.trace"
    writer.println(s)    
}

writer.close();
