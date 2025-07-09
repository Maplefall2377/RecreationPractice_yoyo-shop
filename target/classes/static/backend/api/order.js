// 查询列表页接口
const geAllOrders = (params) => {
  return $axios({
    url: `/api/order/listspage`,
    method: 'get',
    params
  })
}

// 查看接口
const queryOrderDetailById = (id) => {
  return $axios({
    url: `/orderDetail/${id}`,
    method: 'get'
  })
}

// 派送，完成接口
const updateStatus = (id, status) => {
  return $axios({
    url: `/api/order/updateStatus/${id}/${status}`,
    method: 'put'
  })
}
