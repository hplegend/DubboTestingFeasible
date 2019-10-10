<%--
  Created by IntelliJ IDEA.
  User: hp.he
  Date: 2019/10/9
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dubbo dubbo服务调用测试</title>
    <meta charset="UTF-8">
    <script src="/js/jquery-1.7.2.min.js"></script>
    <style type="text/css">
        .input_bo {
            border: 1px solid #dcdcdc;
            width: 900px;
            position: relative;
            margin-top: 60px;
            margin-left: 6px;
            padding: 22px 0 17px 22px
        }

        .input_bo .h4 {
            font-weight: bold;
            color: #2e9edb;
            font-size: 16px;
            padding: 0 10px;
            height: 30px;
            line-height: 30px;
            background-color: #fff;
            position: absolute;
            top: -35px;
            left: 32px;
            text-align: center
        }

        .input_bo .table {
            position: relative;
            margin-top: 15px;
        }

        .input_bo .table .th input, .input_bo .table td input {
            vertical-align: middle
        }

        .input_bo .table .th {
            height: 30px
        }

        .inputInfo {
            width: 300px;
        }

        .title {
            text-align: right;
        }

        .btn {
            text-align: center;
        }

        .hrClass {
            border: 1px solid #dcdcdc;
        }

        .result {
            display: none;
        }
    </style>

    <script type="text/javascript">

        $(document).ready(function () {
            $("#returnResultDiv").hide();
        });

        function beginDubboCallService() {
            var dubboRegistryGroup = $("#dubboRegistryGroup").val();
            var dubboRegistryProtocol = $("#dubboRegistryProtocol").val();
            var dubboRpcProtocol = $("#dubboRpcProtocol").val();
            var dubboZkAddress = $("#dubboZkAddress").val();

            var interfaceName = $("#interfaceName").val();
            var methodName = $("#methodName").val();

            var serviceVersion = $("#serviceVersion").val();
            var serviceGroup = $("#serviceGroup").val();

            var parameterTypes = new Array();
            var parameterValues = new Array();

            $("input[id='parameterTypes']").each(function () {
                parameterTypes.push($(this).val());
            })

            $("input[id='parameterValues']").each(function () {
                parameterValues.push($(this).val());
            })


            $.ajax({
                type: 'post',
                url: '/dubbo/method/callService.json',
                traditional: true,
                data: {
                    dubboGroup: dubboRegistryGroup,
                    registryProtocol: dubboRegistryProtocol,
                    registryAddress: dubboZkAddress,
                    rpcProtocol: dubboRpcProtocol,
                    interfaceName: interfaceName,
                    methodName: methodName,
                    serviceVersion: serviceVersion,
                    serviceGroup: serviceGroup,
                    parameterTypes: parameterTypes,
                    parameterValues: parameterValues
                },
                success: function (data) {
                    if (data.ret) {
                        var retList = "<ul>";
                        retList += "<li>" + data.data + "</li>"
                        /*
                            $.each(data.data, function (i, indexDate) {
                                retList += "<li>" + indexDate + "</li>"
                            });
    */
                        retList += "</ul>";

                        $("#returnResultDiv,#returnResult").show();
                        $("#returnResult").html("<h4>" + retList + "</h4>")

                    } else {
                        alert(data.msg);
                        return false;
                    }
                }
            });
        }

        var num = 0;

        function addParameterRow() {
            num++;
            var tr = document.createElement("tr");
            var xh = document.createElement("td");
            var xm = document.createElement("td");

            xh.innerHTML = "<input id='parameterTypes' name ='parameterTypes' type='text' />";
            xm.innerHTML = "<input   id='parameterValues' name ='parameterValues' type='text' style='width:400px; height:200px;' />";
            var del = document.createElement("td");
            del.innerHTML = "<a href='javascript:;' onclick='parameterRowDel(this)' >删除</a>";
            var tab = document.getElementById("parameterTable");
            tab.appendChild(tr);
            tr.appendChild(xh);
            tr.appendChild(xm);
            tr.appendChild(del);
            var tr = document.getElementsByTagName("tr");
        }


        // 创建删除函数
        function parameterRowDel(obj) {
            var tr = obj.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
            num--;
        }

    </script>
</head>
<body>

<div class="input_bo">
    <h4 class="h4">dubbo基本协议配置</h4>
    <table cellspacing="0" class="table" cellpadding="0" border="0">

        <tr class="th">
            <td class="title">dubbo registry group:</td>
            <td>
                <input type="text" id="dubboRegistryGroup" name="dubboRegistryGroup" class="inputInfo">
            </td>
        </tr>

        <tr class="th">
            <td class="title">dubbo registry protocol:</td>
            <td>
                <input type="text" id="dubboRegistryProtocol" name="dubboRegistryProtocol" class="inputInfo"
                       value="zookeeper">
            </td>
        </tr>

        <tr class="th">
            <td class="title">dubbo RPC protocol:</td>
            <td>
                <input type="text" id="dubboRpcProtocol" name="dubboRpcProtocol" class="inputInfo" value="dubbo">
            </td>
        </tr>

        <tr class="th">
            <td class="title">dubbo zookeeper address:</td>
            <td>
                <input type="text" id="dubboZkAddress" name="dubboZkAddress" class="inputInfo">
            </td>
        </tr>
    </table>
</div>


<div class="input_bo">
    <h4 class="h4">参数区</h4>
    <table cellspacing="0" class="table" cellpadding="0" border="0">
        <tr class="th">
            <td class="title">方法接口全限定名:</td>
            <td>
                <input type="text" id="interfaceName" name="interfaceName" class="inputInfo"
                       value="com.hplegend.api.SimpleDubboTestApi">
            </td>
        </tr>

        <tr class="th">
            <td class="title">方法名:</td>
            <td>
                <input type="text" id="methodName" name="methodName" class="inputInfo"
                       value="doTestBeanParameter">
            </td>
        </tr>


        <tr class="th">
            <td class="title">服务的版本:</td>
            <td>
                <input type="text" id="serviceVersion" name="serviceVersion" class="inputInfo"
                       value="1.0.0">
            </td>
        </tr>

        <tr class="th">
            <td class="title">服务的分组group:</td>
            <td>
                <input type="text" id="serviceGroup" name="serviceGroup" class="inputInfo"
                       value="">
            </td>
        </tr>

    </table>

    <table cellspacing="0" class="table" cellpadding="0" border="0" id="parameterTable">
        <tr>
            <th>参数类型</th>
            <th>参数值（String类型）</th>
        </tr>


    </table>


    <input type="button" value="添加一行" onclick="addParameterRow()"/>

</div>


<div class="input_bo">
    <h4 class="h4">操作区</h4>
    <table cellspacing="0" class="table" cellpadding="0" border="0">
        <tr class="th">
            <td colspan="2" class="btn">
                <input type="button" name="search" value="开始发现服务和接口" onclick="beginDubboCallService()"/>
            </td>
        </tr>


    </table>
</div>


<div class="input_bo" id="returnResultDiv">
    <h4 class="h4">结果输出</h4>
    <div id="returnResult" class="result"></div>
</div>

</body>
</html>

