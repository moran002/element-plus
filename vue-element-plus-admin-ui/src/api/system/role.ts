import request from '@/axios'
import { RoleParams } from './types'

export const deleteRoleApi = (data: any) => {
  return request.delete({ url: '/system/role/delete?id=' + data })
}

export const updateRoleApi = (data: any) => {
  return request.post({ url: '/system/role/update', data })
}

export const createRoleApi = (data: any) => {
  return request.post({ url: '/system/role/create', data })
}

export const getRolesApi = (params: RoleParams) => {
  return request.get({ url: '/system/role/list', params })
}

export const getRoleSimpleList = () => {
  return request.get({ url: '/system/role/simple-list' })
}
