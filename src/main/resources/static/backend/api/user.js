function getUserList (params) {
  return $axios({
    url: '/api/user/lists',
    method: 'get',
    params
  })
}

// 修改---启用禁用接口
function updateUserStatus (params) {
  return $axios({
    url: '/api/user/updateUserStatus',
    method: 'put',
    data: { ...params }
  })
}

// 新增---添加员工
function addUser (params) {
  return $axios({
    url: '/api/user/adduser',
    method: 'post',
    data: { ...params }
  })
}

// 修改---添加员工
function updateUser (params) {
  return $axios({
    url: '/api/user/updateUser',
    method: 'put',
    data: { ...params }
  })
}

// 修改页面反查详情接口
function getUserById (id) {
  return $axios({
    url: `/api/user/getuserbyid/${id}`,
    method: 'get'
  })
}

// 删除当前列的接口
const delUser = (id) => {
  return $axios({
    url: '/api/user/delUser',
    method: 'delete',
    params: { id }
  })
}