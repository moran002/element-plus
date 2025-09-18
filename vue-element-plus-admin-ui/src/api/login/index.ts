import request from '@/axios'
import type { UserType } from './types'

interface RoleParams {
  roleName: string
}

export const loginApi = (data: UserType): Promise<IResponse<string>> => {
  return request.post({ url: '/auth/login', data })
}

export const loginOutApi = (): Promise<IResponse> => {
  return request.get({ url: '/auth/logout' })
}

export const getUserInfo = (): Promise<IResponse<UserType>> => {
  return request.get({ url: '/auth/userInfo' })
}

export const getRouters = (): Promise<IResponse<AppCustomRouteRecordRaw[]>> => {
  return request.get({ url: '/auth/routers' })
}
