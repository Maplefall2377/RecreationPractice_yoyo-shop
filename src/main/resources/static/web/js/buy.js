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

function tips(title) {
    $('#alert').html(title).css('opacity', 1)
    // 1.5秒后自动消失，1500毫秒后执行第一次参数函数（）
    // 一次性定时器
    setTimeout(function () {
        $('#alert').html(title).css('opacity', 0)
    }, 1500)
}

//获取地址栏中total的值
const urlParams = new URLSearchParams(location.search);
let total = urlParams.has('total') ? urlParams.get('total') : '0';

$('#total').html(total)

// 根据令牌获取用户信息，并且添加到页面中的三个输入框中
let data = {token: token}
$.post('/api/user/getUserInfo', data, function (res) {
    if (res.code != 0) {
        alert("登录已失效！")
        location.href = '/web/login.html'
        return
    }
    // 获取服务器返回的用户信息
    let data = res.data
    $('#name').val(data.name)
    $('#phone').val(data.phone)
    $('#address').val(data.address)
})

let payType
$('.pic').click((event) => {
    // 把原生的标签对象转换成jQuery对象
    $(event.currentTarget).addClass("active").siblings().removeClass('active')
    // 获取当前选中的支付方式: 1微信 2支付宝 3到付
    payType = $(event.currentTarget).data('val')
})

$('#handle-submit').click(() => {
    // payType在判断小括号中，如果值为：false '' "" undefined null 0 NaN
    if (!payType) {
        tips('请选择支付方式')
        return
    }
    // 判断姓名，手机号，地址是否为空
    let name = $('#name').val()
    if (!name) {
        tips('姓名不能为空')
        return
    }
    let phone = $('#phone').val()
    if (!phone) {
        tips('手机号不能为空')
        return
    }
    // 校验手机号格式
    if (!/^1[3-9]\d{9}$/.test(phone)) {
        tips('手机号格式不正确')
        return
    }
    let address = $('#address').val()
    if (!address) {
        tips('地址不能为空')
        return
    }
    // 提交订单
    let data = {
        token: token,
        payType,
        name: name,
        phone: phone,
        address
    }
    $.ajax({
        url: '/api/order/submit',
        type: 'POST',
        data: data,
        success: function (res) {
            if (res.code != 0) {
                tips(res.msg)
                return
            }
            $('#alert_success').html("购买成功！").css("opacity", 1)
            setTimeout(function(){
                window.location.href = '/web/order.html'
            }, 1500)
        },
        error: function (err) {
            console.log(err)
        }
    })
})