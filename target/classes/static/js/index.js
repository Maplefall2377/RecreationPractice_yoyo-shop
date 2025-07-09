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
// 获取今日精选
$.get('/api/index/todayChoice', {}, function(result){
    console.log(result)
    if(result.code == 0){
        let goodsTags = ''
        result.data.forEach((item, index) => {
            goodsTags += '<div class="body">\n' +
                '                    <div class="goodsInfo">\n' +
                '                        <h2><a href=/web/detail.html?id='+item.id+'>'+item.name+'</a></h2>\n' +
                '                        <div class="label">今日精选推荐</div>\n' +
                '                        <div class="btn add-cart" data-id="'+item.id+'">加入购物车</div>\n' +
                '                    </div>\n' +
                '                    <a href=/web/detail.html?id='+item.id+'><img src="/common/download?name='+item.cover+'" alt=""></a>\n' +
                '                </div>'
        })
        $('#today').html(goodsTags)
        bindClickToCart('#today .add-cart')
    }
}, 'json')
// 获取热销商品
$.get('/api/index/recommend', {type: 2}, function(result){
    console.log(result)
    if(result.code == 0){
        let goodsTags = ''
        result.data.slice(0,6).forEach((item, index) => {
            goodsTags += '<div class="item">\n' +
            '                            <div class="pic">\n' +
            '                                <img onclick="goToUrl('+item.id+')" src="/common/download?name='+item.cover+'" />\n' +
            '                                <div class="tips">\n' +
            '                                    <div class="detail">\n' +
            '                                        <i></i>\n' +
            '                                        <a href=/web/detail.html?id='+item.id+ '><span style="color: white">查看详情</span></a>\n' +
            '                                    </div>\n' +
            '                                    <div class="add-cart" data-id="'+item.id+'">加入购物车</div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                            <div class="title">\n' +
                                            item.name
            +'                            </div>\n' +
            '                            <div class="price">\n' +
            '                                ¥ '+item.price+'\n' +
            '                            </div>\n' +
            '                        </div>'
        })
        // $('#hots') 获取页面中id="hots"标签
        // html方法，把小括号中的变量值插入到id="hots"标签中
        $('#hots').html(goodsTags)
        bindClickToCart("#hots .add-cart")
    }
}, 'json')

// 获取新品商品
$.get('/api/index/recommend', {type: 3}, function(result){
    console.log(result)
    if(result.code == 0){
        let goodsTags = ''
        result.data.slice(0,6).forEach((item, index) => {
            goodsTags += '<div class="item">\n' +
                '                            <div class="pic">\n' +
                '                                <img onclick="goToUrl('+item.id+')" src="/common/download?name='+item.cover+'" />\n' +
                '                                <div class="tips">\n' +
                '                                    <div class="detail">\n' +
                '                                        <i></i>\n' +
                '                                        <a href=/web/detail.html?id='+item.id+ '><span style="color: white">查看详情</span></a>\n' +
                '                                    </div>\n' +
                '                                    <div class="add-cart" data-id="'+item.id+'">加入购物车</div>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="title">\n' +
                item.name
                +'                            </div>\n' +
                '                            <div class="price">\n' +
                '                                ¥ '+item.price+'\n' +
                '                            </div>\n' +
                '                        </div>'
        })
        // $('#hots') 获取页面中id="hots"标签
        // html方法，把小括号中的变量值插入到id="hots"标签中
        $('#news').html(goodsTags)
        bindClickToCart('#news .add-cart')
    }
}, 'json')


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

// 跳转到详情页
function goToUrl(id){
    // 打开新的页面
    location.href = '/web/detail.html?id='+id
}