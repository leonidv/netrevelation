graph G {
    node [
        fontsize = "6"
        shape = "ellipse"
        width = "0.3"
        height = "0.3"
        margin = "0.01"
        colorscheme = "paired12"
    ]

    edge [
        arrowhead = "none"
    ]

    <#assign x = 1>
    <#list links as link>
        <#if link.a.boundary>
             "${link.a.ip}" [fillcolor="${x}", style="filled"]
             <#assign x = x +1>
        </#if>
        <#if link.b.boundary>
             "${link.b.ip}" [fillcolor="${x}", style="filled"]
             <#assign x = x +1>
        </#if>
    </#list>

    <#list links as link>
        "${link.a.ip}" -- "${link.b.ip}"
    </#list>
}