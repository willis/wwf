<#--显示总记录数,只有同时设置了总记录数和显示总记录数时才有效-->   
<#if (parameters.totalRecord?exists) && (parameters.showTotalRecord?exists) && (parameters.showTotalRecord == true)>   
    <span>${parameters.totalRecord}</span><#rt/>   
</#if>   
   
<#--打印选择页的面板 -->   
<#--首先,当parameters.pageLimit为空时将其设定为total-->   
<#if (parameters.pageLimit?exists)>   
    <#assign limit = parameters.pageLimit />   
<#else>   
    <#assign limit = parameters.totalPage />   
</#if>   

<#--打印选择页的面板结束-->   
   
<#--显示总页数-->   
<#if parameters.showTotalPage?exists && (parameters.showTotalPage == true)>   
    <span>..${parameters.totalPage}</span><#rt/>   
</#if>   
   
<#--打印翻页面板的宏,配合printPage,printButton-->   
<#macro pagePanel cur total limit url curCssClass = "cur_page"><#--curCssClass默认值为cur_page-->   
    <#--limit的中间数-->   
    <#assign l_mid = (limit/2)?int + 1 />   
        
    <#--total的中间数-->   
    <#assign t_mid = (total/2)?int />   
        
    <#--情况一：总页数小于等于限制显示页数，这时显示所有页-->   
    <#if total <= limit>   
        <@printButton direct="left" valuable = false />   
        <@printPage left = 1 right = total curcur = cur urlurl = url curCssClasscurCssClass = curCssClass />   
        <@printButton direct="right" valuable = false />                    
    <#else>   
    <#--情况二：总页数大于限制显示页数，这时又分三种情况-->   
        <#--情况1:显示的limit个页号在total个页面中偏向左端,1作为最左边的页号,当前页没能显示在中间,偏左,例:    
            total = 20,cur = 2,limit = 5.显示的页面为:1 [2] 3 4 5    
            这种情况 cur <= l_mid     
        -->   
        <#if cur <= l_mid>   
            <@printButton direct="left" valuable = false />   
            <@printPage left = 1 right = limit curcur = cur urlurl = url curCssClasscurCssClass = curCssClass />   
            <@printButton direct="right" valuable = true />   
                
        <#--情况2:显示的limit个页号在total个页面中偏向右端,total作为最右边的页号,当前页没能显示在中间,偏右,例:    
            total = 20,cur = 19,limit = 5.显示的页面为:16 17 18 [19] 20    
            这种情况 cur > total - l_mid    
        -->   
        <#elseif (cur > (total - l_mid))>   
            <@printButton direct="left" valuable = true />   
            <@printPage left = (total - limit + 1) right = total curcur = cur urlurl = url curCssClasscurCssClass = curCssClass />   
            <@printButton direct="right" valuable = false />   
                
        <#--在中间的情况-->   
        <#else>   
            <@printButton direct="left" valuable = true />   
            <@printPage left = (cur - l_mid + 1) right = (cur + l_mid -1) curcur = cur urlurl = url curCssClasscurCssClass = curCssClass />   
            <@printButton direct="right" valuable = true />   
        </#if>   
    </#if>   
        
</#macro>   
   
<#--根据最左与最右的页号来打印所显示的页面，当前页为的cssStyle为curCssClass-->   
<#macro printPage left right cur url curCssClass>   
    <#list left..right as p>   
        <#if p == cur>   
            <span class = "${curCssClass}" >${p}</span><#rt/>   
        <#else>   
            <a href = "<@makeURL text=url page=p />">${p}</a><#rt/>   
        </#if>          
    </#list>        
</#macro>   
   
<#--翻页控制按钮,direct选择"left"或"right",avaluable设置是否有链接-->   
<#macro printButton direct valuable = true>   
    <#if direct == "left">   
        <#if valuable>   
            <a href="<@makeURL text=parameters.url page=1 />"> << </a><#rt/>   
            <a href="<@makeURL text=parameters.url page=parameters.curPage-1 />"> < </a><#rt/>   
        <#else>   
            <span><<</span><#rt/>   
            <span><</span><#rt/>   
        </#if>   
    <#else>   
        <#if valuable>   
            <a href="<@makeURL text=parameters.url page=parameters.curPage+1 />"> > </a><#rt/>   
            <a href="<@makeURL text=parameters.url page=parameters.totalPage />"> >> </a><#rt/>   
        <#else>   
            <span>></span><#rt/>   
            <span>>></span><#rt/>   
        </#if>   
    </#if>   
</#macro>   
   
<#--产生动态URL的宏-->   
<#macro makeURL text page>   
    <#if text?last_index_of("{page}") < 0>   
        ${text}?page=${page}    
    <#else>   
        ${text?replace("{page}",page)}    
    </#if>      
</#macro>   