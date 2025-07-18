// 获取所有分类
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
            // 获取页面中id="allKinds"的标签
            // 把上边拼接好的字符串，放入id="allKinds"的标签中
            $('#allKinds').html(kindsTags)

        }
    }
})

// 获取指定的分类商品信息--分页
function getData(kw, page, size){
    $.get('/api/index/search', 'kw='+kw+'&page='+page+'&size='+size, function (result){
        if(result.code == 0){
            // 要展示的数据
            let goods = result.data.goods
            // 推荐的商品总数
            let count = result.data.count
            // 数据展示
            let goodsTags = ''
            goods.forEach((item, index) => {
                goodsTags += '<li class="item">\n' +
                    '                <img onclick="goToUrl('+item.id+')" src="/common/download?name='+item.cover+'" />\n' +
                    '                <p class="title">'+item.name+'</p>\n' +
                    '                <text class="price">￥'+item.price+'</text>\n' +
                    '                <div class="btn">\n' +
                    '                    <button class="add-cart" data-id="'+item.id+'">加入购物车</button>\n' +
                    '                </div>\n' +
                    '                <a href="/web/detail.html?id='+item.id+'" class="detail">查看详情</a>\n' +
                    '            </li>'
            })
            $('#list').html(goodsTags)
            bindClickToCart('#list .add-cart')

            // 分页
            let pageTags = ''
            // 总页数
            let pageCount = Math.ceil(count / size)
            // 判断是否是第一页
            if(page == 1){
                pageTags = '<li class="page-item disabled">\n' +
                    '      <a class="page-link" href="javascript:void(0)" aria-label="Previous">\n' +
                    '        <span aria-hidden="true">&laquo;</span>\n' +
                    '      </a>\n' +
                    '    </li>'
            }else{
                pageTags = '<li class="page-item">\n' +
                    '      <a class="page-link" href="javascript:void(0)" aria-label="Previous" onclick="getData('+"'"+kw+"'"+', '+(page - 1)+','+size+')">\n' +
                    '        <span aria-hidden="true">&laquo;</span>\n' +
                    '      </a>\n' +
                    '    </li>'
            }


            for(let i = 1; i <= pageCount; i++){
                if(i == page){
                    pageTags += '<li class="page-item active"><a class="page-link" href="javascript:void(0)">'+i+'</a></li>'
                }else{
                    pageTags += '<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="getData('+"'"+kw+"'"+', '+i+','+size+')">'+i+'</a></li>'
                }
            }

            // 判断是否是第一页
            if(page == pageCount){
                pageTags += '<li class="page-item disabled">\n' +
                    '      <a class="page-link" href="javascript:void(0)" aria-label="Next">\n' +
                    '        <span aria-hidden="true">&raquo;</span>\n' +
                    '      </a>\n' +
                    '    </li>'
            }else{
                pageTags += '<li class="page-item">\n' +
                    '      <a class="page-link" href="javascript:void(0)" aria-label="Next" onclick="getData('+"'"+kw+"'"+', '+(page + 1)+','+size+')">\n' +
                    '        <span aria-hidden="true">&raquo;</span>\n' +
                    '      </a>\n' +
                    '    </li>'
            }


            $('#pagination').html(pageTags)
        }
    }, 'json')
}

// 得到的是编码后的内容  （肉干 = %E8%82%89%E5%B9%B2）
const params = new URLSearchParams(location.search);
let kw = params.has('kw') ? decodeURIComponent(params.get('kw')) : '';
let page = 1, size = 4
// 第一次调用getData为获取指定分类的商品信息
getData(kw, 1, size)

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