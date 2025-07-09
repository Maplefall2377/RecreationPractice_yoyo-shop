// 从缓存中获取数据
let token = localStorage.getItem('token')
// true已经登录 false未登录
let isLogin = false
// 判断是否登录
if(token){
    // 校验登录是否过期
    $.ajax({
        url: '/api/user/checkLogin',
        type: 'POST',
        async: false, // 设置ajax为同步执行
        data: {
            token: token
        },
        dataType: 'json',
        success: function (res){
            console.log(res)
            if(res.code == 0){
                isLogin = true
                // 登录
                let htmlTag = '<li id="orderListBtn"><a href="/web/order.html">我的订单</a></li>\n' +
                    '                        <li id="userInfoBtn"><a href="/web/userInfo.html">个人中心</a></li>\n' +
                    '                        <li><a href="javascript:void(0)" onclick="logout()">退出</a></li>'
                $('#nav').append(htmlTag)
            }else{
                isLogin = false
                localStorage.removeItem("token")
                console.log('未登录')
                // 未登录
                let htmlTag = '<li id="registerBtn"><a href="/web/register.html">注册</a></li>\n' +
                    '                        <li id="loginBtn"><a href="/web/login.html">登录</a></li>'
                $('#nav').append(htmlTag)
            }
        }
    })
}else{
    // 未登录
    let htmlTag = '<li id="registerBtn"><a href="/web/register.html">注册</a></li>\n' +
        '                        <li id="loginBtn"><a href="/web/login.html">登录</a></li>'
    $('#nav').append(htmlTag)
}

// 退出
function logout(){
    // 删除缓存
    localStorage.removeItem("token")
    // 通知后台删除缓存(redis)
    $.ajax({
        url: '/api/user/logout',
        type: 'POST',
        data: {
            token: token
        },
        dataType: 'json',
        success: function (res){
            console.log(res)
            if(res.code == 0){
                // 退出成功
                location.href = '/web/index.html'
            }else{
                // 退出失败
                alert(res.msg)
            }
        }
    })
}

/*
* 加入购物车逻辑
* */
function bindClickToCart(selector){
    $(selector).click((event) => {
        console.log(event.currentTarget.dataset.id)
        // 判断是否登录
        if(isLogin) {
            // 登录
            let id = event.currentTarget.dataset.id
            let token = localStorage.getItem('token')
            $.ajax({
                url: '/api/cart/addCart',
                type: 'POST',
                data: {
                    id: id,
                    token: token
                },
                dataType: 'json',
                success: function (res){
                    console.log(res)
                    if(res.code == 0){
                        // 获取购物车中的最新商品数量
                        getCartNum()
                        // 加购成功
                        alert('加入购物车成功')
                    }else{
                        // 加购失败
                        alert(res.msg)
                    }
                }
            })
        }else{
            // 未登录
            alert('请求登录！')
            location.href = '/web/login.html'
            /*setTimeout(() => {
                location.href = '/login.html'
            }, 500)*/
        }
    })
}

// 获取购物车中的商品购买数量
function getCartNum(){
    // 判断是否登录
    if(isLogin) {
        let token = localStorage.getItem('token')
        $.ajax({
            url: '/api/cart/getCartNum',
            type: 'POST',
            data: {
                token: token
            },
            dataType: 'json',
            success: function (res){
                // console.log(res)
                if(res.code == 0){
                    let count = res.data == 0 ? '' : res.data
                    $('#cart-num').html(count)
                }
            }
        })
    }
}
getCartNum()

/*
* 封装登录检查
* */
function checkLogin(){
    // 判断是否登录
    if(!isLogin) {
        // 未登录
        alert('请求登录！')
        location.href = '/web/login.html'
        return false
    }
}