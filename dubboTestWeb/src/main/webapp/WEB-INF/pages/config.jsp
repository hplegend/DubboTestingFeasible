<%--
  Created by IntelliJ IDEA.
  User: hp.he
  Date: 2019/10/9
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: hp.he
  Date: 2019/1/11
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dubbo 接口测试</title>
    <meta charset="UTF-8">
    <script src="/js/jquery-1.7.2.min.js"></script>
    <style type="text/css">
        .input_bo {
            border: 1px solid #dcdcdc;
            width: 750px;
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

        function beginDetectService() {
            var dubboRegistryGroup = $("#dubboRegistryGroup").val();
            var dubboRegistryProtocol = $("#dubboRegistryProtocol").val();
            var dubboRpcProtocol = $("#dubboRpcProtocol").val();
            var dubboZkAddress = $("#dubboZkAddress").val();

            $.ajax({
                type: 'get',
                url: '/dubbo/method/listMethods.json',
                data: {
                    dubboGroup: dubboRegistryGroup,
                    registryProtocol: dubboRegistryProtocol,
                    registryAddress: dubboZkAddress,
                    rpcProtocol: dubboRpcProtocol
                },
                success: function (data) {
                    if (data.ret) {
                        var retList = "<ul>";
                        $.each(data.data, function (i, indexDate) {
                            retList += "<li>" + indexDate + "</li>"
                        })

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
    <h4 class="h4">操作区</h4>
    <table cellspacing="0" class="table" cellpadding="0" border="0">
        <tr class="th">
            <td colspan="2" class="btn">
                <input type="button" name="search" value="开始发现服务和接口" onclick="beginDetectService()"/>
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

