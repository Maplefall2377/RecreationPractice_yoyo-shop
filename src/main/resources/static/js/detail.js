// 获取所有分类
$.ajax('/api/index/kinds', {
    // 默认异步，false为同步
    async: false,
    // 请求方式
    // type: 'post',
    // 预期服务器返回内容的类型
    dataType: 'json',
    // 请求失败时调用此函数(自动)
    error: function (err) {
        console.log(err);
    },
    // 请求成功时调用此函数(自动), data服务器返回的内容，data为json格式, data在响应主体中
    success: function (data) {
        // console.log(data);
        let kindsTags;
        if (data.code == 0) {
            // 获取分类列表
            let kinds = data.data
            console.log(kinds)
            // 用于存放所有的 li标签
            kindsTags = ''
            kinds.forEach(function (item, index) {
                // 获取分类名称
                let name = item.name
                kindsTags = kindsTags + '<li><a href="/web/kinds.html?type=' + item.id + '">' + name + '</a></li>'
            })
            // 获取页面中id="allKinds"的标签
            // 把上边拼接好的字符串，放入id="allKinds"的标签中
            $('#allKinds').html(kindsTags)

        }
    }
})
$('#submit').click(function () {
    // 当点击按钮时次函数执行
    let kw = $('#kw').val() // 获取输入框的值
    // 判断输入框的内容是否为空
    if (!kw) {
        alert('输入框不能为空！')
        return
    }
    // url拼接参数，跳转到搜索页面
    location.href = '/web/search.html?kw=' + kw
})

// 标识当前正在浏览的图片  第一张0 第二张1 第三张2
let index = 0
function changePic(num) {
    // 记录当前点击的图片，第一张0 第二张1 第三张2
    index = num
    // 控制图片移动的距离
    let left = index * 340

    $('#wrap_scroll').css("left", '-' + left + 'px')
}

//获取地址栏中id的值
const urlParams = new URLSearchParams(location.search);
let id = urlParams.has('id') ? urlParams.get('id') : ''; // 更严谨的写法

if(!id){
    alert('id错误！')
}else {
    $.ajax({
        url: '/api/goods/detail/'+id,
        type: 'get',
        // sync: false,
        dataType: 'json',
        success: function (result) {
            if(result.code != 0){
                alert(result.msg)
            }else{
                // 获取商品信息
                let data = result.data
                console.log(data)
                // 轮播大图
                $('#wrap_scroll').html('<img src="/common/download?name='+data.cover+'"/>\n' +
                    '                            <img src="/common/download?name='+data.image1+'"/>\n' +
                    '                            <img src="/common/download?name='+data.image2+'"/>')
                // 小图
                let str = ''
                for(let i = 0; i < 3; i++){
                    str += '<div onclick="changePic('+i+')">\n' +
                        '       <img src="/common/download?name='+data[i===0 ? 'cover' : i == 1 ? 'image1' : 'image2']+'" alt="">\n' +
                        ' </div>'
                }
                $('#small_pic').html(str)
                // 右侧基本信息
                $("#baseinfo").html('<h2>'+data.name+'</h2>\n' +
                '                    <div class="stock">库存: '+data.stock+'</div>\n' +
                '                    <div class="kinds">分类: '+data.type.name+'</div>\n' +
                '                    <div class="desc">介绍: '+data.intro+'</div>\n' +
                '                    <div class="price">￥ '+data.price+'</div>\n' +
                '                    <button class="btn add-cart" data-id="'+data.id+'">加入购物车</button>')
                bindClickToCart('#baseinfo .add-cart')
            }
        }
    })
}


$.ajax({
    url: '/api/goods/ranking',
    type: 'get',
    // sync: false,
    dataType: 'json',
    success: function (result) {
        console.log(result)
        if (result.code == 0) {
            let data = result.data
            let str = ''
            data.forEach(function (item, index) {
                str += '<li class="item">\n' +
                ' <a href="/web/detail.html?id=' + item.id + '">\n' +
                ' <img src="/common/download?name=' + item.cover + '" alt="' + item.name + '">\n' +
                ' <div class="desc">\n' +
                '       <p class="title">' + item.name + '</p>\n' +
                '       <p class="price">￥' + item.price.toFixed(2) + '</p>\n' +
                ' </div>\n' +
                ' </a>\n' +
                ' </li>'

            })
            $('#ranking').html(str)

        }
    }
})