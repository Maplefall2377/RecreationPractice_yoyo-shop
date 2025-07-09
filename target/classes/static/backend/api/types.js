function queryTypeList () {
  return $axios({
    url: '/api/type/lists',
    method: 'get'
  })
}
function getAllTypes () {
  return $axios({
    url: '/api/type/listall',
    method: 'get'
  })
}
// 修改接口
const updateType = (params) => {
  return $axios({
    url: '/api/type/updateType',
    method: 'put',
    data: { ...params }
  })
}
// 新增接口
const addType = (params) => {
  return $axios({
    url: `/api/type/saveType`,
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const getTypeById = (id) => {
  return $axios({
    url: `/api/type/detail/${id}`,
    method: 'get'
  })
}
// 删除当前列的接口
const delType = (id) => {
  return $axios({
    url: `/api/type/delType/${id}`,
    method: 'delete'
  })
}
