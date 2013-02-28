<%--
property                value                               comment
.form                   start,end,start_end                 值为start和start_end时，输出<form>；
                                                            值为end和start_end时，输出</form>
.name                   string                              form元素的name属性
.action                 url                                 form元素的action属性
.method                 POST/GET/DELETE/PUT                 form表单的method属性
.enctype                application/x-www-form-urlencoded   form表单的enctype属性
                        multipart/form-data
                        text/plain
.title                  string                              表单的标题
form.<itemName>         [itemType]<value>                   itemName是表单元素的id和name，itemType的取值为:
                                                            text,password,textarea,select等form表单元素，
                                                            value是表单的。另外，type也可为plain和custom。
                                                            type为plain时为纯文本；type为custom时，value是的输出
<itemName>.tip          string                              提示信息。显示在表单元素之后
<itemName>.label        string                              表单元素的标签。如果没有使用此属性，则使用默认的key为
                                                            <itemName>.label的国际化信息
<itemName>.options      对象列表                                使用select，radio，checkbox时使用的属性，
                                                            为可选值提供键值对，结合属性option.key和
                                                            option.value使用
<itemName>.option.key   options指定列表对象的属性                可选值的键
<itemName>.option.value options指定列表对象的属性                可选值的值


例：
    <jsp:useBean id="formMap" class="java.util.LinkedHashMap" scope="request" />
    <c:set target="${formMap}" property=".form" value="start_end" />
    <c:set target="${formMap}" property=".action" value="/doreg" />
    <c:set target="${formMap}" property=".title" value="用户注册" />
    <c:set target="${formMap}" property="form.username" value="[text]aaaa" />
    <c:set target="${formMap}" property="form.status" value="[select]" />
    <c:set target="${formMap}" property="form.status" value="[custom]<input type='text' />" />
    <spring:message code='username.label' var="label"/>
    <c:set target="${formMap}" property="email.label" value="${label}" />
    <c:set target="${formMap}" property="status.options" value="${dictionary}" />
    <c:set target="${formMap}" property="status.option.key" value="code" />
    <c:set target="${formMap}" property="status.option.value" value="value" />
    <c:import url="/WEB-INF/views/templates/form.jsp">
        <c:param name="form.configuration" value="formMap" />
    </c:import>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<style>
#customForm .control-label{
    width:140px;
    padding-top:0;
}
#customForm .controls{
    margin-left:150px;
}
#customForm .control-group{
    margin-bottom:10px;
}
</style>
<c:set value="${requestScope[param['form.configuration']]}" var="conf"></c:set>
<c:if test="${conf['.method'] == null}">
    <c:set target="${conf}" property=".method" value="post"></c:set>
</c:if>

<c:if test="${conf['.form'] == 'start_end' or conf['.form'] == 'start'}">
    <form id="customForm" class="form-horizontal" method="${conf['.method']}"
        <c:if test="${conf['.action'] != null}">
            action="${conf['.action']}" 
        </c:if>
        <c:if test="${conf['.name'] != null}">
            name="${conf['.name']}" 
        </c:if>
        <c:if test="${conf['.enctype'] != null}">
            enctype="${conf['.enctype']}" 
        </c:if>
    >
