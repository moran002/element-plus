// 用户相关

export interface UserParams {
  pageSize: number
  pageNo: number
  account?: string
  nickName?: string
  roleId?: string
}

export interface RoleParams {
  pageSize: number
  pageNo: number
  name?: string
}

export interface LogParams {
  pageSize: number
  pageNo: number
  type?: number
  nickName?: string
}
