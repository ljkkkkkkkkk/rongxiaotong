import http from '@/api'
import { RegisterInfo } from '@/interface'

export const register = (info: RegisterInfo) => {
    return http.post<null>('user/add', info)
}