</c:if>
<div>${conf[".title"]}</div>
<c:forEach items="${conf}" var="p">
    <c:if test="${fn:startsWith(p.key, 'form.')}">
        <c:set value="${fn:replace(p.key, 'form.', '')}" var="item"/>
        <c:if test="${fn:startsWith(p.value,'[text]')}">
            <div class="control-group">
                <label  class="control-label" for="${item}"> 
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <div class="controls">
                    <input type="text" id="${item}" name="${item}" <c:if test="${conf[f:append(item, '.title')] != null}">
            title="${conf[f:append(item, '.title')]}" </c:if> value="${fn:replace(p.value, '[text]', '')}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[password]')}">
            <div class="control-group">
                <label  class="control-label"  for="${item}">
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <div class="controls">
                    <input type="password" id="${item}" name="${item}"  <c:if test="${conf[f:append(item, '.title')] != null}">
            title="${conf[f:append(item, '.title')]}" </c:if> value="${fn:replace(p.value, '[password]', '')}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[file]')}">
            <div class="control-group">
                <label  class="control-label"  for="${item}">
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <div class="controls">
                    <input type="file" id="${item}" name="${item}"   <c:if test="${conf[f:append(item, '.title')] != null}">
            title="${conf[f:append(item, '.title')]}" </c:if> />
                    <span> ${fn:replace(p.value, '[file]', '')} </span>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[hidden]')}">
             <input type="hidden" id="${item}" name="${item} " value="${fn:replace(p.value, '[hidden]', '')}"/>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[checkbox]')}">
            <div class="control-group">
                <label class="control-label"  >
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <c:set var="v" value="${fn:replace(p.value, '[checkbox]', '')}"></c:set>
                <div class="controls">
                <c:if test="${conf[f:append(item, '.options')] == null}">
                    <input type="checkbox" id="${item}" name="${item}" 
                        <c:if test="${v == 'true'}">
                            checked="checked" 
                        </c:if>
                    />
                </c:if>
                <c:if test="${conf[f:append(item, '.options')] != null}">
                    <div  class="controls">
                    <c:set var="okey" value="${conf[f:append(item, 'option.key')]}"></c:set>
                    <c:set var="ovalue" value="${conf[f:append(item, '.option.value')]}"></c:set>
                    <span>
                        <c:forEach items="${conf[f:append(item, '.options')]}" var="o" varStatus="status">
                            <span>
                                <input type="checkbox" id="${f:append(item, status.index)}" name="${item}"
                                    value="${o[okey]}" <c:if test="${v == o[okey]}">checked</c:if> /> 
                                <label for="${f:append(item, status.index)}">
                                ${o[ovalue]}
                                </label>
                            </span>
                        </c:forEach>
                    </span>
                    </div>
                </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[radio]')}">
            <div class="control-group">
                <label class="control-label"  >
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <c:set var="v" value="${fn:replace(p.value, '[radio]', '')}"></c:set>
                <c:set var="okey" value="${conf[f:append(item, 'option.key')]}"></c:set>
                <c:set var="ovalue" value="${conf[f:append(item, '.option.value')]}"></c:set>
                <div  class="controls">
                    <c:forEach items="${conf[f:append(item, '.options')]}" var="o" varStatus="status">
                        <span>
                            <input type="radio" id="${f:append(item, status.index)}" name="${item}"
                                value="${o[okey]}" <c:if test="${v == o[okey]}">checked</c:if> /> 
                            <label for="${f:append(item, status.index)}">
                            ${o[ovalue]}
                            </label>
                        </span>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[submit]')}">
          <div  class="control-group">
            <label class="control-label"  for="${item}">&nbsp;</label>
            <div  class="controls">
                <input type="submit" id="${item}" name="${item}" value="${fn:replace(p.value, '[submit]', '')}"/>
            </div>
        </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[reset]')}">
          <div  class="control-group">
            <label class="control-label"  for="${item}">&nbsp;</label>
            <div  class="controls">
                <input type="reset" id="${item}" name="${item}" value="${fn:replace(p.value, '[reset]', '')}"/>
            </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[button]')}">
          <div  class="control-group">
            <label class="control-label"  for="${item}">
                <c:if test="${conf[f:append(item, '.label')] != null }">
                    ${conf[f:append(item, '.label')]}:
                </c:if>
            </label>
            <div  class="controls">
                <input type="button" id="${item}" name="${item}" value="${fn:replace(p.value, '[button]', '')}"/>
            </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[select]')}" >
            <div  class="control-group">
                <label class="control-label"  for="${item}">
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <div  class="controls">
                    <select id="${item}" name="${item}" >
                        <c:set var="v" value="${fn:replace(p.value, '[select]', '')}"></c:set>
                        <c:set var="okey" value="${conf[f:append(item, '.option.key')]}"></c:set>
                        <c:set var="ovalue" value="${conf[f:append(item, '.option.value')]}"></c:set>
                        <c:forEach items="${conf[f:append(item, '.options')]}" var="o">
                            <option value="${o[okey]}" <c:if test="${v == o[okey]}">selected</c:if> >${o[ovalue]}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[textarea]')}" >
            <div class="control-group">
                <label class="control-label"   for="${item}">
                    ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
                </label>
                <div  class="controls">
                    <textarea id="${item}" name="${item}"   <c:if test="${conf[f:append(item, '.title')] != null}">
            title="${conf[f:append(item, '.title')]}" </c:if> >${fn:replace(p.value, '[textarea]', '')}</textarea>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[plain]')}">
        <div  class="control-group">
            <label class="control-label"  for="${item}">
                ${f:label(conf[f:append(item, '.label')], f:append(item, '.label'))}:
            </label>
            <div id="${item}"  class="controls">${fn:replace(p.value, '[plain]', '')}</div>
            </div>
        </c:if>
        <c:if test="${fn:startsWith(p.value, '[custom]')}">
        <div  class="control-group">
            <label class="control-label"  >
                <c:if test="${conf[f:append(item, '.label')] != null }">
                    ${conf[f:append(item, '.label')]}:
                </c:if>
            </label>
            <div class="controls">
                ${fn:replace(p.value, '[custom]', '')}
            </div>
            </div>
        </c:if>
        
        <c:if test="${conf[f:append(item, '.tip')] != null}">
            <div class="formtip">${conf[f:append(item, '.tip')]}</div>
        </c:if>
    </c:if>
</c:forEach>
<c:if test="${conf['.form'] == 'start_end' or conf['.form'] == 'end'}">
    </form>
</c:if>
