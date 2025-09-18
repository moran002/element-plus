import request from '@/axios'
import { LogParams } from './types'

export const getLogsApi = (params: LogParams) => {
  return request.get({ url: '/system/log/list', params })
}
