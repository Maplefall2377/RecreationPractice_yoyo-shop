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


$('#loginBtn').addClass('active')

function tips(title){
    $('#alert').html(title).css('opacity', 1)
    // 1.5秒后自动消失，1500毫秒后执行第一次参数函数（）
    // 一次性定时器
    setTimeout(function(){
        $('#alert').html(title).css('opacity', 0)
    }, 1500)
}

// 点击登录按钮
$('#handle_login').click(function (){
    // 校验登录信息
    // 1、用户名
    let username = $('#username').val()
    if(!username){
        tips('用户名不能为空！')
        return
    }
    // 2、密码
    let password = $('#password').val()
    if(!password){
        tips('密码不能为空！')
        return
    }
    $.ajax({
        url: '/api/user/login',
        type: 'POST',
        data: {
            username: username,
            password: password
        },
        dataType: 'json',
        success: function (res){
            console.log(res)
            if(res.code == 0){
                // 把后端返回的数据保存到浏览器中
                localStorage.setItem('token',res.data)

                $('#alert_success').html("登录成功！").css("opacity", 1)
                setTimeout(function(){
                    window.location.href = '/web/index.html'
                }, 1500)
            }else{
                tips(res.msg)
            }
        }
    })
})
