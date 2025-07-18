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
$('#userInfoBtn').addClass('active')

//获取用户信息
$(function () {
    $.ajax('/api/user/getUserInfo', {
        // 默认异步，false为同步
        async: false,
        // 请求方式
        type: 'post',
        data: {
            token: localStorage.getItem('token')
        },
        // 预期服务器返回内容的类型
        dataType: 'json',
        // 请求失败时调用此函数(自动)
        error: function(err){
            console.log(err);
        },
        // 请求成功时调用此函数(自动), data服务器返回的内容，data为json格式, data在响应主体中
        success: function (data){
            console.log(data)
            if (data.code == 0) {
                $('input[name="name"]').val(data.data.name)
                $('input[name="phone"]').val(data.data.phone)
                $('input[name="address"]').val(data.data.address)
            }
        }
    })
})

//点击修改按钮
$('#handleUserInfo').click(() => {
    if($('input[name="name"]').val().length <= 0){
        $('#dangers').fadeIn().html('收货人不能为空')
        setTimeout(() => {
            $('#dangers').fadeOut()
        }, 1000)
        return
    }
    if($('input[name="phone"]').val().length <= 0){
        $('#dangers').fadeIn().html('手机号不能为空')
        setTimeout(() => {
            $('#dangers').fadeOut()
        }, 1000)
        return
    }
    if($('input[name="address"]').val().length <= 0){
        $('#dangers').fadeIn().html('收货地址不能为空').show()
        setTimeout(() => {
            $('#dangers').fadeOut().hide()
        }, 1000)
        return
    }
    $.post('/api/user/updateUserInfo', {
            name: $('input[name="name"]').val(),
            phone: $('input[name="phone"]').val(),
            address: $('input[name="address"]').val(),
            token: localStorage.getItem('token')
        },
        function (res){
            console.log(res)
            if (res.code == 0) {
                alert('修改成功')
                location.href = '/web/userInfo.html'
            }else{
                alert(res.msg)
            }
    }, 'json')
})