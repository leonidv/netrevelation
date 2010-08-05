package lv

class Node implements Comparable {
    String name;
    String ip;
    int level;
    Set<Node> linkedWith = new HashSet()

    Node(String line) {
        def String[] splitted = line.split()
        name = splitted[1]
        ip = splitted[2].replaceAll("[()]","")
    }

    @Override
    String toString() {
        "Node [ip = ${ip}, name = ${name}, linkedWith = ${linkedWith}]"
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

