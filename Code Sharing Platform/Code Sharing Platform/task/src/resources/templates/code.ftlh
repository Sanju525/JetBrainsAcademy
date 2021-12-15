<!DOCTYPE html>
<html lang="en">
<head>
<title>${title}</title>

<link rel="stylesheet"
       target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
<script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<style>
body {
    margin: auto;
    width: 50%;
    padding: 10px;
}
span {
    color: green;
}
</style>
</head>
<body>
<#if codeList?has_content>
<#list codeList as currentCode>
<div>
<p><span id="load_date">${currentCode.date}</span></p>
<pre id="code_snippet"><code>${currentCode.code}</code></pre>
</#list>
</#if>
<#if codeById?has_content>
<p><span id="load_date">${codeById.date}</span></p>
<span id="views_restriction">${codeById.views} more views</span>
<span id="time_restriction">The code will be available for ${codeById.time} seconds.</span>
<pre id="code_snippet"><code>${codeById.code}</code></pre>

</#if>
</div>
</body>
</html>