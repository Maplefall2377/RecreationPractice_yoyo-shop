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
// 封装函数
let total = 0
function  getCartList() {
    let token = localStorage.getItem("token")
    total = 0
    $.ajax({
        url: '/api/cart/getCartList',
        type: 'POST',
        data: {
            token: token
        },
        dataType: 'json',
        success: function (res) {
            console.log(res)
            if (res.code != 0) {
                alert("登录已失效！")
                location.href = '/web/login.html'
            }
            // 获取服务器返回的购物车商品列表
            let data = res.data
            let html = ''

            Array.isArray(data) && data.forEach(item => {
                html = html + `
                <div class="item">
                    <img src="/common/download?name=` + item.pic + `" />
                    <div class="right">
                        <div>` + item.title + `</div>
                        <div>单价: ￥` + item.price + `</div>
                        <div>数量：` + item.num + `</div>
                        <div>
                            <div onclick="changeNum(1, ` + item.id + `)"><i class="bi bi-plus-lg"></i></div>
                            <div onclick="changeNum(-1, ` + item.id + `)"><i class="bi bi-dash-lg"></i></div>
                            <div onclick="del(` + item.id + `)"><i class="bi bi-trash"></i></div>
                        </div>
                    </div>
                </div>
            `
                total += item.price * item.num
            })
            $('#list').html(html)
            $("#total").html(total.toLocaleString('zh-CN', {style: 'currency', currency: 'CNY'}))
        }
    })
}
// 首次渲染购物车列表
getCartList()
// 更改购物车商品数量
function changeNum(num, cartId){
    let token = localStorage.getItem("token")
    $.post('/api/cart/changeNum', { num: num, cartId: cartId, token: token }, function (res){
        if(res.code == 0){
            $('#alert_success').html("更新成功！").css('opacity', 1)
            setTimeout(() => {
                $('#alert_success').html("").css('opacity', 0)
                getCartNum()
                getCartList()
            }, 1500)
        }else {
            tips(res.msg)
        }
    })
}
// 删除购物车商品
function del(cartId){
    let token = localStorage.getItem("token")
    $.post('/api/cart/delCart', { cartId: cartId, token: token }, function (res){
        if(res.code == 0){
            $('#alert_success').html("删除成功！").css('opacity', 1)
            setTimeout(() => {
                $('#alert_success').html("").css('opacity', 0)
                getCartNum()
                getCartList()
            }, 1500)
        }else {
            tips(res.msg)
        }
    })
}
// 跳转到购买页面（订单提交页）
// location.href = '/buy.html?total=' + total
$('#handle-submit').click(() => {
    if(total == 0){
        tips("购物车为空！")
        return
    }
    location.href = '/web/buy.html?total=' + total
})