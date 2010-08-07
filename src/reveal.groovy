import com.vygovskiy.routes.*
import com.vygovskiy.*
import static com.vygovskiy.DataProvider.*



def Routes routes = new Routes()

routes.keysNodes << "93.157.123.16"
routes.keysNodes << "188.40.165.3"

routes << fromFile("traces/vygovskiy.com.trace")
routes << fromFile("traces/home.leonidv.ru.trace")
routes << fromFile("full-trace.out")

FreemarkerExporter exporter = new FreemarkerExporter("templates/")
exporter.export "graphviz.ftl", "example.dot", ["links":routes.links()]