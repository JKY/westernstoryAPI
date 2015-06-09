<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
    系统提示 | 西口小事
</title>
    <style>
        body {
            font-size: 16px;
            color: rgba(0, 0, 0, 0.76);
            -webkit-font-smoothing: antialiased;
            font-family: "Hiragino Sans GB", "Open Sans", Arial, "Microsoft YaHei",
            "微软雅黑", "STHeiti", "WenQuanYi Micro Hei", SimSun, sans-serif;
        }
        .container {
            padding: 60px 20px 0 20px;
        }
        .title {
            font-size: 1.2em;
            margin-bottom: 32px;
            text-align: center;
        }
        .nice {
            width: 120px;
            height: 120px;
            border-radius: 60px;
            background-color: #48ad2c;
            color: white;
            text-align: center;
            line-height: 120px;
            margin: 0 auto;
        }
        .bad {
            text-align: center;
            color: red;
        }
    </style>
</head>
<body>
	<div id="content" class="container">
        <div class="title">西口小事－优惠券</div>

		<#if e?has_content>
            <div class="bad">${e}</div>
		<#else>
			<div class="nice">√ 验证成功</div>
		</#if>
	</div>
</body>
</html>