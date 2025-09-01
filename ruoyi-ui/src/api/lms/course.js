import request from '@/utils/request'

// 查询参数列表
export function list(query) {
  return request({
    url: '/lms/course/list',
    method: 'get',
    params: query
  })
}

export function listAll() {
  return request({
    url: '/lms/course/listAll',
    method: 'get'
  })
}

//保存并上传数据
export function saveBatch(data) {
  return request({
    url: 'lms/course/saveBatch',
    method: 'post',
    data: data
  })
}

