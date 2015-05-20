<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${article.title} | 西口小事</title>
    <meta name="description" content="${article.title}"/>
    <#import "ftl/macro_global.ftl" as G>
    <link rel="stylesheet" href="../css/article_detail.css">
</head>
<body>

<#assign currentYear = .now?string("yyyy")?number>

<div class="container" >
    <div class="title">${article.title}</div>
    <div class="subinfo">
        <span class="subinfo_item">
        <#if article.publishTime?number_to_datetime?string("yyyy") == currentYear>
            ${article.publishTime?number_to_datetime?string("MM月dd日")}
        <#else>
            ${article.publishTime?number_to_datetime?string("yyyy年MM月dd日")}
        </#if>
        </span>
    </div>
    <div class="tags">
    <#list article.tags as tag>
        <a class="no-link" href="#">${tag.name}</a>
    </#list>
    </div>
    <div class="summary">${article.summary}</div>
    <div class="article-content">
        ${article.content}
    </div>
</div>

</body>
</html>