/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vygovskiy.routes

import com.vygovskiy.routes.Node

class Link implements Comparable {
    Node a, b

    Link(Node a, Node b) {
        if ((a <=> b) < 0 ) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
        
    }

    @Override
    int hashCode() {
        31 * (a.hashCode() * b.hashCode())
    }

    @Override
    boolean equals(Object o) {
        if (o == null) return false;
        assert this.class == o.class

        return o.hashCode() == hashCode()
    }

    @Override
    String toString() {
        "Link [${a.ip} - ${b.ip}]"
    }

    @Override
    int compareTo(other) {
        assert other != null
        assert other.class == this.class

        a <=> other.a
    }
}

