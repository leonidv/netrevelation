package com.vygovskiy.routes

import com.vygovskiy.routes.Node
import com.vygovskiy.routes.Link

class Routes {

    def nodes = new HashMap<String,Node>()

    def List<String> keysNodes = new ArrayList<String>()

    def leftShift(InputStream input) {
        addPath(input)
    }

    def public addPath(InputStream input) {
        Node previousNode
        int level = 0;
        input.eachLine{ line ->
            printLine(line)

            if (!containsIp(line)) {
                if (line =~ /Resume/) {
                    previousNode = null
                }
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
        Node node = Node.fromTracepath(line)

        if (!nodes[node.ip]) {
            nodes[node.ip] = node
            node.level = level
            if (node.ip  in keysNodes) {
                node.boundary = true
            }
        }
        return nodes[node.ip]
    }

    def getNodes() {
        new TreeSet(nodes.values())
    }

    def private boolean containsIp(String line) {
        line =~ /(\d{1,3}\.){3}(\d{1,3})/
    }

    @Override
    String toString() {
        def sb = new StringBuffer()



        getNodes().each {node ->
            sb << "${node.level}. ${node.ip} (${node.name}) \n"
        }

        sb.toString()
    }

   def Set<Link> links() {
       def Set<Link> links = new TreeSet<Link>()
       nodes.values().each{Node a->
           a.linkedWith.each {b->
               if (b.linkedWith.isEmpty()){
                   return
               }
               links << new Link(a,b)
           }
       }
       return links
    }
}

