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

$('#registerBtn').addClass('active')

// 注册逻辑
$('#handle_register').click(function(){
    // 当用户点击id="handle_register"标签时触发
    // 检验逻辑
    // 1、用户名
    let username = $('#username').val()
    if(!username){
        tips('用户名不能为空！')
        return
    }
    // 2、密码
    let password = $('#password').val()
    if(password.length < 6 || password.length > 16){
        tips('密码长度应为6-16之间！')
        return
    }
    if(!(/[a-z]/.test(password) && /[A-Z]/.test(password) && /[0-9]/.test(password))){
        tips('密码必须包含大小写字母和数字！')
        return
    }
    // 3、真实姓名
    let real_name = $('#real_name').val()
    if(!real_name){
        tips('真实姓名不能为空！')
        return
    }
    // 4、手机号
    let phone = $('#phone').val()
    if(!/^1[3-9]\d{9}$/.test(phone)){
        tips('手机号格式不正确！')
        return
    }
    // 5、家庭住址
    let address = $('#address').val()
    if(!address){
        tips('家庭住址不能为空！')
        return
    }
    let url = '/api/user/register'
    $.post(url, {username, password, name: real_name, phone, address}, function (res){
        console.log(res)
        if(res.code === 0){
            // 注册成功，跳转到登录页面
            $('#alert_success').html("注册成功！").css("opacity", 1)
            setTimeout(function(){
                window.location.href = '/web/login.html'
            }, 1500)
        }else{
            tips(res.msg)
        }
    }, 'json')
})

function tips(title){
    $('#alert').html(title).css('opacity', 1)
    // 1.5秒后自动消失，1500毫秒后执行第一次参数函数（）
    // 一次性定时器
    setTimeout(function(){
        $('#alert').html(title).css('opacity', 0)
    }, 1500)
}
