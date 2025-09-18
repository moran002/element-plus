import request from '@/axios'
import { UserParams } from './types'

export const updatePasswordApi = (data: any) => {
  return request.post({ url: '/system/user/password', data })
}

export const deleteUserApi = (data: any) => {
  return request.delete({ url: '/system/user/delete?id=' + data })
}

export const updateUserApi = (data: any) => {
  return request.post({ url: '/system/user/update', data })
}

export const createUserApi = (data: any) => {
  return request.post({ url: '/system/user/create', data })
}

export const getUsersApi = (params: UserParams) => {
  return request.get({ url: '/system/user/list', params })
}
