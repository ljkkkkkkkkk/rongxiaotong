import http from '@/api'
import { PasswordInfo } from '@/interface'

export const updatepw = (pw: PasswordInfo) => {
    return http.post<null>('user/loginUpdatePassword', pw)
}