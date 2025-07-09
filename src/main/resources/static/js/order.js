// 获取所有商品分类分类
$.ajax('/api/index/kinds', {
    // 默认异步，false为同步
    async: false,
    // 请求方式
    // type: 'post',
    // 预期服务器返回内容的类型
    dataType: 'json',
    // 请求失败时调用此函数(自动)
    error: function(err){
        console.log(err);
    },
    // 请求成功时调用此函数(自动), data服务器返回的内容，data为json格式, data在响应主体中
    success: function (data){
        console.log(data);
        let kindsTags;
        if (data.code == 0) {
            // 获取分类列表
            let kinds = data.data
            console.log(kinds)
            // 用于存放所有的 li标签
            kindsTags = ''
            kinds.forEach(function(item, index){
                // 获取分类名称
                let name = item.name
                kindsTags = kindsTags + '<li><a href="/web/kinds.html?type='+item.id+'">' + name + '</a></li>'
            })
            // 把上边拼接好的字符串，放入id="allKinds"的标签中
            $('#allKinds').html(kindsTags)

        }
    }
})

// 检查是否登录
checkLogin()
$('#orderListBtn').addClass('active')

// 获取所有订单
$.ajax({
    url: '/api/order/list',
    type: 'POST',
    async: false, // 设置ajax为同步执行
    data: {
        token: token
    },
    // 预期服务器返回内容的类型
    dataType: 'json',
    // 请求失败时调用此函数(自动)
    error: function(err){
        console.log(err);
    },
    // 请求成功时调用此函数(自动), data服务器返回的内容，data为json格式, data在响应主体中
    success: function (res){
        // console.log(data);
        let orderInfoTags;
        if (res.code == 0) {
            // 获取订单列表
            let orders = res.data
            // 用于存放所有的标签
            orderInfoTags = '<tr>\n' +
                '                    <th width="10%">ID</th>\n' +
                '                    <th width="10%">总价</th>\n' +
                '                    <th width="20%">商品详情</th>\n' +
                '                    <th width="20%">收货信息</th>\n' +
                '                    <th width="10%">订单状态</th>\n' +
                '                    <th width="10%">支付方式</th>\n' +
                '                    <th width="10%">下单时间</th>\n' +
                '                    <th width="10%">操作</th>\n' +
                '                </tr>'
            orders.forEach(function(item, index){
                let goodsListTags = ''
                item.itemList && item.itemList.forEach(function(item2, index2){
                    goodsListTags += `<p>${item2.title}(${item2.price} x ${item2.amount})</p>`
                })

                orderInfoTags = orderInfoTags + `
                    <tr>
                        <td><p>`+item.id+`</p></td>
                        <td><p>`+item.total+`</p></td>
                        <td>`+ goodsListTags +`</td>
                        <td>
                            <p>${item.name}</p>
                            <p>${item.phone}</p>
                            <p>${item.address}</p>
                        </td>
                        <td>
                            <p>`+
                                (item.status == 1 ? '未付款' : item.status == 2 ? '<span style="color:red;">已付款</span>' : item.status == 3 ? '配送中' : '已完成')
                            +`</p>
                        </td>
                        <td>
                            <p>`+
                                (item.paytype == 1 ? '微信' : item.paytype == 2 ? '支付宝' : item.paytype == 3 ? '货到付款' : '')
                            +`</p>
                        </td>
                        <td><p>${item.systime}</p></td>
                        <td>`+
                    (item.status == 1 ? `
                        <a class="btn btn-success" href="topay?orderid=${item.id}">支付</a>
                        ` : `
                        <a class="btn btn-default"  href="javascript:void(0)">已付款</a>
                        `)+`
                        </td>
                    </tr>
                `
            })
            if(orders.length <= 0){
                orderInfoTags = `<div class="alert alert-info">空空如也</div>`
            }
            // 获取页面中id="allKinds"的标签
            // 把上边拼接好的字符串，放入id="allKinds"的标签中
            $('#orderList').append(orderInfoTags)

        }
    }
})

$('#submit').click(function(){
    // 当点击按钮时次函数执行
    let kw = $('#kw').val() // 获取输入框的值
    // 判断输入框的内容是否为空
    if(!kw){
        alert('输入框不能为空！')
        return
    }
    // url拼接参数，跳转到搜索页面
    location.href = '/web/search.html?kw=' + kw
})
