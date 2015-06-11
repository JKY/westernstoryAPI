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
            margin: 60px 4%;
            color: #fff;
            border: 1px solid #fff;
            padding: 12px;
        }
        form {
            width: 100%;
        }

        form .password,
        form .submit{
            border: 0;
            height: 32px;
        }

        .ticket-wrap {
            text-align: center;
            margin-bottom: 16px;
            line-height: 2em;
        }
        .ticket-wrap .name {
            font-weight: bold;
        }
        .ticket-wrap .time {
            font-size: 0.8em;
        }

        form .submit{
            background-color: #48ad2c;
            border: 1px solid #fff;
            color: #fff;
            width: 80px;
            height: 40px;
            border-radius: 20px;
            position: absolute;
            left: 50%;
            margin-left: -40px;
        }
        form .password {
            width: 96%;
            padding: 0 2%;
        }
        .submit-wrap {
            position: relative;
            height: 48px;
            margin-top: 16px;
        }
    </style>
</head>
<body>
	<div id="content" class="container">
        <div class="ticket-wrap">
            <div class="name">${ticket.name}</div>
            <div class="address">${ticket.address}</div>
            <div class="time">
                ${ticket.startTime?number_to_datetime?string("yyyy年MM月dd日")}
                    -
                ${ticket.endTime?number_to_datetime?string("yyyy年MM月dd日")}
            </div>
        </div>
		<form action="identify" method="POST">
            <input class="password" type="password" name="password" placeholder="请输入密码"/>
            <div class="submit-wrap">
                <input class="submit" type="submit" value="确定"/>
            </div>

            <input type="hidden" name="tid" value="${tid}"/>
            <input type="hidden" name="uid" value="${uid}"/>
            <input type="hidden" name="token" value="${token}"/>
        </form>
	</div>
</body>
</html>