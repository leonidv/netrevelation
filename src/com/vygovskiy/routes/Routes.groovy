package com.vygovskiy.routes

import com.vygovskiy.routes.Node
import com.vygovskiy.routes.Link

class Routes {

    def Map<String,Node>nodesByIp = new HashMap<String,Node>()

    def List<String> keyNodes = new ArrayList<String>()

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

            boolean cycle = previousNode in node.linkedWith
            if (cycle) {
                print "\u27F2"
            }

            if (!((previousNode == null) || (cycle))) {
                previousNode.linkedWith << node
                node.parents << previousNode
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

        if (!nodesByIp[node.ip]) {
            nodesByIp[node.ip] = node
            node.level = level
            if (node.ip in keyNodes) {
                node.boundary = true
            }
        }
        return nodesByIp[node.ip]
    }

    def NavigableSet getNodes() {
        new TreeSet(nodesByIp.values())
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

    def removeLeafs() {
        def leafs = findLeafs(this.nodesByIp.values());
       
        while (!leafs.isEmpty()) {

            println "[before] nodesByIp.size() = " + this.nodesByIp.size()
            leafs.each{Node leaf ->
                removeFromParent(leaf)
                leaf.parents.clear()
                nodesByIp.remove(leaf.ip)
            }
            println "[after ] nodesByIp.size() = " + this.nodesByIp.size()

            leafs = findLeafs(this.nodesByIp.values())
        }

    }

    def private findLeafs(nodesByIp) {
       def result = nodesByIp.findAll{
            it.linkedWith.isEmpty() && it.parents.size() == 1 &&
                !(it.ip in keyNodes)
        }
        println "found leafs: ${result.size()}"
        return result
    }

    def private removeFromParent(node) {
        node.parents.each{ parent ->
            parent.linkedWith.remove(node)
        }
    }

    def Set<Link> links() {
        def Set<Link> links = new TreeSet<Link>()
        nodesByIp.values().each{Node a->
            a.linkedWith.each {b->
                links << new Link(a,b)
            }
        }
        return links
    }
}

