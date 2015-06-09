<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
    优惠券验证 | 西口小事
</title>
    <style>
        body {
            font-size: 16px;
            background: #48ad2c;
            color: #fff;
            color: rgba(0, 0, 0, 0.76);
            -webkit-font-smoothing: antialiased;
            font-family: "Hiragino Sans GB", "Open Sans", Arial, "Microsoft YaHei",
            "微软雅黑", "STHeiti", "WenQuanYi Micro Hei", SimSun, sans-serif;
            overflow-y: auto;
        }
        .container {
            margin: 60px 24px;
            color: #fff;
        }
        form {
            width: 100%;
        }
        form .item {
            padding: 0 10%;
        }
        form .title {
            text-align: center;
            font-size: 1.2em;
            margin-bottom: 24px;
        }
        form input {
            width: 100%;
            padding: 0 12px;
        }
        form .password,
        form .submit{
            border: 0;
            height: 32px;
        }
        form .submit{
            margin-top: 24px;
            background-color: #48ad2c;
            border: 1px solid #fff;
            color: #fff;
        }
    </style>
</head>
<body>
	<div id="content" class="container">
		<form action="identify" method="POST">
            <div class="item title">请输入密码：</div>
            <div class="item"><input class="password" type="password" name="password"/></div>
            <div class="item"><input class="submit" type="submit" value="确定"/></div>

            <input type="hidden" name="tid" value="${tid}"/>
            <input type="hidden" name="uid" value="${uid}"/>
            <input type="hidden" name="token" value="${token}"/>
        </form>
	</div>
</body>
</html>