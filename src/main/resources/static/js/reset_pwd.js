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

// 下一步 点击事件
$('#handle_next').click(() => {
  $.post("/api/user/checkCode", { phone: $('#phone').val(), code: $('#code').val()}, function (res){
    if (res.code == 0) {
      // 隐藏
      $('#one').css('display', 'none')
      // 展示
      $('#two').css('display', 'block')
    } else {
      alert(res.msg)
    }
  })
})

// 提交按钮 点击事件
$('#handle_pwd').click(() => {
  let pwd = $('#password').val()
  if (pwd.length < 6 || pwd.length > 16) {
    alert('密码长度应为6-16之间！')
    return
  }
  if (!(/[a-z]/.test(pwd) && /[A-Z]/.test(pwd) && /[0-9]/.test(pwd))) {
    alert('密码必须包含大小写字母和数字！')
    return
  }
  let again_pwd = $('#confirm_password').val()
  if (again_pwd != pwd) {
    alert('两次密码不一致！')
    return
  }

  $.post("/api/user/resetPwd", { phone: $('#phone').val(),code: $('#code').val(), password: pwd, again_pwd: again_pwd }, function (res){
    if (res.code == 0) {
      alert(res.msg)
      // 隐藏
      $('#two').css('display', 'none')
      // 展示
      $('#three').css('display', 'block')
      $('#title').html('重置成功')

    } else {
      alert(res.msg)
    }
  })
})

// 检查手机号是否存在于商城系统
$('#phone').change(() => {
  let res = checkPhone()
  // 判断检验是否成功
  if(!res){
    return;
  }
  // 校验手机号之前是否已经在数据库中存在
  $.post('/api/user/checkPhone', { phone: $('#phone').val() }, function (res) {
    if (res.code == 0) {
      // 移除属性
      $('#send_code').removeAttr('disabled')
    } else {
      alert('手机号未注册!')
    }
  })
})
// 获取服务器验证码
let second = 60
let phone = ''
$('#send_code').click(() => {
  let res = checkPhone()
  // 判断检验是否成功
  if(!res){return;}
  // 禁用发送验证码按钮
  $('#send_code').attr('disabled', 'disabled')
  downTime()
  // 发送验证码
  phone = $('#phone').val()
  $.post('/api/user/sendSMS', { phone: phone }, function (res) {
    if (res.code == 0) {
      console.log(res.data)
      $("#code").val(res.data)
    }
  })
})
// 验证码倒计时
function downTime() {
  $('#send_code').html(second + "秒后重新发送")
  $('#handle_next').removeAttr('disabled')
  // 每隔1秒执行一次，timer保存定时器的编号
  let timer = setInterval(() => {
    // 判断倒计时是否结束
    if (second <= 0) {
      // 清除指定编号的定时器
      clearInterval(timer)
      // 重置
      $('#send_code').html("发送验证码")
      second = 60
      $('#send_code').removeAttr('disabled')
      return
    }
    // 每隔一秒执行的函数
    second--
    $('#send_code').html(second + "秒后重新发送")
  }, 1000)
}

// 校验手机号是否合规
function checkPhone() {
  let phone = $('#phone').val()
  if (phone.length != 11) {
    alert('手机号长度正确！')
    $('#phone').focus()
    return false
  }
  if (!/^1[345789]\d{9}$/.test(phone)) {
    alert('手机号格式不正确！')
    $('#phone').focus()
    return false
  }
  return true
}