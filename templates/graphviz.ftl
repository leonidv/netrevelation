graph G {
    node [
        fontsize = "6"
        shape = "ellipse"
        width = "0.3"
        height = "0.3"
        margin = "0.01"
    ]

    edge [
        arrowhead = "none"
    ]

    <#list links as link>
        "${link.a.ip}" -- "${link.b.ip}"
    </#list>
}