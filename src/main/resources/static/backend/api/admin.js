function loginApi (data) {
  return $axios({
    'url': '/api/admin/login',
    'method': 'post',
    data
  })
}

function logoutApi (tokenAdmin) {
  return $axios({
    url: `/api/admin/logout/${tokenAdmin}`,
    method: 'get'
  })
}


function updatePassword (data) {
  return $axios({
    url: `/api/admin/updatepwd`,
    method: 'put',
    data
  })
}
