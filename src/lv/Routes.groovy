import lv.Node
import lv.Link

class Routes {

    def nodes = new HashMap<String,Node>()

    def public addPath(InputStream input) {
        Node previousNode
        int level = 0;
        input.eachLine{ line ->
            printLine(line)

            if (!containsIp(line)) {
                return
            }

            def node = getNode(line, level)

            if (node.equals(previousNode)) {
                return
            }
            if (previousNode != null) {
                previousNode.linkedWith << node
            }

            previousNode = node
            level++
        }
        println()
    }

    def printLine(String line) {
        def ch = "*"
        if (line =~ /reply/) {
            ch = "-"
        } else if (line =~ /Resume/) {
            ch = "!"
        } else if (line =~ /Too many hops/) {
            ch = ""
        }
        print ch
    
    }

    def private  getNode(line, level) {
        Node node = new Node(line)
        if (!nodes[node.ip]) {
            nodes[node.ip] = node
            node.level = level
        }
        return nodes[node.ip]
    }

    def private boolean containsIp(String line) {
        line =~ /(\d{1,3}\.){3}(\d{1,3})/
    }

    @Override
    String toString() {
        def sb = new StringBuffer()

        nodes.v

        SortedSet values = new TreeSet(nodes.values())

        values.each {node -> 
            sb << "${node.level}. ${node.ip} (${node.name}) \n"
        }

        sb.toString()
    }

   def Set links() {
       def Set links = new TreeSet()
       nodes.values().each{Node a->
           a.linkedWith.each {b->
               links << new Link(a,b)
           }
       }
       return links
    }
}

