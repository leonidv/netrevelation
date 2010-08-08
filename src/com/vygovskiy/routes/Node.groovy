package com.vygovskiy.routes

class Node implements Comparable {

    public static fromNmap(String line) {
        def String[] splitted = line.split()
        Node node;
        if (splitted.length == 7) {
            // They has equals splitted position
            node = fromTracepath(line)
        } else {
            // If nmap can't get name, he outputs nothing in second postion
            node = new Node()
            node.ip = splitted[1].replaceAll("[()]","")
            node.name = node.ip
        }
        return node;
    }

    public static Node fromTracepath(String line) {
        def String[] splitted = line.split()
        Node node = new Node()
        node.name = splitted[1]
        node.ip = splitted[2].replaceAll("[()]","")

        return node
    }

    String name;
    String ip;
    int level;
    boolean boundary = false;

    Set<Node> parents = new HashSet()
    Set<Node> linkedWith = new HashSet()

    @Override
    String toString() {
        def StringBuilder p = new StringBuilder()
        parents.each { parent ->
            p << "${parent.ip}"
        }
        "Node [ip = ${ip}, name = ${name}, parents = ${p}, linkedWith = ${linkedWith}]"
    }

    @Override
    int hashCode() {
        ip.hashCode()
    }

    @Override
    boolean equals(o) {
        if (o == null) return false

        assert o.getClass() == getClass()
        this.hashCode() == o.hashCode()
    }

    int compareTo(Object other) {
        assert other.class == this.class

        int cmpr = level <=> other.level
        if (cmpr == 0) {
            cmpr = (ip <=> other.ip)
        }
        return cmpr
    }
}

