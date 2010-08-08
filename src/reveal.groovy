import com.vygovskiy.routes.*
import com.vygovskiy.*
import static com.vygovskiy.DataProvider.*



def Routes routes = new Routes()

routes.keyNodes << "93.157.123.16"
routes.keyNodes << "188.40.165.3"

routes << fromFile("traces/vygovskiy.com.trace")
routes << fromFile("traces/home.leonidv.ru.trace")
routes << fromFile("full-trace.out")
routes << fromFile("from-vygovskiy.com.trace")

/*
routes.keyNodes << "1.1.1.1"
routes.keyNodes << "1.1.1.4"
routes << fromFile("traces/leafs-remove.test")


println routes.getNodes().find{it.ip == "80.81.192.43"}
def node = routes.getNodes().find{it.ip == "217.79.3.113"}
node.parents.each{
    print "${it.ip} "
}
println()
node.linkedWith.each{
    println "${it.ip} "
}
*/
routes.removeLeafs()

println routes.getNodes().find{it.ip == "80.81.192.43"}


FreemarkerExporter exporter = new FreemarkerExporter("templates/")
exporter.export "graphviz_adv.ftl", "example.dot", [
    "links":routes.links(),
    "nodes":routes.getNodes()
]