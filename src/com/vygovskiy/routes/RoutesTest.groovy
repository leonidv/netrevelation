/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vygovskiy.routes

import com.vygovskiy.routes.*
import com.vygovskiy.*
import static com.vygovskiy.DataProvider.*

def makeLink(a,b) {
    sA = "1.1.1."+a
    sB = "1.1.1."+b

    new Link(
        new Node(ip : sA, name : sA),
        new Node(ip : sB, name : sB)
    )
}

def check01() {
    Routes routes = new Routes()

    routes.keyNodes << "1.1.1.1"
    routes.keyNodes << "1.1.1.4"
    routes << fromFile("traces/leafs-remove.test")

    def withLeafs = routes.links();

    expected = new HashSet<Node>()
    expected << makeLink(1,2)

    expected << makeLink(2,3)
    expected << makeLink(2,5)
    expected << makeLink(2,8)

    expected << makeLink(3,4)
    expected << makeLink(3,8)

    expected << makeLink(5,6)
    expected << makeLink(5,7)

    assert expected == withLeafs

    routes.removeLeafs()
    def withoutLeafs = routes.links()

    expected = new HashSet<Node>()
    expected << makeLink(1,2)

    expected << makeLink(2,3)
    expected << makeLink(2,8)

    expected << makeLink(3,4)
    expected << makeLink(3,8)
    assert expected == withoutLeafs
}

def check02() {
    
}