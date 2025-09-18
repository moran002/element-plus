import request from '@/axios'

export const deleteApi = (data: any) => {
  return request.delete({ url: '/system/menu/delete?id=' + data })
}

export const updateApi = (data: any) => {
  return request.post({ url: '/system/menu/update', data })
}

export const createApi = (data: any) => {
  return request.post({ url: '/system/menu/create', data })
}

export const getListApi = () => {
  return request.get({ url: '/system/menu/list' })
}

export const getSimpleListApi = () => {
  return request.get({ url: '/system/menu/simple-list' })
}
