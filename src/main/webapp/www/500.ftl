<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
	<#if e?has_content>
		系统提示 | 西口小事
	<#else>
    	开了小差 | 西口小事
	</#if>
</title>
</head>
<body>
	<div id="content" class="art-ctn container">
		<#if e?has_content>
			${e}!
		<#else>
			开了小差！
		</#if>
	</div>
</body>
</html>