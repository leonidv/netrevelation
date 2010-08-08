graph G {
    <#setting locale = "en_US">
    <#assign size = 0.025>
    node [
        fontsize = "3"
        shape = "circle"
        width = "${size}"
        height = "${size}"
        margin = "0.001"
        label =""
    ]

    edge [
        arrowhead = "none",
        color = "gray"
    ]

    <#assign x = 1>
    <#list nodes as node>
        <#assign nodeSize = size * (node.parents?size + node.linkedWith?size)>
        "${node.ip}" [
            <#if node.boundary>
                <#assign nodeSize = 0.5>
                style="filled"
                color = "red"                
                shape = "triangle"
            </#if>

            <#if (nodeSize > 0.5)>
                <#assign nodeSize = 0.5>
                color = "blue"
                style = "filled"
            </#if>
            width = "${nodeSize}"
            height = "${nodeSize}"
        ]
    </#list>

    <#list links as link>
        "${link.a.ip}" -- "${link.b.ip}"
    </#list>
}