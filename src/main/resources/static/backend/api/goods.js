// 查询列表接口
const getGoods = (params) => {
  return $axios({
    url: '/api/goods/lists',
    method: 'get',
    params
  })
}



// 修改接口
const updateGoods = (params) => {
  return $axios({
    url: '/api/goods/updateGoods',
    method: 'put',
    data: { ...params }
  })
}
// 修改榜单状态接口
const updateTopType = (params) => {
  return $axios({
    url: '/api/goods/updateTopType',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addGoods = (params) => {
  return $axios({
    url: '/api/goods/saveGoods',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const getGoodsById = (id) => {
  return $axios({
    url: `/api/goods/detail/${id}`,
    method: 'get'
  })
}

// 修改---启用禁用接口
const updateGoodsStatus = (params) => {
  return $axios({
    url: '/api/goods/updateGoods',
    method: 'put',
    data: { ...params }
  })
}
// 删除当前列的接口
const delGoods = (id) => {
  return $axios({
    url: `/api/goods/delGoods/${id}`,
    method: 'delete'
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}