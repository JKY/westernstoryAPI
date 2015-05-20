<#macro formatExactTime time>
	<#if time??>
		<#assign now = .now?long>
		<#assign interval = (now-time)/1000>
		<#if interval lt 60>
        <span>${interval}秒前</span>
		<#elseif interval lt 3600>
        <span>${interval/60}分钟前</span>
		<#elseif interval lt 86400>
        <span>${interval/3600}小时前</span>
		<#elseif interval lt 2592000>
        <span>${interval/86400}天前</span>
		<#elseif interval lt 31536000>
        <span>${interval/2592000}月前</span>
		<#else>
        <span>${time?number_to_datetime?string("yyyy.MM.dd")}</span>
		</#if>
	</#if>
</#macro>

<#macro formatTime time>
    <#if time??>
		<#assign nowYear = .now?string("yyyy")?number>
		<#assign nowMonth= .now?string("M")?number>
		<#assign nowDay = .now?string("d")?number>

		<#assign publishYear=time?number_to_datetime?string("yyyy")?number>
		<#assign publishMonth=time?number_to_datetime?string("M")?number>
		<#assign publishDay=time?number_to_datetime?string("d")?number>

		<#if nowYear==publishYear>
			<#if nowMonth==publishMonth>
				<#if nowDay-publishDay == 0 >
					今天
				<#elseif nowDay-publishDay == 1>
					昨天
				<#elseif nowDay-publishDay == 2>
				    前天
				<#else>
					${time?number_to_datetime?string("MM月dd日")}
				</#if>
			<#else>
				${time?number_to_datetime?string("MM月dd日")}
			</#if>
		<#else>
			${time?number_to_datetime?string("yyyy年MM月dd日")}
		</#if>
    </#if>
</#macro>
