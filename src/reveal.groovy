import com.vygovskiy.routes.*
import com.vygovskiy.*
import static com.vygovskiy.DataProvider.*



def Routes routes = new Routes()

routes << fromFile("vygovskiy.com")
routes << fromFile("home.leonidv.ru")

FreemarkerExporter exporter = new FreemarkerExporter("templates/")
exporter.export "graphviz.ftl", "example.dot", ["links":routes.links()]