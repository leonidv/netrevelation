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
import static java.lang.String.format as fmt

import com.vygovskiy.routes.*
import static com.vygovskiy.routes.Zones.*;

final String OUTPUT_SCRIPT_NAME = "findtraces.sh"
final int LINES_PER_SCRIPT = 100

Zones zones = new Zones()

Routes routes = new Routes()
["home.leonidv.ru","vygovskiy.com"].each{
    routes << fromFile("traces/${it}.trace")
}


Set<String> nets = new HashSet<String>()
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

println "Total subnets to check: ${nets.size()}"

nets.eachWithIndex { net, i ->
def progress = i/nets.size()*100
println "${fmt("%02.1f",progress)}% Check net ${net}, please be patient..."
nodes += zones.getZonesNode(nmap(net))
}
 

//nodes += zones.getZonesNode(fromFile("traces/217.65.1.1-255.nmap"))


int scriptsCount = (int)(nodes.size() / LINES_PER_SCRIPT)+1
def List<PrintWriter> writers = new ArrayList<PrintWriter>(scriptsCount)
def List<String> scriptFiles = new ArrayList<String>()
scriptsCount.times{ i->
    def fileName = fmt("traces-%02d.sh",i)
    writers[i] = new File(fileName).newPrintWriter()
    scriptFiles << fileName
}

println "Total ${nodes.size()} will be wrotten to ${scriptsCount} files"

def writerIndex = -1;
nodes.eachWithIndex { node, i ->
    if (i % LINES_PER_SCRIPT == 0) {
        writerIndex++
    }
    s = "tracepath ${node.ip} >> ${scriptFiles[writerIndex]}.out"
    writers[writerIndex].println(s)
}

writers.each{
    it.close()
};

writer = new File("traces-run.sh").newPrintWriter()
scriptFiles.each {
    writer.print("./${it} & ")
}
writer.println("\ncat traces-*.sh.out > traces.out")
writer.close()

"chmod +x traces-*.sh".execute()

