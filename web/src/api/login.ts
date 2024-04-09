import http from '@/api'
import { LoginInfo } from '@/interface'

export const login = (info: LoginInfo) => {
    return http.post<string>('/user/login', info)
}