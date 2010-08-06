import com.vygovskiy.routes.*
import com.vygovskiy.*

def trace(String address) {
    Process proc = "tracepath ${address}".execute()
    proc.inputStream
}

def fromFile(String fileName) {
    new File("traces/"+fileName+".trace").newInputStream()
}


def Routes routes = new Routes()

routes.addPath(fromFile("vygovskiy.com") )
routes.addPath(fromFile("home.leonidv.ru"))
/*
routes.addPath(trace("93.158.134.3") )
routes.addPath(trace("77.88.21.3")   )
routes.addPath(trace("213.180.204.3"))
routes.addPath(trace("vygovskiy.com"))
routes.addPath(trace("skai-dev.ru"))

/*
routes.links().each{
    println "${it.a.level}. ${it.a.ip} - ${it.b.ip}"
}
*/

FreemarkerExporter exporter = new FreemarkerExporter("templates/")
exporter.export "graphviz.ftl", "example.dot", ["links":routes.links()]